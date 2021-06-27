package club.ncr.cayenne.cache;

import club.ncr.cayenne.dao.MotorDataFormats;
import club.ncr.cayenne.model.MotorDataFormat;

public class MotorDataFormatCache extends RecordCache<String, MotorDataFormat> {

    private final MotorDataFormats ctx;

    public MotorDataFormatCache(MotorDataFormats ctx, boolean autoCreate) {
        super(ctx, autoCreate);
        this.ctx = ctx;
    }

    @Override
    public String key(MotorDataFormat value) {
        return value.getName();
    }

    public MotorDataFormat get(String key, String extension) {
        MotorDataFormat record= super.get(key);

        if (record == null && autoCreate()) {
            record= ctx.createNew(key, extension);
            put(key, record);
        }

        return record;
    }

}
