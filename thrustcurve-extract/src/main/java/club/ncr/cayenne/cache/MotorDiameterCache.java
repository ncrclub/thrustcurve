package club.ncr.cayenne.cache;

import club.ncr.cayenne.model.MotorDiameter;
import org.apache.cayenne.ObjectContext;

import java.util.Collection;

public class MotorDiameterCache extends CayenneRecordCache<Float, MotorDiameter> {

    public MotorDiameterCache(ObjectContext ctx, boolean autoCreate) {
        super(ctx, autoCreate);
    }

    @Override
    public Collection<MotorDiameter> getAll() {
        return MotorDiameter.get(context(), null);
    }

    @Override
    public Float key(MotorDiameter value) {
        return value.getDiameter();
    }

    public MotorDiameter get(Float key) {
        MotorDiameter record= super.get(key);

        if (record == null && autoCreate()) {
            record= MotorDiameter.createNew(key, context());
            put(key, record);
        }

        return record;
    }

}
