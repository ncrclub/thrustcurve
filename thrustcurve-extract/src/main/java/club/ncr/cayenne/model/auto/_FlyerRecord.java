package club.ncr.cayenne.model.auto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import org.apache.cayenne.BaseDataObject;
import org.apache.cayenne.exp.Property;

import club.ncr.cayenne.model.FlightLog;

/**
 * Class _FlyerRecord was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _FlyerRecord extends BaseDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String ID_PK_COLUMN = "id";

    public static final Property<String> ADDRESS1 = Property.create("address1", String.class);
    public static final Property<String> ADDRESS2 = Property.create("address2", String.class);
    public static final Property<String> ADDRESS_CITY = Property.create("addressCity", String.class);
    public static final Property<String> ADDRESS_STATE = Property.create("addressState", String.class);
    public static final Property<String> ADDRESS_ZIP = Property.create("addressZip", String.class);
    public static final Property<String> AFFILIATION = Property.create("affiliation", String.class);
    public static final Property<Short> AGE = Property.create("age", Short.class);
    public static final Property<Short> CERT_LEVEL = Property.create("certLevel", Short.class);
    public static final Property<Integer> CONTACT_ID = Property.create("contactId", Integer.class);
    public static final Property<Date> CREATED_DATE = Property.create("createdDate", Date.class);
    public static final Property<String> DUES_PAID = Property.create("duesPaid", String.class);
    public static final Property<Date> DUES_PAID_DATE = Property.create("duesPaidDate", Date.class);
    public static final Property<String> EMAIL_ADDRESS = Property.create("emailAddress", String.class);
    public static final Property<Integer> EXTERNAL_USER_ID = Property.create("externalUserId", Integer.class);
    public static final Property<String> FIRST_NAME = Property.create("firstName", String.class);
    public static final Property<String> HOME_PHONE = Property.create("homePhone", String.class);
    public static final Property<Long> ID = Property.create("id", Long.class);
    public static final Property<String> LAST_NAME = Property.create("lastName", String.class);
    public static final Property<String> LCO_INITIALS = Property.create("lcoInitials", String.class);
    public static final Property<Short> MEMBER_TYPE = Property.create("memberType", Short.class);
    public static final Property<String> MIDDLE_INITIAL = Property.create("middleInitial", String.class);
    public static final Property<Integer> MIGRATION_ID = Property.create("migrationId", Integer.class);
    public static final Property<String> MOBILE_PHONE = Property.create("mobilePhone", String.class);
    public static final Property<String> NAR_NUMBER = Property.create("narNumber", String.class);
    public static final Property<String> NOTES = Property.create("notes", String.class);
    public static final Property<String> PAID_THRU = Property.create("paidThru", String.class);
    public static final Property<Date> PAID_THRU_DATE = Property.create("paidThruDate", Date.class);
    public static final Property<String> POST_EMAIL = Property.create("postEmail", String.class);
    public static final Property<String> TRIPOLI_NUMBER = Property.create("tripoliNumber", String.class);
    public static final Property<Integer> USER_ID = Property.create("userId", Integer.class);
    public static final Property<String> USERNAME = Property.create("username", String.class);
    public static final Property<String> WORK_PHONE = Property.create("workPhone", String.class);
    public static final Property<FlightLog> FLIGHTS = Property.create("flights", FlightLog.class);

    protected String address1;
    protected String address2;
    protected String addressCity;
    protected String addressState;
    protected String addressZip;
    protected String affiliation;
    protected Short age;
    protected Short certLevel;
    protected Integer contactId;
    protected Date createdDate;
    protected String duesPaid;
    protected Date duesPaidDate;
    protected String emailAddress;
    protected Integer externalUserId;
    protected String firstName;
    protected String homePhone;
    protected Long id;
    protected String lastName;
    protected String lcoInitials;
    protected Short memberType;
    protected String middleInitial;
    protected Integer migrationId;
    protected String mobilePhone;
    protected String narNumber;
    protected String notes;
    protected String paidThru;
    protected Date paidThruDate;
    protected String postEmail;
    protected String tripoliNumber;
    protected Integer userId;
    protected String username;
    protected String workPhone;

    protected Object flights;

    public void setAddress1(String address1) {
        beforePropertyWrite("address1", this.address1, address1);
        this.address1 = address1;
    }

    public String getAddress1() {
        beforePropertyRead("address1");
        return this.address1;
    }

    public void setAddress2(String address2) {
        beforePropertyWrite("address2", this.address2, address2);
        this.address2 = address2;
    }

    public String getAddress2() {
        beforePropertyRead("address2");
        return this.address2;
    }

    public void setAddressCity(String addressCity) {
        beforePropertyWrite("addressCity", this.addressCity, addressCity);
        this.addressCity = addressCity;
    }

    public String getAddressCity() {
        beforePropertyRead("addressCity");
        return this.addressCity;
    }

    public void setAddressState(String addressState) {
        beforePropertyWrite("addressState", this.addressState, addressState);
        this.addressState = addressState;
    }

    public String getAddressState() {
        beforePropertyRead("addressState");
        return this.addressState;
    }

    public void setAddressZip(String addressZip) {
        beforePropertyWrite("addressZip", this.addressZip, addressZip);
        this.addressZip = addressZip;
    }

    public String getAddressZip() {
        beforePropertyRead("addressZip");
        return this.addressZip;
    }

    public void setAffiliation(String affiliation) {
        beforePropertyWrite("affiliation", this.affiliation, affiliation);
        this.affiliation = affiliation;
    }

    public String getAffiliation() {
        beforePropertyRead("affiliation");
        return this.affiliation;
    }

    public void setAge(Short age) {
        beforePropertyWrite("age", this.age, age);
        this.age = age;
    }

    public Short getAge() {
        beforePropertyRead("age");
        return this.age;
    }

    public void setCertLevel(Short certLevel) {
        beforePropertyWrite("certLevel", this.certLevel, certLevel);
        this.certLevel = certLevel;
    }

    public Short getCertLevel() {
        beforePropertyRead("certLevel");
        return this.certLevel;
    }

    public void setContactId(Integer contactId) {
        beforePropertyWrite("contactId", this.contactId, contactId);
        this.contactId = contactId;
    }

    public Integer getContactId() {
        beforePropertyRead("contactId");
        return this.contactId;
    }

    public void setCreatedDate(Date createdDate) {
        beforePropertyWrite("createdDate", this.createdDate, createdDate);
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        beforePropertyRead("createdDate");
        return this.createdDate;
    }

    public void setDuesPaid(String duesPaid) {
        beforePropertyWrite("duesPaid", this.duesPaid, duesPaid);
        this.duesPaid = duesPaid;
    }

    public String getDuesPaid() {
        beforePropertyRead("duesPaid");
        return this.duesPaid;
    }

    public void setDuesPaidDate(Date duesPaidDate) {
        beforePropertyWrite("duesPaidDate", this.duesPaidDate, duesPaidDate);
        this.duesPaidDate = duesPaidDate;
    }

    public Date getDuesPaidDate() {
        beforePropertyRead("duesPaidDate");
        return this.duesPaidDate;
    }

    public void setEmailAddress(String emailAddress) {
        beforePropertyWrite("emailAddress", this.emailAddress, emailAddress);
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        beforePropertyRead("emailAddress");
        return this.emailAddress;
    }

    public void setExternalUserId(Integer externalUserId) {
        beforePropertyWrite("externalUserId", this.externalUserId, externalUserId);
        this.externalUserId = externalUserId;
    }

    public Integer getExternalUserId() {
        beforePropertyRead("externalUserId");
        return this.externalUserId;
    }

    public void setFirstName(String firstName) {
        beforePropertyWrite("firstName", this.firstName, firstName);
        this.firstName = firstName;
    }

    public String getFirstName() {
        beforePropertyRead("firstName");
        return this.firstName;
    }

    public void setHomePhone(String homePhone) {
        beforePropertyWrite("homePhone", this.homePhone, homePhone);
        this.homePhone = homePhone;
    }

    public String getHomePhone() {
        beforePropertyRead("homePhone");
        return this.homePhone;
    }

    public void setId(Long id) {
        beforePropertyWrite("id", this.id, id);
        this.id = id;
    }

    public Long getId() {
        beforePropertyRead("id");
        return this.id;
    }

    public void setLastName(String lastName) {
        beforePropertyWrite("lastName", this.lastName, lastName);
        this.lastName = lastName;
    }

    public String getLastName() {
        beforePropertyRead("lastName");
        return this.lastName;
    }

    public void setLcoInitials(String lcoInitials) {
        beforePropertyWrite("lcoInitials", this.lcoInitials, lcoInitials);
        this.lcoInitials = lcoInitials;
    }

    public String getLcoInitials() {
        beforePropertyRead("lcoInitials");
        return this.lcoInitials;
    }

    public void setMemberType(Short memberType) {
        beforePropertyWrite("memberType", this.memberType, memberType);
        this.memberType = memberType;
    }

    public Short getMemberType() {
        beforePropertyRead("memberType");
        return this.memberType;
    }

    public void setMiddleInitial(String middleInitial) {
        beforePropertyWrite("middleInitial", this.middleInitial, middleInitial);
        this.middleInitial = middleInitial;
    }

    public String getMiddleInitial() {
        beforePropertyRead("middleInitial");
        return this.middleInitial;
    }

    public void setMigrationId(Integer migrationId) {
        beforePropertyWrite("migrationId", this.migrationId, migrationId);
        this.migrationId = migrationId;
    }

    public Integer getMigrationId() {
        beforePropertyRead("migrationId");
        return this.migrationId;
    }

    public void setMobilePhone(String mobilePhone) {
        beforePropertyWrite("mobilePhone", this.mobilePhone, mobilePhone);
        this.mobilePhone = mobilePhone;
    }

    public String getMobilePhone() {
        beforePropertyRead("mobilePhone");
        return this.mobilePhone;
    }

    public void setNarNumber(String narNumber) {
        beforePropertyWrite("narNumber", this.narNumber, narNumber);
        this.narNumber = narNumber;
    }

    public String getNarNumber() {
        beforePropertyRead("narNumber");
        return this.narNumber;
    }

    public void setNotes(String notes) {
        beforePropertyWrite("notes", this.notes, notes);
        this.notes = notes;
    }

    public String getNotes() {
        beforePropertyRead("notes");
        return this.notes;
    }

    public void setPaidThru(String paidThru) {
        beforePropertyWrite("paidThru", this.paidThru, paidThru);
        this.paidThru = paidThru;
    }

    public String getPaidThru() {
        beforePropertyRead("paidThru");
        return this.paidThru;
    }

    public void setPaidThruDate(Date paidThruDate) {
        beforePropertyWrite("paidThruDate", this.paidThruDate, paidThruDate);
        this.paidThruDate = paidThruDate;
    }

    public Date getPaidThruDate() {
        beforePropertyRead("paidThruDate");
        return this.paidThruDate;
    }

    public void setPostEmail(String postEmail) {
        beforePropertyWrite("postEmail", this.postEmail, postEmail);
        this.postEmail = postEmail;
    }

    public String getPostEmail() {
        beforePropertyRead("postEmail");
        return this.postEmail;
    }

    public void setTripoliNumber(String tripoliNumber) {
        beforePropertyWrite("tripoliNumber", this.tripoliNumber, tripoliNumber);
        this.tripoliNumber = tripoliNumber;
    }

    public String getTripoliNumber() {
        beforePropertyRead("tripoliNumber");
        return this.tripoliNumber;
    }

    public void setUserId(Integer userId) {
        beforePropertyWrite("userId", this.userId, userId);
        this.userId = userId;
    }

    public Integer getUserId() {
        beforePropertyRead("userId");
        return this.userId;
    }

    public void setUsername(String username) {
        beforePropertyWrite("username", this.username, username);
        this.username = username;
    }

    public String getUsername() {
        beforePropertyRead("username");
        return this.username;
    }

    public void setWorkPhone(String workPhone) {
        beforePropertyWrite("workPhone", this.workPhone, workPhone);
        this.workPhone = workPhone;
    }

    public String getWorkPhone() {
        beforePropertyRead("workPhone");
        return this.workPhone;
    }

    public void setFlights(FlightLog flights) {
        setToOneTarget("flights", flights, true);
    }

    public FlightLog getFlights() {
        return (FlightLog)readProperty("flights");
    }

    @Override
    public Object readPropertyDirectly(String propName) {
        if(propName == null) {
            throw new IllegalArgumentException();
        }

        switch(propName) {
            case "address1":
                return this.address1;
            case "address2":
                return this.address2;
            case "addressCity":
                return this.addressCity;
            case "addressState":
                return this.addressState;
            case "addressZip":
                return this.addressZip;
            case "affiliation":
                return this.affiliation;
            case "age":
                return this.age;
            case "certLevel":
                return this.certLevel;
            case "contactId":
                return this.contactId;
            case "createdDate":
                return this.createdDate;
            case "duesPaid":
                return this.duesPaid;
            case "duesPaidDate":
                return this.duesPaidDate;
            case "emailAddress":
                return this.emailAddress;
            case "externalUserId":
                return this.externalUserId;
            case "firstName":
                return this.firstName;
            case "homePhone":
                return this.homePhone;
            case "id":
                return this.id;
            case "lastName":
                return this.lastName;
            case "lcoInitials":
                return this.lcoInitials;
            case "memberType":
                return this.memberType;
            case "middleInitial":
                return this.middleInitial;
            case "migrationId":
                return this.migrationId;
            case "mobilePhone":
                return this.mobilePhone;
            case "narNumber":
                return this.narNumber;
            case "notes":
                return this.notes;
            case "paidThru":
                return this.paidThru;
            case "paidThruDate":
                return this.paidThruDate;
            case "postEmail":
                return this.postEmail;
            case "tripoliNumber":
                return this.tripoliNumber;
            case "userId":
                return this.userId;
            case "username":
                return this.username;
            case "workPhone":
                return this.workPhone;
            case "flights":
                return this.flights;
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
            case "address1":
                this.address1 = (String)val;
                break;
            case "address2":
                this.address2 = (String)val;
                break;
            case "addressCity":
                this.addressCity = (String)val;
                break;
            case "addressState":
                this.addressState = (String)val;
                break;
            case "addressZip":
                this.addressZip = (String)val;
                break;
            case "affiliation":
                this.affiliation = (String)val;
                break;
            case "age":
                this.age = (Short)val;
                break;
            case "certLevel":
                this.certLevel = (Short)val;
                break;
            case "contactId":
                this.contactId = (Integer)val;
                break;
            case "createdDate":
                this.createdDate = (Date)val;
                break;
            case "duesPaid":
                this.duesPaid = (String)val;
                break;
            case "duesPaidDate":
                this.duesPaidDate = (Date)val;
                break;
            case "emailAddress":
                this.emailAddress = (String)val;
                break;
            case "externalUserId":
                this.externalUserId = (Integer)val;
                break;
            case "firstName":
                this.firstName = (String)val;
                break;
            case "homePhone":
                this.homePhone = (String)val;
                break;
            case "id":
                this.id = (Long)val;
                break;
            case "lastName":
                this.lastName = (String)val;
                break;
            case "lcoInitials":
                this.lcoInitials = (String)val;
                break;
            case "memberType":
                this.memberType = (Short)val;
                break;
            case "middleInitial":
                this.middleInitial = (String)val;
                break;
            case "migrationId":
                this.migrationId = (Integer)val;
                break;
            case "mobilePhone":
                this.mobilePhone = (String)val;
                break;
            case "narNumber":
                this.narNumber = (String)val;
                break;
            case "notes":
                this.notes = (String)val;
                break;
            case "paidThru":
                this.paidThru = (String)val;
                break;
            case "paidThruDate":
                this.paidThruDate = (Date)val;
                break;
            case "postEmail":
                this.postEmail = (String)val;
                break;
            case "tripoliNumber":
                this.tripoliNumber = (String)val;
                break;
            case "userId":
                this.userId = (Integer)val;
                break;
            case "username":
                this.username = (String)val;
                break;
            case "workPhone":
                this.workPhone = (String)val;
                break;
            case "flights":
                this.flights = val;
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
        out.writeObject(this.address1);
        out.writeObject(this.address2);
        out.writeObject(this.addressCity);
        out.writeObject(this.addressState);
        out.writeObject(this.addressZip);
        out.writeObject(this.affiliation);
        out.writeObject(this.age);
        out.writeObject(this.certLevel);
        out.writeObject(this.contactId);
        out.writeObject(this.createdDate);
        out.writeObject(this.duesPaid);
        out.writeObject(this.duesPaidDate);
        out.writeObject(this.emailAddress);
        out.writeObject(this.externalUserId);
        out.writeObject(this.firstName);
        out.writeObject(this.homePhone);
        out.writeObject(this.id);
        out.writeObject(this.lastName);
        out.writeObject(this.lcoInitials);
        out.writeObject(this.memberType);
        out.writeObject(this.middleInitial);
        out.writeObject(this.migrationId);
        out.writeObject(this.mobilePhone);
        out.writeObject(this.narNumber);
        out.writeObject(this.notes);
        out.writeObject(this.paidThru);
        out.writeObject(this.paidThruDate);
        out.writeObject(this.postEmail);
        out.writeObject(this.tripoliNumber);
        out.writeObject(this.userId);
        out.writeObject(this.username);
        out.writeObject(this.workPhone);
        out.writeObject(this.flights);
    }

    @Override
    protected void readState(ObjectInputStream in) throws IOException, ClassNotFoundException {
        super.readState(in);
        this.address1 = (String)in.readObject();
        this.address2 = (String)in.readObject();
        this.addressCity = (String)in.readObject();
        this.addressState = (String)in.readObject();
        this.addressZip = (String)in.readObject();
        this.affiliation = (String)in.readObject();
        this.age = (Short)in.readObject();
        this.certLevel = (Short)in.readObject();
        this.contactId = (Integer)in.readObject();
        this.createdDate = (Date)in.readObject();
        this.duesPaid = (String)in.readObject();
        this.duesPaidDate = (Date)in.readObject();
        this.emailAddress = (String)in.readObject();
        this.externalUserId = (Integer)in.readObject();
        this.firstName = (String)in.readObject();
        this.homePhone = (String)in.readObject();
        this.id = (Long)in.readObject();
        this.lastName = (String)in.readObject();
        this.lcoInitials = (String)in.readObject();
        this.memberType = (Short)in.readObject();
        this.middleInitial = (String)in.readObject();
        this.migrationId = (Integer)in.readObject();
        this.mobilePhone = (String)in.readObject();
        this.narNumber = (String)in.readObject();
        this.notes = (String)in.readObject();
        this.paidThru = (String)in.readObject();
        this.paidThruDate = (Date)in.readObject();
        this.postEmail = (String)in.readObject();
        this.tripoliNumber = (String)in.readObject();
        this.userId = (Integer)in.readObject();
        this.username = (String)in.readObject();
        this.workPhone = (String)in.readObject();
        this.flights = in.readObject();
    }

}
