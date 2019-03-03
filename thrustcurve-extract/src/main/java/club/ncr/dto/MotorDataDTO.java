package club.ncr.dto;

import club.ncr.cayenne.Motor;
import club.ncr.cayenne.MotorData;
import org.thrustcurve.api.data.TCMotorData;
import org.thrustcurve.api.data.TCMotorRecord;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MotorDataDTO {

    public final MotorDTO motor;
    public final String dataUrl;
    public final String infoUrl;
    public final String source;
    public final String license;
    public final String fileExtension;
    public final String impulse;
    public final Double burnTime;
    public final Float diameter;


    public MotorDataDTO(Motor motor, MotorData data) {
        this.motor= new MotorDTO(motor);
        this.dataUrl = data.getDataUrl();
        this.infoUrl = data.getInfoUrl();
        this.source= data.getSource();
        this.license= data.getLicense();
        this.fileExtension = data.getFormat().getFileExtension();
        this.impulse= motor.getImpulse().getImpulse();
        this.burnTime = motor.getBurnTime();
        this.diameter= motor.getDiameter().getDiameter();
    }

    public MotorDataDTO(TCMotorRecord motor, TCMotorData data) {
        this.motor= new MotorDTO(motor);
        this.dataUrl = data.getDataUrl();
        this.infoUrl = data.getInfoUrl();
        this.source= data.getSource();
        this.license= data.getLicense();
        this.fileExtension = data.getFormat();
        this.impulse= motor.getImpulseClass();
        this.burnTime = motor.getBurnTime();
        this.diameter= BigDecimal.valueOf(Float.parseFloat(motor.getDiameter())).setScale(1, RoundingMode.HALF_EVEN).floatValue();
    }
}
