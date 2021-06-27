package club.ncr.motors;

import club.ncr.cayenne.model.Motor;
import club.ncr.cayenne.model.MotorMfg;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;

import java.util.List;

public class QueryFilters {

    public static void motorManufacturers(List<MotorMfg> motorMfgs, SelectQuery query) {
        if (motorMfgs != null && !motorMfgs.isEmpty()) {
            Expression manufacturers = null;
            for (MotorMfg mfg : motorMfgs) {
                if (manufacturers == null) {
                    manufacturers = ExpressionFactory.or(Motor.MANUFACTURER.eq(mfg));
                } else {
                    manufacturers = manufacturers.orExp(Motor.MANUFACTURER.eq(mfg));
                }
            }
            query.andQualifier(manufacturers);
        }
    }

}
