package club.ncr.cayenne;

import club.ncr.cayenne.auto._MotorDiameter;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;
import org.thrustcurve.api.json.JsonPrimitive;
import org.thrustcurve.api.json.JsonValue;

import java.util.HashMap;
import java.util.List;

public class MotorDiameter extends _MotorDiameter {

	public static MotorDiameter createNew(Integer diameter, DataContext ctx) {
		
		MotorDiameter record= new MotorDiameter();
		ctx.registerNewObject(record);
		record.setDiameter(diameter);
		ctx.commitChanges();
		return record;
	}
	
	
	public static List<MotorDiameter> get(DataContext ctx, Expression filter) {
		SelectQuery query= new SelectQuery(MotorDiameter.class);
		if (filter != null) {
			query.andQualifier(filter);
		}
		query.addOrdering(new Ordering(MotorDiameter.DIAMETER_PROPERTY, SortOrder.ASCENDING));
		return (List<MotorDiameter>)ctx.performQuery(query);
	}
	
	public static HashMap<String, MotorDiameter> getMap(DataContext ctx, Expression filter) {
		HashMap<String, MotorDiameter> map= new HashMap<String, MotorDiameter>();
		for (MotorDiameter diam : get(ctx, filter)) {
			map.put(""+ diam.getDiameter(), diam);
		}
		return map;
	}

	public JsonValue toJsonValue() {
		JsonValue json= new JsonPrimitive(getDiameter());
		return json;
	}
}
