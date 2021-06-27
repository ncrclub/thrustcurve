package club.ncr.cayenne.cache;

import club.ncr.cayenne.model.MotorImpulse;
import club.ncr.cayenne.model.MotorName;
import org.apache.cayenne.ObjectContext;

import java.util.Collection;

public class MotorNameCache extends CayenneRecordCache<String, MotorName> {

    public MotorNameCache(ObjectContext ctx, boolean autoCreate) {
        super(ctx, autoCreate);
    }

    @Override
    public Collection<MotorName> getAll() {
        return MotorName.get(context(), null);
    }

    @Override
    public String key(MotorName value) {
        return value.getName();
    }

    public MotorName get(String name, MotorImpulse impulse) {
        MotorName record= super.get(name);

        if (record == null && autoCreate()) {
            record= MotorName.createNew(name, impulse, context());
            put(name, record);
        }

        return record;
    }

}
