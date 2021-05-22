package club.ncr.cayenne;

import club.ncr.cayenne.auto._MotorCaseImpulse;
import club.ncr.dto.motor.ImpulseDTO;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.query.SelectQuery;

import java.util.List;
import java.util.stream.Collectors;

public class MotorCaseImpulse extends _MotorCaseImpulse {

    private static final long serialVersionUID = 1L;


    public static List<MotorCaseImpulse> get(DataContext ctx, ImpulseDTO impulse, Float diameter) {

        SelectQuery<MotorCaseImpulse> query = new SelectQuery(MotorCaseImpulse.class);

        if (impulse != null) {
            query.andQualifier(MotorCaseImpulse.MOTOR_IMPULSE.eq(MotorImpulse.get(ctx, impulse)));
        }

        return ((List<MotorCaseImpulse>)ctx.performQuery(query)).stream()
                .filter(mci -> diameter == null || mci.getMotorCase().getMotorDiameter().getDiameter() == diameter)
                .collect(Collectors.toList());
    }
}
