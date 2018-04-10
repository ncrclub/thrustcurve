package club.ncr.website.db;

import club.ncr.website.db.auto._MotorType;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;

import java.util.HashMap;
import java.util.List;

public class MotorType extends _MotorType {
	
	public static MotorType createNew(String name, DataContext ctx) {
		MotorType record= new MotorType();
		ctx.registerNewObject(record);
		record.setName(name);
		ctx.commitChanges();
		return record;
		
	}

	public static List<MotorType> get(DataContext ctx, Expression filter) {
		SelectQuery query= new SelectQuery(MotorType.class);
		if (filter != null) {
			query.andQualifier(filter);
		}
		query.addOrdering(new Ordering(MotorType.NAME_PROPERTY, SortOrder.ASCENDING_INSENSITIVE));
		return (List<MotorType>)ctx.performQuery(query);
	}
	
	public static HashMap<String, MotorType> getMap(DataContext ctx, Expression filter) {
		HashMap<String, MotorType> map= new HashMap<String, MotorType>();
		for (MotorType type : get(ctx, filter)) {
			map.put(type.getName(), type);
		}
		return map;
	}
}
