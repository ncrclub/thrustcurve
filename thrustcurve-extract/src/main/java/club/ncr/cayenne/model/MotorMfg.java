package club.ncr.cayenne.model;

import club.ncr.cayenne.model.auto._MotorMfg;
import club.ncr.cayenne.dao.Motors;
import club.ncr.dto.ImpulseDTO;
import org.apache.cayenne.CayenneRuntimeException;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.Orderings;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;
import org.thrustcurve.api.json.JsonObject;
import org.thrustcurve.api.json.JsonValue;

import java.util.*;
import java.util.stream.Collectors;

import static club.ncr.cayenne.select.Builder.select;

public class MotorMfg extends _MotorMfg implements Comparable<MotorMfg> {

	/*
	@Deprecated
	public static MotorMfg createNew(String manufacturer, String abbreviation, ObjectContext ctx) {
		MotorMfg record= new MotorMfg();
		ctx.registerNewObject(record);
		record.setName(manufacturer);
		record.setAbbreviation(abbreviation);
		ctx.commitChanges();
		return record;
	}

	@Deprecated
	public static List<MotorMfg> get(ObjectContext ctx, Expression filter) {
		SelectQuery query= new SelectQuery(MotorMfg.class);
		if (filter != null) {
			query.andQualifier(filter);
		}
		query.addOrdering(new Ordering(MotorMfg.NAME.getName(), SortOrder.ASCENDING));
		try {
			return (List<MotorMfg>) ctx.performQuery(query);
		} catch (CayenneRuntimeException err) {
			return (List<MotorMfg>) ctx.performQuery(query);
		}
	}

	 */

	/*
	public static HashMap<String, MotorMfg> getNameMap(ObjectContext ctx, Expression filter) {
		HashMap<String, MotorMfg> map= new HashMap<String, MotorMfg>();
		for (MotorMfg mfg : get(ctx, filter)) {
			map.put(mfg.getName(), mfg);
		}
		return map;
	}
	 */
	
	public JsonValue toJsonValue() {
		JsonObject json= new JsonObject();
		json.set("name", getName());
		json.set("abbreviation", getAbbreviation());
		return json;
	}


	public Map<String, Object> asMap() {
		Map<String, Object> map= new HashMap<>();
		map.put("name", getName());
		map.put("abbreviation", getAbbreviation());
		map.put("motors", getMotors().size());
		return map;
	}

	public int getId() {
		return (int)getObjectId().getIdSnapshot().get(ID_PK_COLUMN);
	}

	public int compareTo(MotorMfg o) {
		if (o == null) { return 1; }
		return getName().compareTo(o.getName());
	}

	@Override
	public String toString() {
		return getAbbreviation() == null ? getName() : getAbbreviation();
	}

	public Collection<Motor> getMotors(ImpulseDTO impulse, MotorDiameter diameter) {
		SelectQuery query = Motors.select(Motor.MANUFACTURER.eq(this),  new Orderings().then(Motor.TOTAL_IMPULSE_NS.asc()).then(Motor.COMMON_NAME.ascInsensitive()));
		if (impulse != null) {
			query.andQualifier(Motor.IMPULSE.eq(MotorImpulse.get(this.getObjectContext(), impulse)));
		}
		if (diameter != null) {
			query.andQualifier(Motor.DIAMETER.eq(diameter));
		}
		return getObjectContext().performQuery(query);
	}

	public List<MotorCase> getMotorCases(ImpulseDTO impulse, MotorDiameter diameter) {
	    return getMotors(impulse, diameter).stream()
				.map(m -> m.getCase())
                .filter(c -> c.getMotorDiameter().equals(diameter))
				.distinct()
				.collect(Collectors.toList());
	}

	public List<MotorDiameter> getDiameters(MotorMfg mfg, MotorImpulse imp) {
		SelectQuery query;
		if (mfg == null && imp == null) {
			query = MotorDiameter.select(Motor.MANUFACTURER.eq(mfg), null);
		} else if (imp == null) {
			query = MotorDiameter.select(Motor.MANUFACTURER.eq(mfg), MotorDiameter.DIAMETER.asc());
		} else if (mfg == null) {
			query = MotorDiameter.select(Motor.IMPULSE.eq(imp), MotorDiameter.DIAMETER.asc());
		} else {
			query = MotorDiameter.select(Motor.IMPULSE.eq(imp), MotorDiameter.DIAMETER.asc());
			query.andQualifier(Motor.MANUFACTURER.eq(mfg));
		}
		return getObjectContext().performQuery(query);
	}

	@Override
	public boolean equals(Object o) {
	    if (o == null) { return false; }
	    if (o instanceof MotorMfg) {
	        MotorMfg other = (MotorMfg) o;
	        if (this.getName().equals(other.getName())) {
	        	return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName());
	}

}
