package club.ncr.cayenne.model;

import club.ncr.cayenne.model.auto._MotorPropellant;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;

import java.util.HashMap;
import java.util.List;

public class MotorPropellant extends _MotorPropellant {

	public static MotorPropellant createNew(String propellant, String type, ObjectContext ctx) {
		MotorPropellant record= new MotorPropellant();
		ctx.registerNewObject(record);
		record.setName(propellant);
		record.setType(type);
		ctx.commitChanges();
		return record;
		
	}
	public static MotorPropellant createNew(String propellant, MotorType type, ObjectContext ctx) {
		MotorPropellant record= new MotorPropellant();
		ctx.registerNewObject(record);
		record.setName(propellant);
		record.setType(type.getName());
		ctx.commitChanges();
		return record;
	}

	public static List<MotorPropellant> get(ObjectContext ctx, Expression filter) {
		SelectQuery query= new SelectQuery(MotorPropellant.class);
		if (filter != null) {
			query.andQualifier(filter);
		}
		query.addOrdering(new Ordering(MotorPropellant.NAME.getName(), SortOrder.ASCENDING_INSENSITIVE));
		return (List<MotorPropellant>)ctx.performQuery(query);
	}
	
	public static HashMap<String, MotorPropellant> getMap(ObjectContext ctx, Expression filter) {
		HashMap<String, MotorPropellant> map= new HashMap<String, MotorPropellant>();
		for (MotorPropellant prop : get(ctx, filter)) {
			map.put(prop.getName(), prop);
		}
		return map;
	}

	public static boolean exists(ObjectContext ctx, String name) {
		return !get(ctx, MotorPropellant.NAME.eq(name)).isEmpty();
	}
}
