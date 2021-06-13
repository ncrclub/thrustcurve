package club.ncr.dto;

import club.ncr.cayenne.MotorName;
import club.ncr.dto.motor.ImpulseDTO;

import java.util.Objects;


public class MotorNameDTO implements Comparable<MotorNameDTO> {

    public final int id;
    public final String name;
    public final Integer average_thrust;
    public final ImpulseDTO impulse;

    public MotorNameDTO(MotorName name) {
        this.id = name.getId();
        this.name = name.getName();
        this.impulse = ImpulseDTO.valueOf(name.getImpulse());
        Integer avgThrust = null;
        try {
            avgThrust = Integer.parseInt(this.name.substring(1));
        } catch (NumberFormatException nfx) { }
        this.average_thrust = avgThrust;
    }

    @Override
    public int compareTo(MotorNameDTO o) {
        if (o == null) { return 1; }

        float diff = impulse.compareTo(o.impulse);
        if (diff == 0) {
            if (average_thrust != null && o.average_thrust != null) {
                return average_thrust - o.average_thrust;
            }
            return name.compareTo(o.name);
        }
        return (diff > 1 ? 1 : -1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MotorNameDTO that = (MotorNameDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
