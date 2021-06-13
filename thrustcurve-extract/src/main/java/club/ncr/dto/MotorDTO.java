package club.ncr.dto;

import club.ncr.cayenne.Motor;
import org.thrustcurve.api.data.TCMotorRecord;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MotorDTO implements Comparable<MotorDTO> {

    public final String name;
    public final String brandName;
    public final String designation;
    public final String identifier;
    public final MotorCaseDTO motor_case;
    public final String impulse;
    public final Double total_impulse_ns;
    public final Double weight;
    public final Double burnTime;
    public final Double averageThrust;
    public final Double maxThrust;
    public final Float diameter;
    public final Double length;
    public final String externalId;
    public final String cert_org;
    public final PropellantDTO propellant;
    public final String type;
    public final MotorManufacturerDTO manufacturer;
    public final List<MotorDataDTO> data;
    public final String lastUpdated;

    private static String firstNotNull(String s1, String s2) {
        return (s1 != null ? s1 : s2);
    }

    private static String toIdentifier(Motor motor) {
        return motor.getDesignation() +" ["+ motor.getManufacturer().getAbbreviation() +"]";
    }

    private static String toIdentifier(TCMotorRecord motor) {
        return motor.getDesignation() +" ["+ motor.getManufacturerAbbv() +"]";
    }

    public MotorDTO(Motor motor, boolean withData) {
        this.name= firstNotNull(motor.getCommonName().getName(), motor.getBrandName());
        this.brandName= firstNotNull(motor.getBrandName(), motor.getDesignation());
        this.identifier= toIdentifier(motor);
        this.type = motor.getType().getName();
        this.externalId = motor.getExternalId();
        this.manufacturer= new MotorManufacturerDTO(motor.getManufacturer());
        this.lastUpdated = motor.getLastUpdated().toString();
        this.designation= motor.getDesignation();
        this.length= motor.getLength();
        this.impulse= motor.getImpulse().getImpulse();
        this.weight= motor.getWeight();
        this.total_impulse_ns = motor.getTotalImpulseNs();
        this.burnTime= motor.getBurnTime();
        this.diameter= motor.getDiameter().getDiameter();
        this.cert_org = motor.getCertificationOrganization().getName();
        this.motor_case = new MotorCaseDTO(motor);
        this.propellant= new PropellantDTO(motor.getPropellant());
        this.averageThrust = motor.getThrustAvg();
        this.maxThrust = motor.getThrustMax();
        if (withData) {
            this.data = motor.getData().stream()
                    .filter(d -> d != null)
                    .filter(d -> d.getFormat() != null)
                    .filter(d -> d.getFormat().getFileExtension() != null)
                    .map(d -> new MotorDataDTO(this, d))
                    .filter(d -> d.filename != null)
                    .collect(Collectors.toList());
        } else {
            this.data = Collections.emptyList();
        }
    }

    public MotorDTO(TCMotorRecord motor, boolean withData) {
        this.name= firstNotNull(motor.getCommonName(), motor.getBrandName());
        this.brandName= firstNotNull(motor.getBrandName(), motor.getDesignation());
        this.identifier= toIdentifier(motor);
        this.type = motor.getType();
        this.externalId = motor.getMotorId();
        this.manufacturer= new MotorManufacturerDTO(motor);
        this.designation= motor.getDesignation();
        this.length= motor.getLength();
        this.impulse= motor.getImpulseClass();
        this.total_impulse_ns = motor.getTotalImpulse();
        this.weight = motor.getWeight();
        this.burnTime = motor.getBurnTime();
        this.diameter= Float.parseFloat(motor.getDiameter());
        this.cert_org = motor.getCertificationOrganization();
        this.motor_case = new MotorCaseDTO(motor.getMotorCase(), this.diameter, this.manufacturer);
        this.propellant= new PropellantDTO(motor.getPropellant(), motor.getType());
        this.averageThrust = motor.getAverageThrust();
        this.maxThrust = motor.getMaxThrust();
        this.lastUpdated = motor.getUpdated();
        if (withData) {
            this.data= motor.getData().stream()
                .filter(d -> d != null)
                .filter(d -> d.getFormat() != null)
                .map(d -> new MotorDataDTO(this, d))
                .filter(d -> d.filename != null)
                .collect(Collectors.toList());
        } else {
            this.data = Collections.emptyList();
        }
    }

    @Override
    public int compareTo(MotorDTO o) {
        int diff;

        if ((diff = impulse.compareTo(o.impulse)) != 0) { return diff; }

        try {
            int thrust = Integer.parseInt(name.substring(1));
            int oThrust = Integer.parseInt(o.name.substring(1));
            return thrust - oThrust;
        } catch (NumberFormatException skip) { }

        if (averageThrust != null && averageThrust > 0) {
            if (o.averageThrust != null && o.averageThrust > 0) {
                return (int) (averageThrust - o.averageThrust);
            }
        }
        if (total_impulse_ns != null && total_impulse_ns > 0) {
            if (o.total_impulse_ns != null && o.total_impulse_ns > 0) {
                return (int) (total_impulse_ns - o.total_impulse_ns);
            }
        }
        if ((diff = (int)(diameter - o.diameter)) != 0) { return diff; }
        if ((diff = name.compareTo(o.name)) != 0) { return diff; }
        if ((diff = manufacturer.compareTo(o.manufacturer)) != 0) { return diff; }
        if ((diff = (int)(maxThrust - o.maxThrust)) != 0) { return diff; }
        if ((diff = (int)(weight - o.weight)) != 0) { return diff; }
        if ((diff = (int)(burnTime - o.burnTime)) != 0) { return diff; }

        return diff;
    }
}
