package club.ncr.cayenne.dao;

import club.ncr.cayenne.DAO;
import club.ncr.cayenne.func.Retry;
import club.ncr.cayenne.model.MotorType;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;

import java.util.*;

public class MotorTypes implements DAO<MotorType> {

    private final ObjectContext ctx;

    public MotorTypes(ObjectContext ctx) {
        this.ctx = ctx;
    }

    public MotorType createNew(String name) {
        MotorType record= new MotorType();
        ctx.registerNewObject(record);
        record.setName(name);
        ctx.commitChanges();
        return record;

    }

    @Override
    public List<MotorType> get(Optional<Expression> filter) {
        SelectQuery query= new SelectQuery(MotorType.class);
        if (filter != null && filter.isPresent()) {
            query.andQualifier(filter.get());
        }
        query.addOrdering(new Ordering(MotorType.NAME.getName(), SortOrder.ASCENDING_INSENSITIVE));
        return new Retry<>(() -> ctx.performQuery(query)).execute(3);
    }

    @Override
    public Collection<MotorType> getAll() {
        return this.get(Optional.empty());
    }

    @Override
    public Map<String, MotorType> getMap(Expression filter) {
        Map<String, MotorType> map= new HashMap<>();
        for (MotorType type : get(Optional.ofNullable(filter))) {
            map.put(type.getName(), type);
        }
        return map;
    }
}
