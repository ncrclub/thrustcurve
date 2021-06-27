package club.ncr.cayenne.cache;

import club.ncr.cayenne.dao.MotorTypes;
import club.ncr.cayenne.model.MotorType;

public class MotorTypeCache extends RecordCache<String, MotorType> {

    private final MotorTypes ctx;

    public MotorTypeCache(MotorTypes ctx, boolean autoCreate) {
        super(ctx, autoCreate);
        this.ctx = ctx;
    }

    @Override
    public String key(MotorType value) {
        return value.getName();
    }

    public MotorType get(String name) {
        MotorType record= super.get(name);

        if (record == null && autoCreate()) {
            record= ctx.createNew(name);
            put(name, record);
        }

        return record;
    }

}
