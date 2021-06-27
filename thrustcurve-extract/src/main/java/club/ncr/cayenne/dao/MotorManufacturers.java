package club.ncr.cayenne.dao;

import club.ncr.cayenne.DAO;
import club.ncr.cayenne.func.Retry;
import club.ncr.cayenne.model.MotorMfg;
import org.apache.cayenne.CayenneRuntimeException;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;

import java.util.*;

public class MotorManufacturers implements DAO<MotorMfg> {

    private final ObjectContext ctx;

    public MotorManufacturers(ObjectContext ctx) {
        this.ctx = ctx;
    }

    public MotorMfg createNew(String manufacturer, String abbreviation) {
        MotorMfg record= new MotorMfg();
        ctx.registerNewObject(record);
        record.setName(manufacturer);
        record.setAbbreviation(abbreviation);
        ctx.commitChanges();
        return record;
    }

    public List<MotorMfg> get(Expression filter) {
        return get(Optional.ofNullable(filter));
    }

    @Override
    public List<MotorMfg> get(Optional<Expression> filter) {
        SelectQuery query= new SelectQuery(MotorMfg.class);
        if (filter != null && filter.isPresent()) {
            query.andQualifier(filter.get());
        }
        query.addOrdering(new Ordering(MotorMfg.NAME.getName(), SortOrder.ASCENDING));
        return new Retry<>(() -> ctx.performQuery(query)).execute(3);
    }

    @Override
    public Map<String, MotorMfg> getMap(Expression filter) {
        Map<String, MotorMfg> map= new HashMap<>();
        for (MotorMfg mfg : get(Optional.ofNullable(filter))) {
            map.put(mfg.getName(), mfg);
        }
        return map;
    }

    @Override
    public Collection<MotorMfg> getAll() {
        return get(Optional.empty());
    }
}
