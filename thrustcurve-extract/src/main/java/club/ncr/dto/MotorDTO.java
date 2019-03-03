package club.ncr.dto;

import club.ncr.cayenne.Motor;
import org.thrustcurve.api.data.TCMotorRecord;

import java.util.List;
import java.util.stream.Collectors;

public class MotorDTO {

    public final MotorManufacturerDTO manufacturer;
    public final String designation;
    public final String motorCase;
    public final String impulse;
    public final Double weight;
    public final Double burnTime;
    public final Double diameter;
    public final String externalId;
    public final List<MotorDataDTO> data;

    public MotorDTO(Motor motor) {
        this.externalId = motor.getExternalID();
        this.manufacturer= new MotorManufacturerDTO(motor.getManufacturer());
        this.designation= motor.getDesignation();
        this.motorCase= motor.getCase().getName();
        this.impulse= motor.getImpulse().getImpulse();
        this.weight = motor.getWeight();
        this.burnTime = motor.getBurnTime();
        this.diameter= (double)motor.getDiameter().getDiameter();
        this.data= motor.getData().stream().map(d -> new MotorDataDTO(motor, d)).collect(Collectors.toList());
    }

    public MotorDTO(TCMotorRecord motor) {
        this.externalId = motor.getMotorId();
        this.manufacturer= new MotorManufacturerDTO(motor);
        this.designation= motor.getDesignation();
        this.motorCase= motor.getMotorCase();
        this.impulse= motor.getImpulseClass();
        this.weight = motor.getWeight();
        this.burnTime = motor.getBurnTime();
        this.diameter= Double.parseDouble(motor.getDiameter());
        this.data= motor.getData().stream().map(d -> new MotorDataDTO(motor, d)).collect(Collectors.toList());
    }
}
