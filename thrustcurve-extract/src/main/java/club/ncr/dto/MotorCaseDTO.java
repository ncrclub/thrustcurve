package club.ncr.dto;

import club.ncr.cayenne.*;
import org.thrustcurve.api.data.TCMotorData;


public class MotorCaseDTO {

    public final String name;
    public final Float diameter;
    public final MotorManufacturerDTO manufacturer;

    public MotorCaseDTO(MotorCase motorCase) {
        this.name = motorCase.getName();
        MotorDiameter diameter = motorCase.getMotorDiameter();
        MotorMfg manufacturer = motorCase.getMotorManufacturer();
        this.diameter = diameter == null ? null : diameter.getDiameter();
        this.manufacturer = manufacturer == null ? null : new MotorManufacturerDTO(manufacturer);
    }

    public MotorCaseDTO(Motor motor) {
        this(motor.getCase());
    }
}
