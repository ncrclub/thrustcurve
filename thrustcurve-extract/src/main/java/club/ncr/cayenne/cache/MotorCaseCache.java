package club.ncr.cayenne.cache;

import club.ncr.cayenne.model.MotorCase;
import club.ncr.cayenne.model.MotorDiameter;
import club.ncr.cayenne.model.MotorImpulse;
import club.ncr.cayenne.model.MotorMfg;
import org.apache.cayenne.ObjectContext;

import java.util.Collection;

public class MotorCaseCache extends CayenneRecordCache<String, MotorCase> {

    public MotorCaseCache(ObjectContext ctx, boolean autoCreate) {
        super(ctx, autoCreate);
    }

    @Override
    public Collection<MotorCase> getAll() {
        return MotorCase.get(context(), null);
    }

    @Override
    public String key(MotorCase value) {
        return value.uuid();
    }

    public MotorCase get(String name, MotorMfg mfg, MotorDiameter diameter, MotorImpulse impulse) {
        MotorCase record= super.get(name);

        if (record == null && autoCreate()) {
            record= MotorCase.getOrCreate(name, mfg, diameter, impulse, context());
            put(name, record);
        }

        return record;
    }

}
