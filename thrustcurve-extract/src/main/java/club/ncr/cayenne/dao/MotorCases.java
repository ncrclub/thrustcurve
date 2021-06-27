package club.ncr.cayenne.dao;

import club.ncr.cayenne.DAO;
import club.ncr.cayenne.model.MotorCase;
import club.ncr.cayenne.model.MotorImpulse;
import club.ncr.cayenne.model.MotorMfg;
import club.ncr.cayenne.model.MotorDiameter;
import club.ncr.cayenne.model.MotorCaseImpulse;
import club.ncr.cayenne.model.MotorCaseMfg;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;

import java.util.*;
import java.util.stream.Collectors;

public class MotorCases implements DAO<MotorCase> {

    private final ObjectContext ctx;

    public MotorCases(ObjectContext ctx) {
        this.ctx = ctx;
    }

    public MotorCase getOrCreate(String name, MotorMfg mfg, MotorDiameter diameter, MotorImpulse impulse) {

        // cache.get(name, mfg, diameter, impulse);

        MotorCase exists = get(MotorCase.NAME.eq(name))
                .stream()
                .filter(c -> c.getMotorDiameter().equals(diameter))
                // .filter(c -> c.getMotors().stream().filter(m -> m.getManufacturer().equals(mfg)).findFirst().isPresent())
                .findFirst().orElse(null);

        if (exists != null) {

            MotorImpulse impulseRef = exists.getMotorCaseImpulses().stream()
                    .map(mci -> mci.getMotorImpulse())
                    .filter(i -> impulse.equals(i))
                    .findFirst().orElse(null);

            if (impulseRef == null) {
                MotorCaseImpulse mci = new MotorCaseImpulse();
                mci.setMotorImpulse(impulse);
                exists.addToMotorCaseImpulses(mci);
                ctx.commitChanges();
            }

            return exists;
        }

        MotorCase record= new MotorCase();
        ctx.registerNewObject(record);
        record.setName(name);

        record.setMotorDiameter(diameter);

        MotorCaseImpulse mci = new MotorCaseImpulse();
        mci.setMotorImpulse(impulse);
        mci.setMotorCase(record);
        record.addToMotorCaseImpulses(mci);

        MotorCaseMfg mcm = new MotorCaseMfg();
        mcm.setMotorManufacturer(mfg);
        mcm.setMotorCase(record);
        record.addToMotorCaseManufacturer(mcm);

        ctx.commitChanges();

        return record;
    }

    @Override
    public Map<String, MotorCase> getMap(Expression filter) {
        return get(filter).stream().collect(Collectors.toMap(k -> k.uuid(), v -> v));
    }

    @Override
    public List<MotorCase> get(Expression filter) {
        SelectQuery query= new SelectQuery(MotorCase.class);
        if (filter != null) {
            query.andQualifier(filter);
        }
        query.addOrdering(new Ordering(MotorCase.NAME.getName(), SortOrder.ASCENDING_INSENSITIVE));
        return (List<MotorCase>)ctx.performQuery(query);
    }

    @Override
    public Collection<MotorCase> getAll() {
        return get((Expression)null);
    }

    /*
    public HashMap<String, MotorCase> getMap(Expression filter) {
        HashMap<String, MotorCase> map= new HashMap<String, MotorCase>();
        for (MotorCase obj : get(filter)) {
            map.put(obj.getName(), obj);
        }
        return map;
    }
     */

    /*
    public void put(String uuid, MotorCaseDTO motorCaseDTO) {
        this.dtos.put(uuid, motorCaseDTO);
    }
     */

}
