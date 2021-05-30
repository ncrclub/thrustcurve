package club.ncr.motors;

import club.ncr.cayenne.MotorDataFormat;
import org.apache.cayenne.ObjectContext;

import java.util.Collection;

public class MotorDataFormatCache extends CayenneRecordCache<String, MotorDataFormat> {

    public MotorDataFormatCache(ObjectContext ctx, boolean autoCreate) {
        super(ctx, autoCreate);
    }

    @Override
    public Collection<MotorDataFormat> getAll() {
        return MotorDataFormat.get(context(), null);
    }

    @Override
    public String key(MotorDataFormat value) {
        return value.getName();
    }

    public MotorDataFormat get(String key, String extension) {
        MotorDataFormat record= super.get(key);

        if (record == null && autoCreate()) {
            record= MotorDataFormat.createNew(key, extension, context());
            put(key, record);
        }

        return record;
    }

}
