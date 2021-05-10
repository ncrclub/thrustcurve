package club.ncr.cayenne;

import club.ncr.cayenne.auto._Motor;
import club.ncr.dto.MotorDTO;
import club.ncr.dto.MotorDataDTO;
import club.ncr.util.CayenneKit;
import org.apache.cayenne.BaseContext;
import org.apache.cayenne.access.DataContext;
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
	
	public static Motor getByExternalId(String id, DataContext ctx) {
		
		List<Motor> list= get(ctx, ExpressionFactory.matchExp(Motor.EXTERNAL_ID.getName(), id));
		
		if (list.size() == 0) { return null; }
		
		return list.get(0);
		
	}
	
	public static List<Motor> get(DataContext ctx, Expression filter) {
		SelectQuery query= new SelectQuery(Motor.class);
		if (filter != null) {
			query.andQualifier(filter);
		}
		query.addOrdering(new Ordering(Motor.COMMON_NAME.getName(), SortOrder.ASCENDING_INSENSITIVE));
		try {
			return (List<Motor>) ctx.performQuery(query);
		} catch (Exception e) {
			// try again
			return (List<Motor>) ctx.performQuery(query);
		}
	}
	
	public static HashMap<String, Motor> getMap(DataContext ctx, Expression filter) {
		HashMap<String, Motor> map= new HashMap<String, Motor>();
		for (Motor motor : get(ctx, filter)) {
			map.put(motor.getManufacturer().getName() +"/"+ motor.getCommonName() +"/"+ motor.getDiameter().getDiameter() +"/"+ motor.getPropellant().getName(), motor);
		}
		return map;
	}


	public static Motor createNew(String externalId, MotorMfg manufacturer, MotorName name, MotorType type, MotorImpulse impulse, MotorDiameter diameter, MotorPropellant propellant, MotorCertOrg certOrg) {

		DataContext ctx= (DataContext)BaseContext.getThreadObjectContext();

		Motor m= new Motor();
		MotorData data= new MotorData();
		
		ctx.registerNewObject(m);
		// ctx.registerNewObject(data);
		// data.setMotor(m);
		// m.addToData(data);
		// data.setDataSource(source);
		
		m.setLastUpdated(new Date());
		
		m.setExternalId(externalId);
		m.setPropellant(propellant);
		m.setManufacturer(manufacturer);
		m.setImpulse(impulse);
		m.setDiameter(diameter);
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

}
