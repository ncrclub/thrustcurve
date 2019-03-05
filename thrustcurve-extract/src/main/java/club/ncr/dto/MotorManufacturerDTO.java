package club.ncr.dto;

import club.ncr.cayenne.MotorMfg;
import org.thrustcurve.api.data.TCMotorRecord;

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
}
