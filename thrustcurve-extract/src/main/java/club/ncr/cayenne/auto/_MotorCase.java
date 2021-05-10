package club.ncr.cayenne.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.apache.cayenne.BaseDataObject;
import org.apache.cayenne.exp.Property;

import club.ncr.cayenne.Motor;
import club.ncr.cayenne.MotorCaseImpulse;
import club.ncr.cayenne.MotorDiameter;
import club.ncr.cayenne.MotorMfg;

/**
 * Class _MotorCase was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _MotorCase extends BaseDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String ID_PK_COLUMN = "id";

    public static final Property<String> NAME = Property.create("name", String.class);
    public static final Property<List<MotorCaseImpulse>> MOTOR_CASE_IMPULSES = Property.create("motorCaseImpulses", List.class);
    public static final Property<MotorDiameter> MOTOR_DIAMETER = Property.create("motorDiameter", MotorDiameter.class);
    public static final Property<MotorMfg> MOTOR_MANUFACTURER = Property.create("motorManufacturer", MotorMfg.class);
    public static final Property<List<Motor>> MOTORS = Property.create("motors", List.class);

    protected String name;

    protected Object motorCaseImpulses;
    protected Object motorDiameter;
    protected Object motorManufacturer;
    protected Object motors;

    public void setName(String name) {
        beforePropertyWrite("name", this.name, name);
        this.name = name;
    }

    public String getName() {
        beforePropertyRead("name");
        return this.name;
    }

    public void addToMotorCaseImpulses(MotorCaseImpulse obj) {
        addToManyTarget("motorCaseImpulses", obj, true);
    }

    public void removeFromMotorCaseImpulses(MotorCaseImpulse obj) {
        removeToManyTarget("motorCaseImpulses", obj, true);
    }

    @SuppressWarnings("unchecked")
    public List<MotorCaseImpulse> getMotorCaseImpulses() {
        return (List<MotorCaseImpulse>)readProperty("motorCaseImpulses");
    }

    public void setMotorDiameter(MotorDiameter motorDiameter) {
        setToOneTarget("motorDiameter", motorDiameter, true);
    }

    public MotorDiameter getMotorDiameter() {
        return (MotorDiameter)readProperty("motorDiameter");
    }

    public void setMotorManufacturer(MotorMfg motorManufacturer) {
        setToOneTarget("motorManufacturer", motorManufacturer, true);
    }

    public MotorMfg getMotorManufacturer() {
        return (MotorMfg)readProperty("motorManufacturer");
    }

    public void addToMotors(Motor obj) {
        addToManyTarget("motors", obj, true);
    }

    public void removeFromMotors(Motor obj) {
        removeToManyTarget("motors", obj, true);
    }

    @SuppressWarnings("unchecked")
    public List<Motor> getMotors() {
        return (List<Motor>)readProperty("motors");
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "name":
                return this.name;
            case "motorCaseImpulses":
                return this.motorCaseImpulses;
            case "motorDiameter":
                return this.motorDiameter;
            case "motorManufacturer":
                return this.motorManufacturer;
            case "motors":
                return this.motors;
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
            case "name":
                this.name = (String)val;
                break;
            case "motorCaseImpulses":
                this.motorCaseImpulses = val;
                break;
            case "motorDiameter":
                this.motorDiameter = val;
                break;
            case "motorManufacturer":
                this.motorManufacturer = val;
                break;
            case "motors":
                this.motors = val;
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
        out.writeObject(this.name);
        out.writeObject(this.motorCaseImpulses);
        out.writeObject(this.motorDiameter);
        out.writeObject(this.motorManufacturer);
        out.writeObject(this.motors);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.name = (String)in.readObject();
        this.motorCaseImpulses = in.readObject();
        this.motorDiameter = in.readObject();
        this.motorManufacturer = in.readObject();
        this.motors = in.readObject();
    }

}
