package club.ncr.cayenne.cache;

import club.ncr.cayenne.model.MotorMfg;
import org.apache.cayenne.ObjectContext;

import java.util.Collection;

public class ManufacturersCache extends CayenneRecordCache<String, MotorMfg> {


    public ManufacturersCache(ObjectContext ctx, boolean autoCreate) {
        super(ctx, autoCreate);
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
            record= MotorMfg.createNew(name, abbv, context());
            put(name, record);
        }

        return record;
    }

    @Override
    public Collection<MotorMfg> getAll() {
        return MotorMfg.get(context(), null);
    }


    @Override
    public String key(MotorMfg value) {
        return value.getName();
    }
}
