package club.ncr.dto;

import club.ncr.cayenne.*;
import org.thrustcurve.api.data.TCMotorData;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class MotorCaseDTO implements Comparable<MotorCaseDTO> {

    public final Integer id;
    public final String name;
    public final Float diameter;
    public final MotorManufacturerDTO mfg;

    public MotorCaseDTO(MotorCase motorCase, MotorManufacturerDTO mfg) {
        this.id = motorCase.getId();
        this.name = motorCase.getName();
        MotorDiameter diameter = motorCase.getMotorDiameter();
        this.diameter = diameter == null ? null : diameter.getDiameter();
        this.mfg = mfg;
    }

    public MotorCaseDTO(Motor motor) {
        this(motor.getCase(), new MotorManufacturerDTO(motor.getManufacturer()));
    }

    public MotorCaseDTO(String name, float diameter, MotorManufacturerDTO mfg) {
        this.id = null;
        this.name = name;
        this.diameter = diameter;
        this.mfg = mfg;
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
