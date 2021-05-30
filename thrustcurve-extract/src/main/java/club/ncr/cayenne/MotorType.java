package club.ncr.cayenne;

import club.ncr.cayenne.auto._MotorType;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;

import java.util.HashMap;
import java.util.List;

public class MotorType extends _MotorType {
	
	public static MotorType createNew(String name, ObjectContext ctx) {
		MotorType record= new MotorType();
		ctx.registerNewObject(record);
		record.setName(name);
		ctx.commitChanges();
		return record;
		
	}

	public static List<MotorType> get(ObjectContext ctx, Expression filter) {
		SelectQuery query= new SelectQuery(MotorType.class);
		if (filter != null) {
			query.andQualifier(filter);
		}
		query.addOrdering(new Ordering(MotorType.NAME.getName(), SortOrder.ASCENDING_INSENSITIVE));
		return (List<MotorType>)ctx.performQuery(query);
	}
	
	public static HashMap<String, MotorType> getMap(ObjectContext ctx, Expression filter) {
		HashMap<String, MotorType> map= new HashMap<String, MotorType>();
		for (MotorType type : get(ctx, filter)) {
			map.put(type.getName(), type);
		}
		return map;
	}
}
