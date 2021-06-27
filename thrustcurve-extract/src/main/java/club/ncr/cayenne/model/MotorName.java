package club.ncr.cayenne.model;

import club.ncr.cayenne.model.auto._MotorName;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.SelectQuery;

import java.util.HashMap;
import java.util.List;

public class MotorName extends _MotorName implements Comparable {

	public static MotorName createNew(String commonName, MotorImpulse impulse, ObjectContext ctx) {
		// TODO Auto-generated method stub
		MotorName record= new MotorName();
		ctx.registerNewObject(record);
		record.setName(commonName);
		record.setImpulse(impulse);
		ctx.commitChanges();
		return record;
		
	}
	
	public static List<MotorName> get(ObjectContext ctx, Expression filter) {
	
		SelectQuery query= new SelectQuery(MotorName.class);
		if (filter != null) {
			query.andQualifier(filter);
		}
		query
			.addOrderings(MotorImpulse.IMPULSE.asc()
			.then(MotorName.NAME.ascInsensitive()));
		return (List<MotorName>)ctx.performQuery(query);
		
		
	}
	
	public static HashMap<String, MotorName> getMap(ObjectContext ctx, Expression filter) {
		HashMap<String, MotorName> map= new HashMap<String, MotorName>();
		for (MotorName name : get(ctx, filter)) {
			map.put(""+ name.getName(), name);
		}
		return map;
	}
	
	public int compareTo(Object o) {
		if (o == null) { return 1; }
		if (o instanceof Motor) {
			Motor motor= (Motor) o;
			return compareTo(motor.getCommonName());
		} else if (o instanceof MotorName) {
			MotorName other= (MotorName) o;
			
			int diff= getImpulse().compareTo(other.getImpulse());
			
			if (diff == 0) {
			    try {
					return Integer.parseInt(getName().substring(1)) - Integer.parseInt(other.getName().substring(1));
				} catch (NumberFormatException nfx) {
			    	return getName().compareTo(other.getName());
				}
			} else {
				return diff;
			}
			
		}
		return -1;
	}

	@Override
	public String toString() {
		return this.getName();
	}
}
