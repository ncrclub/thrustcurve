package club.ncr.motors;

import club.ncr.cayenne.MotorType;
import org.apache.cayenne.ObjectContext;

import java.util.Collection;

public class MotorTypeCache extends CayenneRecordCache<String, MotorType> {

    public MotorTypeCache(ObjectContext ctx, boolean autoCreate) {
        super(ctx, autoCreate);
    }

    @Override
    public Collection<MotorType> getAll() {
        return MotorType.get(context(), null);
    }

    @Override
    public String key(MotorType value) {
        return value.getName();
    }

    public MotorType get(String name) {
        MotorType record= super.get(name);

        if (record == null && autoCreate()) {
            record= MotorType.createNew(name, context());
            put(name, record);
        }

        return record;
    }

}
