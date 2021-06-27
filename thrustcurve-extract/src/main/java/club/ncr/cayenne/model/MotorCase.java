package club.ncr.cayenne.model;

import club.ncr.cayenne.model.auto._MotorCase;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;

import java.util.HashMap;
import java.util.List;

public class MotorCase extends _MotorCase implements Comparable<MotorCase> {




	@Deprecated
	public static List<MotorCase> get(ObjectContext ctx, Expression filter) {
		SelectQuery query= new SelectQuery(MotorCase.class);
		if (filter != null) {
			query.andQualifier(filter);
		}
		query.addOrdering(new Ordering(MotorCase.NAME.getName(), SortOrder.ASCENDING_INSENSITIVE));
		return (List<MotorCase>)ctx.performQuery(query);
	}

	@Deprecated
	public static HashMap<String, MotorCase> getMap(ObjectContext ctx, Expression filter) {
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
