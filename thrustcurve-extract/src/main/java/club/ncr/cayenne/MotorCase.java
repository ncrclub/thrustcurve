package club.ncr.cayenne;

import club.ncr.cayenne.auto._MotorCase;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;

import java.util.HashMap;
import java.util.List;

public class MotorCase extends _MotorCase {

	
	public static MotorCase createNew(String name, DataContext ctx) {
		MotorCase record= new MotorCase();
		ctx.registerNewObject(record);
		record.setName(name);
		
		ctx.commitChanges();
		
		return record;
		
	}

	public static List<MotorCase> get(DataContext ctx, Expression filter) {
		SelectQuery query= new SelectQuery(MotorCase.class);
		if (filter != null) {
			query.andQualifier(filter);
		}
		query.addOrdering(new Ordering(MotorCase.NAME_PROPERTY, SortOrder.ASCENDING_INSENSITIVE));
		return (List<MotorCase>)ctx.performQuery(query);
	}
	
	public static HashMap<String, MotorCase> getMap(DataContext ctx, Expression filter) {
		HashMap<String, MotorCase> map= new HashMap<String, MotorCase>();
		for (MotorCase obj : get(ctx, filter)) {
			map.put(obj.getName(), obj);
		}
		return map;
	}
	
}
