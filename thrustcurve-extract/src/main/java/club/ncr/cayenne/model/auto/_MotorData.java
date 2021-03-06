package club.ncr.cayenne.model.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.cayenne.BaseDataObject;
import org.apache.cayenne.exp.Property;

import club.ncr.cayenne.model.Motor;
import club.ncr.cayenne.model.MotorDataFormat;

/**
 * Class _MotorData was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _MotorData extends BaseDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String ID_PK_COLUMN = "id";

    public static final Property<byte[]> DATA = Property.create("data", byte[].class);
    public static final Property<String> DATA_URL = Property.create("dataUrl", String.class);
    public static final Property<String> INFO_URL = Property.create("infoUrl", String.class);
    public static final Property<String> LICENSE = Property.create("license", String.class);
    public static final Property<String> SOURCE = Property.create("source", String.class);
    public static final Property<Integer> TC_FILE_ID = Property.create("tcFileID", Integer.class);
    public static final Property<MotorDataFormat> FORMAT = Property.create("format", MotorDataFormat.class);
    public static final Property<Motor> MOTOR = Property.create("motor", Motor.class);

    protected byte[] data;
    protected String dataUrl;
    protected String infoUrl;
    protected String license;
    protected String source;
    protected Integer tcFileID;

    protected Object format;
    protected Object motor;

    public void setData(byte[] data) {
        beforePropertyWrite("data", this.data, data);
        this.data = data;
    }

    public byte[] getData() {
        beforePropertyRead("data");
        return this.data;
    }

    public void setDataUrl(String dataUrl) {
        beforePropertyWrite("dataUrl", this.dataUrl, dataUrl);
        this.dataUrl = dataUrl;
    }

    public String getDataUrl() {
        beforePropertyRead("dataUrl");
        return this.dataUrl;
    }

    public void setInfoUrl(String infoUrl) {
        beforePropertyWrite("infoUrl", this.infoUrl, infoUrl);
        this.infoUrl = infoUrl;
    }

    public String getInfoUrl() {
        beforePropertyRead("infoUrl");
        return this.infoUrl;
    }

    public void setLicense(String license) {
        beforePropertyWrite("license", this.license, license);
        this.license = license;
    }

    public String getLicense() {
        beforePropertyRead("license");
        return this.license;
    }

    public void setSource(String source) {
        beforePropertyWrite("source", this.source, source);
        this.source = source;
    }

    public String getSource() {
        beforePropertyRead("source");
        return this.source;
    }

    public void setTcFileID(int tcFileID) {
        beforePropertyWrite("tcFileID", this.tcFileID, tcFileID);
        this.tcFileID = tcFileID;
    }

    public int getTcFileID() {
        beforePropertyRead("tcFileID");
        if(this.tcFileID == null) {
            return 0;
        }
        return this.tcFileID;
    }

    public void setFormat(MotorDataFormat format) {
        setToOneTarget("format", format, true);
    }

    public MotorDataFormat getFormat() {
        return (MotorDataFormat)readProperty("format");
    }

    public void setMotor(Motor motor) {
        setToOneTarget("motor", motor, true);
    }

    public Motor getMotor() {
        return (Motor)readProperty("motor");
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "data":
                return this.data;
            case "dataUrl":
                return this.dataUrl;
            case "infoUrl":
                return this.infoUrl;
            case "license":
                return this.license;
            case "source":
                return this.source;
            case "tcFileID":
                return this.tcFileID;
            case "format":
                return this.format;
            case "motor":
                return this.motor;
            default:
                return super.readPropertyDirectly(propName);
        }
    }

    @Override
    public void writePropertyDirectly(String propName, Object val) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch (propName) {
            case "data":
                this.data = (byte[])val;
                break;
            case "dataUrl":
                this.dataUrl = (String)val;
                break;
            case "infoUrl":
                this.infoUrl = (String)val;
                break;
            case "license":
                this.license = (String)val;
                break;
            case "source":
                this.source = (String)val;
                break;
            case "tcFileID":
                this.tcFileID = (Integer)val;
                break;
            case "format":
                this.format = val;
                break;
            case "motor":
                this.motor = val;
                break;
            default:
                super.writePropertyDirectly(propName, val);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        writeSerialized(out);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        readSerialized(in);
    }

    @Override
    protected void writeState(ObjectOutputStream out) throws IOException {
        super.writeState(out);
        out.writeObject(this.data);
        out.writeObject(this.dataUrl);
        out.writeObject(this.infoUrl);
        out.writeObject(this.license);
        out.writeObject(this.source);
        out.writeObject(this.tcFileID);
        out.writeObject(this.format);
        out.writeObject(this.motor);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.data = (byte[])in.readObject();
        this.dataUrl = (String)in.readObject();
        this.infoUrl = (String)in.readObject();
        this.license = (String)in.readObject();
        this.source = (String)in.readObject();
        this.tcFileID = (Integer)in.readObject();
        this.format = in.readObject();
        this.motor = in.readObject();
    }

}
