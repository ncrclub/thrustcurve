package club.ncr.cayenne;

import club.ncr.cayenne.auto._MotorCase;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MotorCase extends _MotorCase implements Comparable<MotorCase> {

	
	public static MotorCase createNew(String name, MotorMfg mfg, MotorDiameter diameter, MotorImpulse impulse, DataContext ctx) {
		MotorCase record= new MotorCase();
		ctx.registerNewObject(record);
		record.setName(name);

		record.setMotorDiameter(diameter);

		MotorCaseImpulse mci = new MotorCaseImpulse();
		mci.setMotorImpulse(impulse);
		record.addToMotorCaseImpulses(mci);

		ctx.commitChanges();
		
		return record;
	}

	public static List<MotorCase> get(ObjectContext ctx, Expression filter) {
		SelectQuery query= new SelectQuery(MotorCase.class);
		if (filter != null) {
			query.andQualifier(filter);
		}
		query.addOrdering(new Ordering(MotorCase.NAME.getName(), SortOrder.ASCENDING_INSENSITIVE));
		return (List<MotorCase>)ctx.performQuery(query);
	}
	
	public static HashMap<String, MotorCase> getMap(DataContext ctx, Expression filter) {
		HashMap<String, MotorCase> map= new HashMap<String, MotorCase>();
		for (MotorCase obj : get(ctx, filter)) {
			map.put(obj.getName(), obj);
		}
		return map;
	}

	@Override
	public int compareTo(MotorCase o) {
	    if (o == null) {
	    	return 1;
		}
	    return getName().compareTo(o.getName());
	}

	public String uuid() {
		return getName() + "-"+ getMotorDiameter().toString();
	}

}
