package club.ncr.motors;

import club.ncr.cayenne.MotorMfg;
import org.apache.cayenne.ObjectContext;

import java.util.Collection;

public class ManufacturersCache extends CayenneRecordCache<String, MotorMfg> {


    public ManufacturersCache(ObjectContext ctx, boolean autoCreate) {
        super(ctx, autoCreate);
    }

    public MotorMfg get(String name, String abbv) {
        MotorMfg record= super.get(name);

        if (record == null && autoCreate()) {
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
