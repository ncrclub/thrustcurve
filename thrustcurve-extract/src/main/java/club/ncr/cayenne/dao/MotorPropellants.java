package club.ncr.cayenne.dao;

import club.ncr.cayenne.DAO;
import club.ncr.cayenne.model.MotorPropellant;
import club.ncr.cayenne.model.MotorType;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;

import java.util.*;


public class MotorPropellants implements DAO<MotorPropellant> {

    private final ObjectContext ctx;

    public MotorPropellants(ObjectContext ctx) {
        this.ctx = ctx;
    }

    public MotorPropellant createNew(String propellant, String type) {
        MotorPropellant record= new MotorPropellant();
        ctx.registerNewObject(record);
        record.setName(propellant);
        record.setType(type);
        ctx.commitChanges();
        return record;

    }

    public MotorPropellant createNew(String propellant, MotorType type) {
        MotorPropellant record= new MotorPropellant();
        ctx.registerNewObject(record);
        record.setName(propellant);
        record.setType(type.getName());
        ctx.commitChanges();
        return record;
    }

    @Override
    public List<MotorPropellant> get(Optional<Expression> filter) {
        SelectQuery query= new SelectQuery(MotorPropellant.class);
        if (filter != null && filter.isPresent()) {
            query.andQualifier(filter.get());
        }
        query.addOrdering(new Ordering(MotorPropellant.NAME.getName(), SortOrder.ASCENDING_INSENSITIVE));
        return (List<MotorPropellant>)ctx.performQuery(query);
    }

    @Override
    public Collection<MotorPropellant> getAll() {
        return get(Optional.empty());
    }

    @Override
    public Map<String, MotorPropellant> getMap(Expression filter) {
        HashMap<String, MotorPropellant> map= new HashMap<String, MotorPropellant>();
        for (MotorPropellant prop : get(Optional.ofNullable(filter))) {
            map.put(prop.getName(), prop);
        }
        return map;
    }

    public boolean exists(String name) {
        return !get(Optional.of(MotorPropellant.NAME.eq(name))).isEmpty();
    }
}
