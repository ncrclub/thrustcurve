package club.ncr.cayenne;

import club.ncr.cayenne.auto._MotorMfg;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;
import org.thrustcurve.api.json.JsonObject;
import org.thrustcurve.api.json.JsonValue;

import java.util.HashMap;
import java.util.List;

public class MotorMfg extends _MotorMfg implements Comparable<MotorMfg> {

	public static MotorMfg createNew(String manufacturer, String abbreviation, DataContext ctx) {
		MotorMfg record= new MotorMfg();
		ctx.registerNewObject(record);
		record.setName(manufacturer);
		record.setAbbreviation(abbreviation);
		ctx.commitChanges();
		return record;
	}
	
	public static List<MotorMfg> get(DataContext ctx, Expression filter) {
		SelectQuery query= new SelectQuery(MotorMfg.class);
		if (filter != null) {
			query.andQualifier(filter);
		}
		query.addOrdering(new Ordering(MotorMfg.NAME_PROPERTY, SortOrder.ASCENDING));
		return (List<MotorMfg>)ctx.performQuery(query);
	}
	
	public static HashMap<String, MotorMfg> getNameMap(DataContext ctx, Expression filter) {
		HashMap<String, MotorMfg> map= new HashMap<String, MotorMfg>();
		for (MotorMfg mfg : get(ctx, filter)) {
			map.put(mfg.getName(), mfg);
		}
		return map;
	}
	
	public JsonValue toJsonValue() {
		JsonObject json= new JsonObject();
		json.set("name", getName());
		json.set("abbreviation", getAbbreviation());
		return json;
	}

	
	public int compareTo(MotorMfg o) {
		if (o == null) { return 1; }
		
		return getName().compareTo(o.getName());
	}

}
