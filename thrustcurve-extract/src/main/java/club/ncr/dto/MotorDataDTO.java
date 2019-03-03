package club.ncr.dto;

import club.ncr.cayenne.MotorData;
import org.thrustcurve.api.data.TCMotorData;


public class MotorDataDTO {

    public final String dataUrl;
    public final String infoUrl;
    public final String source;
    public final String license;
    public final String fileExtension;


    // these constructors are only visible to the package on purpose, this is to force the instantiation of a MotorDTO first.
    MotorDataDTO(MotorDTO motor, MotorData data) {
        this.dataUrl = data.getDataUrl();
        this.infoUrl = data.getInfoUrl();
        this.source= data.getSource();
        this.license= data.getLicense();
        this.fileExtension = data.getFormat().getFileExtension();
    }

    MotorDataDTO(MotorDTO motor, TCMotorData data) {
        this.dataUrl = data.getDataUrl();
        this.infoUrl = data.getInfoUrl();
        this.source= data.getSource();
        this.license= data.getLicense();
        this.fileExtension = data.getFormat();
    }
}
