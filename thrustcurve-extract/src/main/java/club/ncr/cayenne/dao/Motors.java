package club.ncr.cayenne.dao;

import club.ncr.cayenne.DAO;
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

import java.util.*;
import java.util.stream.Collectors;

public class Motors implements DAO<Motor> {

	private final ObjectContext ctx;

	public Motors(ObjectContext ctx) {
		this.ctx = ctx;
	}

	public Optional<Motor> getByExternalId(String id) {
		return get(Optional.of(Motor.EXTERNAL_ID.eq(id))).stream().findFirst();
	}

	@Override
	public List<Motor> getAll() {
		return get(Optional.empty(), 3);
	}

	@Override
	public Map<String, Motor> getMap(Expression filter) {
		return get(Optional.ofNullable(filter)).stream().collect(Collectors.toMap(k -> k.getExternalId(), v -> v));
	}

	public List<Motor> get(Expression filter) {
		return get(Optional.ofNullable(filter), 3);
	}

	@Override
	public List<Motor> get(Optional<Expression> filter) {
	    return get(filter, 3);
    }

	public List<Motor> get(Optional<Expression> filter, int retries) {
		SelectQuery query= new SelectQuery(Motor.class);
		if (filter != null && filter.isPresent()) {
			query.andQualifier(filter.get());
		}
		query.addOrdering(new Ordering(Motor.COMMON_NAME.getName(), SortOrder.ASCENDING_INSENSITIVE));
        return new Retry<>(() -> ctx.performQuery(query)).execute(retries);
	}

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
