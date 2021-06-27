package club.ncr.cayenne.model;

import club.ncr.cayenne.model.auto._MotorCase;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;

import java.util.HashMap;
import java.util.List;

public class MotorCase extends _MotorCase implements Comparable<MotorCase> {


	@Deprecated
	public static MotorCase getOrCreate(String name, MotorMfg mfg, MotorDiameter diameter, MotorImpulse impulse, ObjectContext ctx) {

	    MotorCase exists = MotorCase.get(ctx, MotorCase.NAME.eq(name))
				.stream()
				.filter(c -> c.getMotorDiameter().equals(diameter))
				// .filter(c -> c.getMotors().stream().filter(m -> m.getManufacturer().equals(mfg)).findFirst().isPresent())
				.findFirst().orElse(null);

	    if (exists != null) {

	        MotorImpulse impulseRef = exists.getMotorCaseImpulses().stream()
					.map(mci -> mci.getMotorImpulse())
					.filter(i -> impulse.equals(i))
                    .findFirst().orElse(null);

	        if (impulseRef == null) {
				MotorCaseImpulse mci = new MotorCaseImpulse();
				mci.setMotorImpulse(impulse);
				exists.addToMotorCaseImpulses(mci);
				ctx.commitChanges();
			}

	    	return exists;
		}

		MotorCase record= new MotorCase();
		ctx.registerNewObject(record);
		record.setName(name);

		record.setMotorDiameter(diameter);

		MotorCaseImpulse mci = new MotorCaseImpulse();
		mci.setMotorImpulse(impulse);
		mci.setMotorCase(record);
		record.addToMotorCaseImpulses(mci);

		MotorCaseMfg mcm = new MotorCaseMfg();
		mcm.setMotorManufacturer(mfg);
		mcm.setMotorCase(record);
		record.addToMotorCaseManufacturer(mcm);

		ctx.commitChanges();
		
		return record;
	}

	@Deprecated
	public static List<MotorCase> get(ObjectContext ctx, Expression filter) {
		SelectQuery query= new SelectQuery(MotorCase.class);
		if (filter != null) {
			query.andQualifier(filter);
		}
		query.addOrdering(new Ordering(MotorCase.NAME.getName(), SortOrder.ASCENDING_INSENSITIVE));
		return (List<MotorCase>)ctx.performQuery(query);
	}

	@Deprecated
	public static HashMap<String, MotorCase> getMap(ObjectContext ctx, Expression filter) {
		HashMap<String, MotorCase> map= new HashMap<String, MotorCase>();
		for (MotorCase obj : get(ctx, filter)) {
			map.put(obj.getName(), obj);
		}
		return map;
	}

	@Override
	public int compareTo(MotorCase o) {
	    if (o == null) {
	    	return 1;
		}
	    return getName().compareTo(o.getName());
	}

	public String uuid() {
		return getName() + "-"+ getMotorDiameter().toString();
	}

}
