package club.ncr.cayenne.cache;

import club.ncr.cayenne.model.MotorPropellant;
import club.ncr.cayenne.model.MotorType;
import org.apache.cayenne.ObjectContext;

import java.util.Collection;

public class MotorPropellantCache extends CayenneRecordCache<String, MotorPropellant> {

    public MotorPropellantCache(ObjectContext ctx, boolean autoCreate) {
        super(ctx, autoCreate);
    }

    @Override
    public Collection<MotorPropellant> getAll() {
        return MotorPropellant.get(context(), null);
    }

    @Override
    public String key(MotorPropellant value) {
        return value.getName();
    }

    public MotorPropellant get(String name, MotorType type) {
        MotorPropellant record= super.get(name);

        if (record == null && autoCreate()) {
            record= MotorPropellant.createNew(name, type.getName(), context());
            put(name, record);
        }

        return record;
    }

}
