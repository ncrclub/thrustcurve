package club.ncr.dto;

import club.ncr.cayenne.Motor;
import org.thrustcurve.api.data.TCMotorRecord;

import java.util.List;
import java.util.stream.Collectors;

public class MotorDTO implements Comparable<MotorDTO> {

    public final String name;
    public final String brandName;
    public final String designation;
    public final String identifier;
    public final String motorCase;
    public final String impulse;
    public final Double totalImpulse;
    public final Double weight;
    public final Double burnTime;
    public final Double averageThrust;
    public final Double maxThrust;
    public final Float diameter;
    public final Double length;
    public final String externalId;
    public final String propellant;
    public final MotorManufacturerDTO manufacturer;
    public final List<MotorDataDTO> data;

    private static String firstNotNull(String s1, String s2) {
        return (s1 != null ? s1 : s2);
    }

    private static String toIdentifier(Motor motor) {
        return motor.getDesignation() +" ["+ motor.getManufacturer().getAbbreviation() +"]";
    }

    private static String toIdentifier(TCMotorRecord motor) {
        return motor.getDesignation() +" ["+ motor.getManufacturerAbbv() +"]";
    }

    public MotorDTO(Motor motor) {
        this.name= firstNotNull(motor.getCommonName().getName(), motor.getBrandName());
        this.brandName= firstNotNull(motor.getBrandName(), motor.getDesignation());
        this.identifier= toIdentifier(motor);
        this.length= motor.getLength();
        this.externalId = motor.getExternalID();
        this.manufacturer= new MotorManufacturerDTO(motor.getManufacturer());
        this.designation= motor.getDesignation();
        this.motorCase= motor.getCase().getName();
        this.impulse= motor.getImpulse().getImpulse();
        this.weight= motor.getWeight();
        this.totalImpulse = motor.getTotalImpulseNs();
        this.burnTime= motor.getBurnTime();
        this.diameter= motor.getDiameter().getDiameter();
        this.propellant= motor.getPropellant().getName();
        this.averageThrust = motor.getThrustAvg();
        this.maxThrust = motor.getThrustMax();
        this.data= motor.getData().stream()
                .filter(d -> d != null)
                .filter(d -> d.getFormat() != null)
                .filter(d -> d.getFormat().getFileExtension() != null)
                .map(d -> new MotorDataDTO(this, d))
                .filter(d -> d.filename != null)
                .collect(Collectors.toList());
    }

    public MotorDTO(TCMotorRecord motor) {
        this.name= firstNotNull(motor.getCommonName(), motor.getBrandName());
        this.brandName= firstNotNull(motor.getBrandName(), motor.getDesignation());
        this.identifier= toIdentifier(motor);
        this.externalId = motor.getMotorId();
        this.manufacturer= new MotorManufacturerDTO(motor);
        this.designation= motor.getDesignation();
        this.length= motor.getLength();
        this.motorCase= motor.getMotorCase();
        this.impulse= motor.getImpulseClass();
        this.totalImpulse = motor.getTotalImpulse();
        this.weight = motor.getWeight();
        this.burnTime = motor.getBurnTime();
        this.diameter= Float.parseFloat(motor.getDiameter());
        this.propellant= motor.getPropellant();
        this.averageThrust = motor.getAverageThrust();
        this.maxThrust = motor.getMaxThrust();
        this.data= motor.getData().stream()
                .filter(d -> d != null)
                .filter(d -> d.getFormat() != null)
                .map(d -> new MotorDataDTO(this, d))
                .filter(d -> d.filename != null)
                .collect(Collectors.toList());
    }

    @Override
    public int compareTo(MotorDTO o) {
        int diff;

        if ((diff = (int)(diameter - o.diameter)) != 0) { return diff; }
        if ((diff = impulse.compareTo(o.impulse)) != 0) { return diff; }
        if ((diff = name.compareTo(o.name)) != 0) { return diff; }
        if ((diff = manufacturer.compareTo(o.manufacturer)) != 0) { return diff; }
        if ((diff = (int)(maxThrust - o.maxThrust)) != 0) { return diff; }
        if ((diff = (int)(weight - o.weight)) != 0) { return diff; }
        if ((diff = (int)(burnTime - o.burnTime)) != 0) { return diff; }

        return diff;
    }
}
