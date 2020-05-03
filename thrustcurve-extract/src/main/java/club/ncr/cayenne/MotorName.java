package club.ncr.cayenne;

import club.ncr.cayenne.auto._MotorName;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;

import java.util.HashMap;
import java.util.List;

public class MotorName extends _MotorName implements Comparable {

	public static MotorName createNew(String commonName, MotorImpulse impulse, DataContext ctx) {
		// TODO Auto-generated method stub
		MotorName record= new MotorName();
		ctx.registerNewObject(record);
		record.setName(commonName);
		record.setImpulse(impulse);
		ctx.commitChanges();
		return record;
		
	}
	
	public static List<MotorName> get(DataContext ctx, Expression filter) {
	
		SelectQuery query= new SelectQuery(MotorName.class);
		if (filter != null) {
			query.andQualifier(filter);
		}
		query.addOrdering(new Ordering(MotorName.NAME.getName(), SortOrder.ASCENDING_INSENSITIVE));
		return (List<MotorName>)ctx.performQuery(query);
		
		
	}
	
	public static HashMap<String, MotorName> getMap(DataContext ctx, Expression filter) {
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
			MotorName name= (MotorName) o;
			
			int impulse= getImpulse().compareTo(name.getImpulse());
			
			if (impulse == 0) {
				int eq= getName().compareTo(name.getName());
				
				return eq;
			} else {
				return impulse;
			}
			
		}
		return -1;
	}

}
