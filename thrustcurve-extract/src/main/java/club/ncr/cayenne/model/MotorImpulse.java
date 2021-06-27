package club.ncr.cayenne.model;

import club.ncr.cayenne.model.auto._MotorImpulse;
import club.ncr.cayenne.dao.Motors;
import club.ncr.cayenne.func.Retry;
import club.ncr.dto.ImpulseDTO;
import club.ncr.motors.QueryFilters;
import org.apache.cayenne.ObjectContext;
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

import static club.ncr.cayenne.select.Builder.select;

public class MotorImpulse extends _MotorImpulse implements Comparable<MotorImpulse> {

	private ImpulseDTO dto;

	public static MotorImpulse getOrCreate(ObjectContext ctx, String impulseClass) {
		
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

	public static MotorImpulse get(ObjectContext ctx, String impulse) {
		SelectQuery query = select(MotorImpulse.class, MotorImpulse.IMPULSE.eq(impulse), new Ordering(MotorImpulse.IMPULSE.getName(), SortOrder.ASCENDING_INSENSITIVE));
		return (MotorImpulse) new Retry<>(() -> ctx.performQuery(query)).execute(3).stream().findFirst().orElse(null);
	}

	public static MotorImpulse get(ObjectContext ctx, ImpulseDTO impulse) {
	    return get(ctx, impulse.impulse);
	}

	public static List<MotorImpulse> getAll(ObjectContext ctx) {
	    return get(ctx, (Expression)null);
	}

	public static List<MotorImpulse> get(ObjectContext ctx, Expression filter) {
	    return get(ctx, filter, 3);
    }
	public static List<MotorImpulse> get(ObjectContext ctx, Expression filter, int retries) {
		SelectQuery query = select(MotorImpulse.class, filter, new Ordering(MotorImpulse.IMPULSE.getName(), SortOrder.ASCENDING_INSENSITIVE));
		return new Retry(() -> ctx.performQuery(query)).execute(3);
	}

	public static HashMap<String, MotorImpulse> getMap(ObjectContext ctx, Expression filter) {
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

	@Override
	public int hashCode() {
		return Objects.hash(getImpulse());
	}

	public int compareTo(MotorImpulse i) {
		if (i == null) { return 1; }
		return getImpulse().compareToIgnoreCase(i.getImpulse());
		
	}

	public int compareTo(ImpulseDTO dto) {
		if (dto == null) { return 1; }
		return getImpulse().compareToIgnoreCase(dto.impulse);
	}

	public JsonValue toJsonValue() {
		JsonValue json= new JsonPrimitive(getImpulse());
		return json;
	}

	public List<Motor> getMotors(List<MotorMfg> motorMfgs, MotorDiameter diameter) {
		SelectQuery query = Motors.select(Motor.IMPULSE.eq(this),  new Orderings().then(Motor.TOTAL_IMPULSE_NS.asc()).then(Motor.COMMON_NAME.ascInsensitive()));
		if (motorMfgs != null && !motorMfgs.isEmpty()) {
			QueryFilters.motorManufacturers(motorMfgs, query);
		}
		if (diameter != null) {
			query.andQualifier(Motor.DIAMETER.eq(diameter));
		}
	    return new Retry(() -> getObjectContext().performQuery(query)).execute(3);
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
		return new Retry(() -> getObjectContext().performQuery(query)).execute(3);
	}

	public Set<Float> getMotorDiameters() {
		return new Retry<>(() -> super.getMotorCaseImpulses()).execute(3).stream()
				.filter(c -> c.getMotorCase() != null && c.getMotorCase().getMotorDiameter() != null)
				.sorted(Comparator.comparing(a -> a.getMotorCase().getMotorDiameter().getDiameter()))
				.map(c -> c.getMotorCase().getMotorDiameter().getDiameter())
				.collect(Collectors.toSet());

	}
	public List<MotorCase> getMotorCases(float diameter) {
		return new Retry<>(() -> super.getMotorCaseImpulses()).execute(3).stream()
				.map(c -> c.getMotorCase())
				.filter(c -> c.getMotorDiameter().getDiameter() == diameter)
				.sorted(Comparator.comparing(a -> a.getName()))
				.collect(Collectors.toList());
	}


	@Override
	public List<MotorName> getMotorNames() {
		List<MotorName> names = new ArrayList<>();
		names.addAll(super.getMotorNames());
		Collections.sort(names, MotorName::compareTo);
		return names;
	}

	public ImpulseDTO getDto() {
		if (this.dto == null) {
		    this.dto = ImpulseDTO.valueOf(getImpulse());
		}
		return dto;
	}

	public String toString() {
		return getImpulse();
	}

}
