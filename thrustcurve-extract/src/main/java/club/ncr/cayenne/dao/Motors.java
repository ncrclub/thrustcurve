package club.ncr.cayenne.dao;

import club.ncr.cayenne.func.Retry;
import club.ncr.cayenne.model.*;
import club.ncr.cayenne.select.Builder;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.Orderings;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Motors {

	private final ObjectContext ctx;

	public Motors(ObjectContext ctx) {
		this.ctx = ctx;
	}

	public Optional<Motor> getByExternalId(String id) {
		List<Motor> list= get(ExpressionFactory.matchExp(Motor.EXTERNAL_ID.getName(), id));
		if (list.size() == 0) { return Optional.empty(); }
		return Optional.of(list.get(0));
	}

	public List<Motor> getAll() {
		return get(null, 3);
	}
	public List<Motor> get(Expression filter) {
	    return get(filter, 3);
    }

	public List<Motor> get(Expression filter, int retries) {
		SelectQuery query= new SelectQuery(Motor.class);
		if (filter != null) {
			query.andQualifier(filter);
		}
		query.addOrdering(new Ordering(Motor.COMMON_NAME.getName(), SortOrder.ASCENDING_INSENSITIVE));
        return new Retry<>(() -> ctx.performQuery(query)).execute(retries);
	}

	/*
	public static HashMap<String, Motor> getMap(ObjectContext ctx, Expression filter) {
		HashMap<String, Motor> map= new HashMap<>();
		for (Motor motor : get(ctx, filter)) {
			map.put(motor.getManufacturer().getName() +"/"+ motor.getCommonName() +"/"+ motor.getDiameter().getDiameter() +"/"+ motor.getPropellant().getName(), motor);
		}
		return map;
	}

	 */

	public Motor createNew(String source, String externalId, MotorMfg manufacturer, MotorName name, MotorType type, MotorImpulse impulse, MotorDiameter diameter, MotorCase motorCase, MotorPropellant propellant, MotorCertOrg certOrg) {

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
		return Builder.select(Motor.class, eq, then);
	}

}
