package club.ncr.cayenne.model;

import club.ncr.cayenne.dao.MotorCases;
import club.ncr.cayenne.model.auto._MotorDiameter;
import club.ncr.cayenne.dao.Motors;
import club.ncr.dto.ImpulseDTO;
import club.ncr.cayenne.select.Builder;
import club.ncr.motors.QueryFilters;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.Orderings;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;
import org.thrustcurve.api.json.JsonPrimitive;
import org.thrustcurve.api.json.JsonValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MotorDiameter extends _MotorDiameter implements Comparable<MotorDiameter> {

	@Deprecated
	public static MotorDiameter createNew(Float diameter, ObjectContext ctx) {
		MotorDiameter record= new MotorDiameter();
		ctx.registerNewObject(record);
		record.setDiameter(diameter);
		ctx.commitChanges();
		return record;
	}


	@Deprecated
	public static List<MotorDiameter> get(ObjectContext ctx, Expression filter) {
		SelectQuery query= new SelectQuery(MotorDiameter.class);
		if (filter != null) {
			query.andQualifier(filter);
		}
		query.addOrdering(new Ordering(MotorDiameter.DIAMETER.getName(), SortOrder.ASCENDING));
		return (List<MotorDiameter>)ctx.performQuery(query);
	}

	@Deprecated
	public static HashMap<String, MotorDiameter> getMap(ObjectContext ctx, Expression filter) {
		HashMap<String, MotorDiameter> map= new HashMap<String, MotorDiameter>();
		for (MotorDiameter diam : get(ctx, filter)) {
			map.put(""+ diam.getDiameter(), diam);
		}
		return map;
	}

	@Deprecated
	public static SelectQuery select(Expression filter, Ordering orderBy) {
		return Builder.select(MotorDiameter.class, filter, orderBy);
	}

	public JsonValue toJsonValue() {
		JsonValue json= new JsonPrimitive(getDiameter());
		return json;
	}

	@Override
	public List<MotorCase> getMotorCases() {
		List<MotorCase> cases = new ArrayList<>(super.getMotorCases());
		cases.addAll(new MotorCases(getObjectContext()).get(MotorCase.MOTOR_DIAMETER.isNull()));
		return cases;
	}

	public Collection<Motor> getMotors(List<MotorMfg> motorMfgs, ImpulseDTO impulse) {
		SelectQuery query = Motors.select(Motor.DIAMETER.eq(this),  new Orderings().then(Motor.TOTAL_IMPULSE_NS.asc()).then(Motor.COMMON_NAME.ascInsensitive()));

		QueryFilters.motorManufacturers(motorMfgs, query);

		if (impulse != null) {
			query.andQualifier(Motor.IMPULSE.eq(MotorImpulse.get(this.getObjectContext(), impulse)));
		}
		return getObjectContext().performQuery(query);
	}


	@Override
	public String toString() {
		Float d = getDiameter();
		int i = d.intValue();
		if (d != i) {
			return Double.toString(d);
		} else {
			return Integer.toString(i);
		}
	}

	@Override
	public int compareTo(MotorDiameter o) {
		if (o == null) { return 1; }
		return getDiameter().compareTo(o.getDiameter());
	}

	@Override
	public int hashCode() {
		return (int)(getDiameter() * 100);
	}

	@Override
	public boolean equals(Object obj) {
	    if (obj instanceof MotorDiameter) {
	    	return getDiameter() == ((MotorDiameter) obj).getDiameter();
		}
	    return false;
	}
}
