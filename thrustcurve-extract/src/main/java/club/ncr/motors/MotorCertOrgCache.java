package club.ncr.motors;

import club.ncr.cayenne.MotorCertOrg;
import org.apache.cayenne.ObjectContext;

import java.util.Collection;
import java.util.Optional;

public class MotorCertOrgCache extends CayenneRecordCache<String, MotorCertOrg> {

    public MotorCertOrgCache(ObjectContext ctx, boolean autoCreate) {
        super(ctx, autoCreate);
    }

    @Override
    public Collection<MotorCertOrg> getAll() {
        return MotorCertOrg.get(context(), null);
    }

    @Override
    public String key(MotorCertOrg value) {
        return Optional.ofNullable(value.getName()).orElse("");
    }

    public MotorCertOrg get(String name) {
        MotorCertOrg record= super.get(name);

        if (record == null && autoCreate()) {
            record= MotorCertOrg.createNew(name, context());
            put(name, record);
        }

        return record;
    }

}
