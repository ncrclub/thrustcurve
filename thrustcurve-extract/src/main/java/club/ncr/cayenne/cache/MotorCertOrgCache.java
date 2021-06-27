package club.ncr.cayenne.cache;

import club.ncr.cayenne.dao.MotorCertOrgs;
import club.ncr.cayenne.model.MotorCertOrg;

import java.util.Optional;

public class MotorCertOrgCache extends RecordCache<String, MotorCertOrg> {

    private final MotorCertOrgs ctx;

    public MotorCertOrgCache(MotorCertOrgs ctx, boolean autoCreate) {
        super(ctx, autoCreate);
        this.ctx = ctx;
    }

    @Override
    public String key(MotorCertOrg value) {
        return Optional.ofNullable(value.getName()).orElse("");
    }

    public MotorCertOrg get(String name) {
        MotorCertOrg record= super.get(name);

        if (record == null && autoCreate()) {
            record= ctx.createNew(name);
            put(name, record);
        }

        return record;
    }

}
