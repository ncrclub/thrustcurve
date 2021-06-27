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
