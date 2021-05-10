package club.ncr.cayenne;

import club.ncr.cayenne.auto._MotorImpulse;
import club.ncr.motors.QueryFilters;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.Orderings;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;
import org.thrustcurve.api.json.JsonPrimitive;
import org.thrustcurve.api.json.JsonValue;

import java.util.*;
import java.util.stream.Collectors;

import static club.ncr.util.CayenneKit.select;

public class MotorImpulse extends _MotorImpulse implements Comparable<MotorImpulse> {

	public static MotorImpulse createNew(String impulseClass, DataContext ctx) {
		
		List<MotorImpulse> exists= get(ctx, ExpressionFactory.matchExp(MotorImpulse.IMPULSE.getName(), impulseClass));

		int classification = 0;

		switch (impulseClass) {
			case "A": case "B": case "C": case "D": case "E": case "F": case "G":
				classification = 1;
			    break;
			case "H": case "I": case "J": case "K": case "L":
				classification = 2;
				break;
			default:
				classification = 3;
			    break;
		}
		
		if (exists == null || exists.isEmpty()) {
			MotorImpulse record= new MotorImpulse();
			ctx.registerNewObject(record);
			record.setImpulse(impulseClass);
			record.setClassification(classification);
			ctx.commitChanges();
			return record;
			
		}
		
		return exists.get(0);
	}
	
	public static List<MotorImpulse> get(DataContext ctx, Expression filter) {
		SelectQuery query = select(MotorImpulse.class, filter, new Ordering(MotorImpulse.IMPULSE.getName(), SortOrder.ASCENDING_INSENSITIVE));
		return (List<MotorImpulse>)ctx.performQuery(query);
	}

	public static HashMap<String, MotorImpulse> getMap(DataContext ctx, Expression filter) {
		HashMap<String, MotorImpulse> map= new HashMap<String, MotorImpulse>();
		for (MotorImpulse imp : get(ctx, filter)) {
			map.put(""+ imp.getImpulse(), imp);
		}
		return map;
	}

	public int getId() {
	    return (int)getObjectId().getIdSnapshot().get(ID_PK_COLUMN);
	}

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

	public Collection<Motor> getMotors(List<MotorMfg> motorMfgs, MotorDiameter diameter) {
		SelectQuery query = Motor.select(Motor.IMPULSE.eq(this),  new Orderings().then(Motor.TOTAL_IMPULSE_NS.asc()).then(Motor.COMMON_NAME.ascInsensitive()));
		if (motorMfgs != null) {
			QueryFilters.motorManufacturers(motorMfgs, query);
		}
		if (diameter != null) {
			query.andQualifier(Motor.DIAMETER.eq(diameter));
		}
	    return getObjectContext().performQuery(query);
	}

	public Collection<MotorDiameter> getDiameters(MotorMfg mfg, MotorImpulse imp) {
		SelectQuery query;
		if (mfg == null && imp == null) {
			return Collections.emptyList();
		}
		if (imp == null) {
			query = select(Motor.class, Motor.MANUFACTURER.eq(mfg), Motor.COMMON_NAME.ascInsensitive());
		} else if (mfg == null) {
			query = select(Motor.class, Motor.IMPULSE.eq(imp), Motor.COMMON_NAME.ascInsensitive());
		} else {
			query = select(Motor.class, Motor.IMPULSE.eq(imp), Motor.COMMON_NAME.ascInsensitive());
			query.andQualifier(Motor.MANUFACTURER.eq(mfg));
		}
		return getObjectContext().performQuery(query);
	}

	public Set<Float> getMotorDiameters() {
		return super.getMotorCaseImpulses().stream()
				.filter(c -> c.getMotorCase() != null && c.getMotorCase().getMotorDiameter() != null)
				.sorted(Comparator.comparing(a -> a.getMotorCase().getMotorDiameter().getDiameter()))
				.map(c -> c.getMotorCase().getMotorDiameter().getDiameter())
				.collect(Collectors.toSet());

	}
	public List<MotorCase> getMotorCases(float diameter) {
		return super.getMotorCaseImpulses().stream()
				.filter(c -> c.getMotorCase().getMotorDiameter().getDiameter() == diameter)
				.map(c -> c.getMotorCase())
				.sorted(Comparator.comparing(a -> a.getName()))
				.collect(Collectors.toList());
	}

}
