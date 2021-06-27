package club.ncr.dto;

import club.ncr.cayenne.model.MotorMfg;
import org.thrustcurve.api.data.TCMotorRecord;

import java.util.Objects;

public class MotorManufacturerDTO implements Comparable<MotorManufacturerDTO> {

    public final String name;
    public final String abbreviation;

    public MotorManufacturerDTO(MotorMfg manufacturer) {
       this.name = manufacturer.getName();
       this.abbreviation = manufacturer.getAbbreviation();
    }

    public MotorManufacturerDTO(TCMotorRecord motor) {
        this.name = motor.getManufacturer();
        this.abbreviation = motor.getManufacturerAbbv();
    }

    @Override
    public int compareTo(MotorManufacturerDTO o) {
        return name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MotorManufacturerDTO that = (MotorManufacturerDTO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(abbreviation, that.abbreviation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, abbreviation);
    }
}
