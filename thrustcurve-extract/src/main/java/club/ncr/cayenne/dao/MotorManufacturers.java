package club.ncr.cayenne.dao;

import club.ncr.cayenne.DAO;
import club.ncr.cayenne.model.MotorMfg;
import org.apache.cayenne.CayenneRuntimeException;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<MotorMfg> get(Expression filter) {
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

    @Override
    public Map<String, MotorMfg> getMap(Expression filter) {
        HashMap<String, MotorMfg> map= new HashMap<String, MotorMfg>();
        for (MotorMfg mfg : get(filter)) {
            map.put(mfg.getName(), mfg);
        }
        return map;
    }

    @Override
    public Collection<MotorMfg> getAll() {
        return get((Expression)null);
    }
}
