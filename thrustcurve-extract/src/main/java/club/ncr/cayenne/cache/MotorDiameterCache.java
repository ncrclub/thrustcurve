package club.ncr.cayenne.cache;

import club.ncr.cayenne.dao.MotorDiameters;
import club.ncr.cayenne.model.MotorDiameter;

public class MotorDiameterCache extends RecordCache<Float, MotorDiameter> {

    private final MotorDiameters source;

    public MotorDiameterCache(MotorDiameters dao, boolean autoCreate) {
        super(dao, autoCreate);
        this.source = dao;
    }

    @Override
    public Float key(MotorDiameter value) {
        return value.getDiameter();
    }

    public MotorDiameter get(Float key) {
        MotorDiameter record= super.get(key);

        if (record == null && autoCreate()) {
            put(key, record = source.createNew(key));
        }

        return record;
    }

}
