package club.ncr.cayenne.cache;

import club.ncr.cayenne.dao.MotorPropellants;
import club.ncr.cayenne.model.MotorPropellant;
import club.ncr.cayenne.model.MotorType;

public class MotorPropellantCache extends RecordCache<String, MotorPropellant> {

    private final MotorPropellants dao;

    public MotorPropellantCache(MotorPropellants dao, boolean autoCreate) {
        super(dao, autoCreate);
        this.dao = dao;
    }


    @Override
    public String key(MotorPropellant value) {
        return value.getName();
    }

    public MotorPropellant get(String name, MotorType type) {
        MotorPropellant record= super.get(name);

        if (record == null && autoCreate()) {
            record= dao.createNew(name, type.getName());
            put(name, record);
        }

        return record;
    }

}
