package club.ncr.cayenne.model;

import club.ncr.cayenne.model.auto._MotorCaseImpulse;
import club.ncr.dto.ImpulseDTO;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.SelectQuery;

import java.util.List;
import java.util.stream.Collectors;

public class MotorCaseImpulse extends _MotorCaseImpulse {

    private static final long serialVersionUID = 1L;


    public static List<MotorCaseImpulse> get(ObjectContext ctx, ImpulseDTO impulse, Float diameter) {

        SelectQuery<MotorCaseImpulse> query = new SelectQuery(MotorCaseImpulse.class);

        if (impulse != null) {
            query.andQualifier(MotorCaseImpulse.MOTOR_IMPULSE.eq(MotorImpulse.get(ctx, impulse)));
        }

        List<MotorCaseImpulse> all = ((List<MotorCaseImpulse>) ctx.performQuery(query));

        List<MotorCaseImpulse> filtered = all.stream()
                .filter(mci -> diameter == null || mci.getMotorCase().getMotorDiameter().getDiameter() == diameter)
                .collect(Collectors.toList());

        return filtered;
    }
}
