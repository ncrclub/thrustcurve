package club.ncr.dto;

import club.ncr.cayenne.Motor;
import club.ncr.cayenne.MotorData;
import org.thrustcurve.api.data.TCMotorData;
import org.thrustcurve.api.data.TCMotorRecord;

public class MotorDataDTO {

    public final String designation;
    public final String manufacturer;
    public final String url;
    public final String fileExtension;
    public final String impulse;
    public final Double weight;
    public final Double burnTime;
    public final Double diameter;


    public MotorDataDTO(Motor motor, MotorData data) {
        this.url = data.getDataUrl();
        this.fileExtension = data.getFormat().getFileExtension();
        this.designation= motor.getDesignation();
        this.manufacturer= motor.getManufacturer().getName();
        this.impulse= motor.getImpulse().getImpulse();
        this.weight = motor.getWeight();
        this.burnTime = motor.getBurnTime();
        this.diameter= (double)motor.getDiameter().getDiameter();
    }

    public MotorDataDTO(TCMotorRecord motor, TCMotorData data) {
        this.url = data.getDataUrl();
        this.fileExtension = data.getFormat();
        this.designation= motor.getDesignation();
        this.manufacturer= motor.getManufacturer();
        this.impulse= motor.getImpulseClass();
        this.weight = motor.getWeight();
        this.burnTime = motor.getBurnTime();
        this.diameter= Double.parseDouble(motor.getDiameter());
    }
}
