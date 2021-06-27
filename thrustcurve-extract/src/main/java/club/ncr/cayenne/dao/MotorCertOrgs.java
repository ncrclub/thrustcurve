package club.ncr.cayenne.dao;

import club.ncr.cayenne.DAO;
import club.ncr.cayenne.model.MotorCertOrg;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MotorCertOrgs implements DAO<MotorCertOrg> {

    private final ObjectContext ctx;

    public MotorCertOrgs(ObjectContext ctx) {
        this.ctx = ctx;
    }

    public MotorCertOrg createNew(String certificationOrganization) {
        MotorCertOrg record= new MotorCertOrg();
        ctx.registerNewObject(record);
        record.setName(certificationOrganization);
        ctx.commitChanges();
        return record;

    }

    @Override
    public List<MotorCertOrg> get(Expression filter) {
        SelectQuery query= new SelectQuery(MotorCertOrg.class);
        if (filter != null) {
            query.andQualifier(filter);
        }
        query.addOrdering(new Ordering(MotorCertOrg.NAME.getName(), SortOrder.ASCENDING));
        return (List<MotorCertOrg>)ctx.performQuery(query);
    }

    @Override
    public Collection<MotorCertOrg> getAll() {
        return this.get((Expression)null);
    }

    @Override
    public Map<String, MotorCertOrg> getMap(Expression filter) {
        HashMap<String, MotorCertOrg> map= new HashMap<String, MotorCertOrg>();
        for (MotorCertOrg org : get(filter)) {
            map.put(org.getName(), org);
        }
        return map;
    }
}
