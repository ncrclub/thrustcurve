package club.ncr.dto;

import club.ncr.cayenne.model.MotorData;
import org.thrustcurve.api.data.TCMotorData;


public class MotorDataDTO {

    public final String dataUrl;
    public final String infoUrl;
    public final String source;
    public final String license;
    public final String fileExtension;
    public final String filename;
    public final String format;

    private static String nameOf(MotorDTO motor) {
        if (motor.designation == null) {
            return motor.name.replaceAll("[^A-Za-z0-9_.-]", "_");
        }
        return motor.designation.replaceAll("[^A-Za-z0-9_.-]", "_");
    }

    private static String toFilename(MotorDTO motor, String source, String license, String fileExtension) {
        return nameOf(motor)
                +"-"+ motor.manufacturer.abbreviation.replaceAll("[^A-Za-z0-9_.-]", "_")
                +"-"+ source
                + (license != null ? "-"+ license : "")
                +"."+ fileExtension;
    }

    // these constructors are only visible to the package on purpose, this is to force the instantiation of a MotorDTO first.
    MotorDataDTO(MotorDTO motor, MotorData data) {
        this.dataUrl = data.getDataUrl();
        this.infoUrl = data.getInfoUrl();
        this.source= data.getSource();
        this.license= data.getLicense();
        this.fileExtension = data.getFormat().getFileExtension();
        this.filename = toFilename(motor, data.getSource(), data.getLicense(), data.getFormat().getFileExtension());
        this.format = data.getFormat().getName();
    }

    MotorDataDTO(MotorDTO motor, TCMotorData data) {
        this.dataUrl = data.getDataUrl();
        this.infoUrl = data.getInfoUrl();
        this.source= data.getSource();
        this.license= data.getLicense();
        this.fileExtension = data.getFormat();
        this.filename = toFilename(motor, data.getSource(), data.getLicense(), data.getFormat());
        this.format = data.getFormat();
    }
}
