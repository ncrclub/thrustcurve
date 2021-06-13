package club.ncr.cayenne;

import club.ncr.cayenne.auto._Motor;
import club.ncr.dto.MotorDTO;
import club.ncr.util.CayenneKit;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.Orderings;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;
import org.thrustcurve.api.json.JsonArray;
import org.thrustcurve.api.json.JsonObject;
import org.thrustcurve.api.json.JsonValue;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Motor extends _Motor {
	
	public static Motor getByExternalId(String id, ObjectContext ctx) {
		
		List<Motor> list= get(ctx, ExpressionFactory.matchExp(Motor.EXTERNAL_ID.getName(), id));
		
		if (list.size() == 0) { return null; }
		
		return list.get(0);
		
	}

	public static List<Motor> get(ObjectContext ctx, Expression filter) {
	    return get(ctx, filter, 3);
    }
	public static List<Motor> get(ObjectContext ctx, Expression filter, int retries) {
		SelectQuery query= new SelectQuery(Motor.class);
		if (filter != null) {
			query.andQualifier(filter);
		}
		query.addOrdering(new Ordering(Motor.COMMON_NAME.getName(), SortOrder.ASCENDING_INSENSITIVE));
		try {
			return (List<Motor>) ctx.performQuery(query);
		} catch (Exception e) {
			// try again
			if (retries > 0 && e.getCause() != null && e.getCause() instanceof CommunicationsException) {
				return get(ctx, filter, retries-1);
			}

			throw e;
		}
	}
	
	public static HashMap<String, Motor> getMap(ObjectContext ctx, Expression filter) {
		HashMap<String, Motor> map= new HashMap<>();
		for (Motor motor : get(ctx, filter)) {
			map.put(motor.getManufacturer().getName() +"/"+ motor.getCommonName() +"/"+ motor.getDiameter().getDiameter() +"/"+ motor.getPropellant().getName(), motor);
		}
		return map;
	}


	public static Motor createNew(ObjectContext ctx, String source, String externalId, MotorMfg manufacturer, MotorName name, MotorType type, MotorImpulse impulse, MotorDiameter diameter, MotorCase motorCase, MotorPropellant propellant, MotorCertOrg certOrg) {

		Motor m= new Motor();

		ctx.registerNewObject(m);

		m.setSource(source);
		m.setCreatedDate(new Date());
		m.setLastUpdated(new Date());
		m.setExternalId(externalId);
		m.setPropellant(propellant);
		m.setManufacturer(manufacturer);
		m.setImpulse(impulse);
		m.setDiameter(diameter);
		m.setCase(motorCase);
		m.setCommonName(name);
		m.setCertificationOrganization(certOrg);
		m.setType(type);

		ctx.commitChanges();

		return m;
	}

	public static SelectQuery select(Expression eq, Orderings then) {
		return CayenneKit.select(Motor.class, eq, then);
	}

	public JsonValue toJsonValue() {
		JsonObject json= new JsonObject();
		
		json.set("external-id", getExternalId());
		json.set("common-name", getCommonName().getName());
		json.set("name", getCommonName().getName());
		json.set("designation", getDesignation());
		json.set("impulse", getImpulse().getImpulse());
		json.set("diameter", getDiameter().getDiameter());
		json.set("length", getLength());
		json.set("burn-time-s", getBurnTime());
		json.set("weight-g", getWeight());
		json.set("propellant", getPropellant().getName());
		json.set("manufacturer", getManufacturer().getName());
		json.set("manufacturer-abbv", getManufacturer().getAbbreviation());
		json.set("case-info", getCase().getName());
		
		JsonArray dataArray= new JsonArray();
		json.set("data", dataArray);
		for (MotorData data : getData()) {
			dataArray.add(data.toJsonValue());
		}
		
		
		return json;
	}

	public MotorDTO asDTO(boolean withData) {
		return new MotorDTO(this, withData);
	}

	public void update(
			Double burnTime,
			Double grossWeight,
			String brandName,
			Double weight,
			Double averageThrust,
			Double maxThrust,
			Double totalImpulse,
			String designation,
			Double length
	) {
		boolean updated = false;

		if (burnTime != null && getBurnTime() != burnTime) {
			setBurnTime(burnTime);
			updated = true;
		}

		if (grossWeight != null && getGrossWeight() != grossWeight) {
			setGrossWeight(grossWeight);
			updated = true;
		}

		if (brandName != null && !brandName.equals(getBrandName())) {
			setBrandName(brandName);
			updated = true;
		}

		if (weight != null && getWeight() != weight) {
			setWeight(weight);
			updated = true;
		}

		if (averageThrust != null && getThrustAvg() != averageThrust) {
			setThrustAvg(averageThrust);
			updated = true;
		}

		if (maxThrust != null && getThrustMax() != maxThrust) {
			setThrustMax(maxThrust);
			updated = true;
		}

		if (totalImpulse != null && getTotalImpulseNs() != totalImpulse) {
			setTotalImpulseNs(totalImpulse);
			updated = true;
		}

		if (designation != null && !designation.equals(getDesignation())) {
			setDesignation(designation);
			updated = true;
		}

		if (length != null && getLength() != length) {
			setLength(length);
			updated = true;
		}

		if (updated) {
			setLastUpdated(new Date());
		}
	}
}
