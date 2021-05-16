package club.ncr.dto;

import club.ncr.cayenne.*;
import org.thrustcurve.api.data.TCMotorData;

import java.util.Objects;


public class MotorCaseDTO implements Comparable<MotorCaseDTO> {

    public final String name;
    public final Float diameter;

    public MotorCaseDTO(MotorCase motorCase) {
        this.name = motorCase.getName();
        MotorDiameter diameter = motorCase.getMotorDiameter();
        this.diameter = diameter == null ? null : diameter.getDiameter();
    }

    public MotorCaseDTO(Motor motor) {
        this(motor.getCase());
    }

    @Override
    public int compareTo(MotorCaseDTO o) {
        if (o == null) { return 1; }

        float diff = diameter - o.diameter;
        if (diff == 0) {
            return name.compareTo(o.name);
        }
        return (diff > 1 ? 1 : -1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MotorCaseDTO that = (MotorCaseDTO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(diameter, that.diameter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, diameter);
    }
}
