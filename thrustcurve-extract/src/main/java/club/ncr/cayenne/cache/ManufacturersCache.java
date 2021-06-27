package club.ncr.cayenne.cache;

import club.ncr.cayenne.dao.MotorManufacturers;
import club.ncr.cayenne.model.MotorMfg;

public class ManufacturersCache extends RecordCache<String, MotorMfg> {


    private final MotorManufacturers source;

    public ManufacturersCache(MotorManufacturers dao, boolean autoCreate) {
        super(dao, autoCreate);
        this.source = dao;
    }

    public MotorMfg getByAbbreviation(String abbv) {

        for (MotorMfg mfg : values()) {
            if (mfg.getAbbreviation().equalsIgnoreCase(abbv)) {
                return mfg;
            }
        }
        return null;

    }

    public MotorMfg get(String name, String abbv) {
        return get(name, abbv, autoCreate());
    }

    public MotorMfg get(String name, String abbv, boolean autoCreate) {
        MotorMfg record= super.get(name);

        if (record == null && autoCreate) {
            record= source.createNew(name, abbv);
            put(name, record);
        }

        return record;
    }

    @Override
    public String key(MotorMfg value) {
        return value.getName();
    }
}
