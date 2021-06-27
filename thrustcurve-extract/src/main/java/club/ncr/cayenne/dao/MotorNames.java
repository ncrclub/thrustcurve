package club.ncr.cayenne.dao;

import club.ncr.cayenne.DAO;
import club.ncr.cayenne.func.Retry;
import club.ncr.cayenne.model.MotorImpulse;
import club.ncr.cayenne.model.MotorName;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.SelectQuery;

import java.util.*;

public class MotorNames implements DAO<MotorName> {

    private final ObjectContext ctx;

    public MotorNames(ObjectContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public Collection<MotorName> getAll() {
        return null;
    }

    public MotorName createNew(String commonName, MotorImpulse impulse) {
        MotorName record= new MotorName();
        ctx.registerNewObject(record);
        record.setName(commonName);
        record.setImpulse(impulse);
        ctx.commitChanges();
        return record;

    }

    @Override
    public List<MotorName> get(Optional<Expression> filter) {

        SelectQuery query= new SelectQuery(MotorName.class);
        if (filter != null && filter.isPresent()) {
            query.andQualifier(filter.get());
        }
        query
                .addOrderings(MotorImpulse.IMPULSE.asc()
                        .then(MotorName.NAME.ascInsensitive()));
        return new Retry<>(() -> ctx.performQuery(query)).execute(3);


    }

    @Override
    public Map<String, MotorName> getMap(Expression filter) {
        HashMap<String, MotorName> map= new HashMap<String, MotorName>();
        for (MotorName name : get(Optional.ofNullable(filter))) {
            map.put(""+ name.getName(), name);
        }
        return map;
    }

}
