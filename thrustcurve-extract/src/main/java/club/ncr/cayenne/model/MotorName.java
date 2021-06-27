package club.ncr.cayenne.model;

import club.ncr.cayenne.model.auto._MotorName;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.SelectQuery;

import java.util.HashMap;
import java.util.List;

public class MotorName extends _MotorName implements Comparable {
	public int compareTo(Object o) {
		if (o == null) { return 1; }
		if (o instanceof Motor) {
			Motor motor= (Motor) o;
			return compareTo(motor.getCommonName());
		} else if (o instanceof MotorName) {
			MotorName other= (MotorName) o;
			
			int diff= getImpulse().compareTo(other.getImpulse());
			
			if (diff == 0) {
			    try {
					return Integer.parseInt(getName().substring(1)) - Integer.parseInt(other.getName().substring(1));
				} catch (NumberFormatException nfx) {
			    	return getName().compareTo(other.getName());
				}
			} else {
				return diff;
			}
			
		}
		return -1;
	}

	@Override
	public String toString() {
		return this.getName();
	}
}
