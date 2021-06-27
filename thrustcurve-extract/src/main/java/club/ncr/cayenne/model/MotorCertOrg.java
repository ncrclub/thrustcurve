package club.ncr.cayenne.model;

import club.ncr.cayenne.model.auto._MotorCertOrg;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;

import java.util.HashMap;
import java.util.List;

public class MotorCertOrg extends _MotorCertOrg {

	public static MotorCertOrg createNew(String certificationOrganization, ObjectContext ctx) {
		MotorCertOrg record= new MotorCertOrg();
		ctx.registerNewObject(record);
		record.setName(certificationOrganization);
		ctx.commitChanges();
		return record;
		
	}

	public static List<MotorCertOrg> get(ObjectContext ctx, Expression filter) {
		SelectQuery query= new SelectQuery(MotorCertOrg.class);
		if (filter != null) {
			query.andQualifier(filter);
		}
		query.addOrdering(new Ordering(MotorCertOrg.NAME.getName(), SortOrder.ASCENDING));
		return (List<MotorCertOrg>)ctx.performQuery(query);
	}
	
	public static HashMap<String, MotorCertOrg> getNameMap(ObjectContext ctx, Expression filter) {
		HashMap<String, MotorCertOrg> map= new HashMap<String, MotorCertOrg>();
		for (MotorCertOrg org : get(ctx, filter)) {
			map.put(org.getName(), org);
		}
		return map;
	}
}
