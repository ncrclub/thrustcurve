package club.ncr.cayenne.dao;

import club.ncr.cayenne.DAO;
import club.ncr.cayenne.model.MotorDiameter;
import club.ncr.cayenne.select.Builder;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MotorDiameters implements DAO<MotorDiameter> {

    private final ObjectContext ctx;

    public static SelectQuery select(Expression filter, Ordering orderBy) {
        return Builder.select(MotorDiameter.class, filter, orderBy);
    }

    public MotorDiameters(ObjectContext ctx) {
        this.ctx = ctx;
    }

    public MotorDiameter createNew(Float diameter) {
        MotorDiameter record= new MotorDiameter();
        ctx.registerNewObject(record);
        record.setDiameter(diameter);
        ctx.commitChanges();
        return record;
    }

    @Override
    public List<MotorDiameter> get(Expression filter) {
        SelectQuery query= new SelectQuery(MotorDiameter.class);
        if (filter != null) {
            query.andQualifier(filter);
        }
        query.addOrdering(new Ordering(MotorDiameter.DIAMETER.getName(), SortOrder.ASCENDING));
        return (List<MotorDiameter>)ctx.performQuery(query);
    }

    @Override
    public HashMap<String, MotorDiameter> getMap(Expression filter) {
        HashMap<String, MotorDiameter> map= new HashMap<String, MotorDiameter>();
        for (MotorDiameter diam : get(filter)) {
            map.put(""+ diam.getDiameter(), diam);
        }
        return map;
    }

    @Override
    public Collection<MotorDiameter> getAll() {
        return this.get((Expression)null);
    }
}
