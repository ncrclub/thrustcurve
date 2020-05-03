package club.ncr.cayenne;

import club.ncr.cayenne.auto._MotorPropellant;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;

import java.util.HashMap;
import java.util.List;

public class MotorPropellant extends _MotorPropellant {

	public static MotorPropellant createNew(String propellant, String type, DataContext ctx) {
		MotorPropellant record= new MotorPropellant();
		ctx.registerNewObject(record);
		record.setName(propellant);
		record.setType(type);
		ctx.commitChanges();
		return record;
		
	}

	public static List<MotorPropellant> get(DataContext ctx, Expression filter) {
		SelectQuery query= new SelectQuery(MotorPropellant.class);
		if (filter != null) {
			query.andQualifier(filter);
		}
		query.addOrdering(new Ordering(MotorPropellant.NAME.getName(), SortOrder.ASCENDING_INSENSITIVE));
		return (List<MotorPropellant>)ctx.performQuery(query);
	}
	
	public static HashMap<String, MotorPropellant> getMap(DataContext ctx, Expression filter) {
		HashMap<String, MotorPropellant> map= new HashMap<String, MotorPropellant>();
		for (MotorPropellant prop : get(ctx, filter)) {
			map.put(prop.getName(), prop);
		}
		return map;
	}
}
