package club.ncr.dto;

import club.ncr.cayenne.Motor;

public class MotorDTO {

    public final String designation;
    public final Double weight;
    public final Double burnTime;

    public MotorDTO(Motor motor) {
        this.designation= motor.getDesignation();
        this.weight = motor.getWeight();
        this.burnTime = motor.getBurnTime();
    }
}
