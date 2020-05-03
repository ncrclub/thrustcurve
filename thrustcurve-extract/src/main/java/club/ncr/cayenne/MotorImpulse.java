package club.ncr.cayenne;

import club.ncr.cayenne.auto._MotorImpulse;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;
import org.thrustcurve.api.json.JsonPrimitive;
import org.thrustcurve.api.json.JsonValue;

import java.util.HashMap;
import java.util.List;

public class MotorImpulse extends _MotorImpulse implements Comparable<MotorImpulse> {

	public static MotorImpulse createNew(String impulseClass, DataContext ctx) {
		
		List<MotorImpulse> exists= get(ctx, ExpressionFactory.matchExp(MotorImpulse.IMPULSE.getName(), impulseClass));
		
		if (exists == null || exists.isEmpty()) {
			
			MotorImpulse record= new MotorImpulse();
			ctx.registerNewObject(record);
			record.setImpulse(impulseClass);
			ctx.commitChanges();
			return record;
			
		}
		
		return exists.get(0);
	
		
	}
	
	public static List<MotorImpulse> get(DataContext ctx, Expression filter) {
		SelectQuery query= new SelectQuery(MotorImpulse.class);
		if (filter != null) {
			query.andQualifier(filter);
		}
		query.addOrdering(new Ordering(MotorImpulse.IMPULSE.getName(), SortOrder.ASCENDING_INSENSITIVE));
		return (List<MotorImpulse>)ctx.performQuery(query);
	}
	
	public static HashMap<String, MotorImpulse> getMap(DataContext ctx, Expression filter) {
		HashMap<String, MotorImpulse> map= new HashMap<String, MotorImpulse>();
		for (MotorImpulse imp : get(ctx, filter)) {
			map.put(""+ imp.getImpulse(), imp);
		}
		return map;
	}


	/*
	public JsonValue toJsonValue() {
		JsonValue json= new JsonPrimitive(getImpulse());
		return json;
	}
	*/

	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) { return false; }
		if (!(obj instanceof MotorImpulse)) { return super.equals(obj); }
		
		MotorImpulse impulse= (MotorImpulse) obj;
		
		return getImpulse().equalsIgnoreCase(impulse.getImpulse());
	}

	public int compareTo(MotorImpulse i) {
		if (i == null) { return 1; }
		
		return getImpulse().compareToIgnoreCase(i.getImpulse());
		
	}

	public JsonValue toJsonValue() {
		JsonValue json= new JsonPrimitive(getImpulse());
		return json;
	}
}
