package club.ncr.cayenne.cache;

import club.ncr.cayenne.dao.MotorNames;
import club.ncr.cayenne.model.MotorImpulse;
import club.ncr.cayenne.model.MotorName;

public class MotorNameCache extends RecordCache<String, MotorName> {

    private final MotorNames ctx;

    public MotorNameCache(MotorNames ctx, boolean autoCreate) {
        super(ctx, autoCreate);
        this.ctx = ctx;
    }

    @Override
    public String key(MotorName value) {
        return value.getName();
    }

    public MotorName get(String name, MotorImpulse impulse) {
        MotorName record= super.get(name);

        if (record == null && autoCreate()) {
            record= ctx.createNew(name, impulse);
            put(name, record);
        }

        return record;
    }

}
