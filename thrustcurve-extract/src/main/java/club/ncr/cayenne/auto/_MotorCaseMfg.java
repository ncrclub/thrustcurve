package club.ncr.cayenne.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.cayenne.BaseDataObject;
import org.apache.cayenne.exp.Property;

import club.ncr.cayenne.MotorCase;
import club.ncr.cayenne.MotorMfg;

/**
 * Class _MotorCaseMfg was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _MotorCaseMfg extends BaseDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String ID_PK_COLUMN = "id";

    public static final Property<MotorCase> MOTOR_CASE = Property.create("motorCase", MotorCase.class);
    public static final Property<MotorMfg> MOTOR_MANUFACTURER = Property.create("motorManufacturer", MotorMfg.class);


    protected Object motorCase;
    protected Object motorManufacturer;

    public void setMotorCase(MotorCase motorCase) {
        setToOneTarget("motorCase", motorCase, true);
    }

    public MotorCase getMotorCase() {
        return (MotorCase)readProperty("motorCase");
    }

    public void setMotorManufacturer(MotorMfg motorManufacturer) {
        setToOneTarget("motorManufacturer", motorManufacturer, true);
    }

    public MotorMfg getMotorManufacturer() {
        return (MotorMfg)readProperty("motorManufacturer");
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "motorCase":
                return this.motorCase;
            case "motorManufacturer":
                return this.motorManufacturer;
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
            case "motorCase":
                this.motorCase = val;
                break;
            case "motorManufacturer":
                this.motorManufacturer = val;
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
        out.writeObject(this.motorCase);
        out.writeObject(this.motorManufacturer);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.motorCase = in.readObject();
        this.motorManufacturer = in.readObject();
    }

}