package club.ncr.cayenne.auto;

import club.ncr.cayenne.MotorData;
import club.ncr.cayenne.MotorImpulse;
import club.ncr.cayenne.MotorCertOrg;
import club.ncr.cayenne.MotorDiameter;
import club.ncr.cayenne.MotorCase;
import club.ncr.cayenne.MotorMfg;
import club.ncr.cayenne.MotorName;
import club.ncr.cayenne.MotorPropellant;
import club.ncr.cayenne.MotorType;
import org.apache.cayenne.CayenneDataObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

/**
 * Class _Motor was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Motor extends CayenneDataObject {

    public static final String BRAND_NAME_PROPERTY = "brandName";
    public static final String BURN_TIME_PROPERTY = "burnTime";
    public static final String DESIGNATION_PROPERTY = "designation";
    public static final String EXTERNAL_ID_PROPERTY = "externalID";
    public static final String GROSS_WEIGHT_PROPERTY = "grossWeight";
    public static final String ID_PROPERTY = "id";
    public static final String LAST_UPDATED_PROPERTY = "lastUpdated";
    public static final String LENGTH_PROPERTY = "length";
    public static final String THRUST_AVG_PROPERTY = "thrustAvg";
    public static final String THRUST_MAX_PROPERTY = "thrustMax";
    public static final String TOTAL_IMPULSE_NS_PROPERTY = "totalImpulseNs";
    public static final String TYPE_ID_PROPERTY = "typeID";
    public static final String WEIGHT_PROPERTY = "weight";
    public static final String CASE_PROPERTY = "case";
    public static final String CERTIFICATION_ORGANIZATION_PROPERTY = "certificationOrganization";
    public static final String COMMON_NAME_PROPERTY = "commonName";
    public static final String DATA_PROPERTY = "data";
    public static final String DIAMETER_PROPERTY = "diameter";
    public static final String IMPULSE_PROPERTY = "impulse";
    public static final String MANUFACTURER_PROPERTY = "manufacturer";
    public static final String PROPELLANT_PROPERTY = "propellant";
    public static final String TYPE_PROPERTY = "type";

    public static final String ID_PK_COLUMN = "ID";

    public void setBrandName(String brandName) {
        writeProperty(BRAND_NAME_PROPERTY, brandName);
    }
    public String getBrandName() {
        return (String)readProperty(BRAND_NAME_PROPERTY);
    }

    public void setBurnTime(Double burnTime) {
        writeProperty(BURN_TIME_PROPERTY, burnTime);
    }
    public Double getBurnTime() {
        return new BigDecimal((Double)readProperty(BURN_TIME_PROPERTY)).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

    public void setDesignation(String designation) {
        writeProperty(DESIGNATION_PROPERTY, designation);
    }
    public String getDesignation() {
        return (String)readProperty(DESIGNATION_PROPERTY);
    }

    public void setExternalID(String externalID) {
        writeProperty(EXTERNAL_ID_PROPERTY, externalID);
    }
    public String getExternalID() {
        return (String)readProperty(EXTERNAL_ID_PROPERTY);
    }

    public void setGrossWeight(Double grossWeight) {
        writeProperty(GROSS_WEIGHT_PROPERTY, grossWeight);
    }
    public Double getGrossWeight() {
        return (Double)readProperty(GROSS_WEIGHT_PROPERTY);
    }

    public void setId(Integer id) {
        writeProperty(ID_PROPERTY, id);
    }
    public Integer getId() {
        return (Integer)readProperty(ID_PROPERTY);
    }

    public void setLastUpdated(Date lastUpdated) {
        writeProperty(LAST_UPDATED_PROPERTY, lastUpdated);
    }
    public Date getLastUpdated() {
        return (Date)readProperty(LAST_UPDATED_PROPERTY);
    }

    public void setLength(Double length) {
        writeProperty(LENGTH_PROPERTY, length);
    }
    public Double getLength() {
        return (Double)readProperty(LENGTH_PROPERTY);
    }

    public void setThrustAvg(Double thrustAvg) {
        writeProperty(THRUST_AVG_PROPERTY, thrustAvg);
    }
    public Double getThrustAvg() {
        return (Double)readProperty(THRUST_AVG_PROPERTY);
    }

    public void setThrustMax(Double thrustMax) {
        writeProperty(THRUST_MAX_PROPERTY, thrustMax);
    }
    public Double getThrustMax() {
        return (Double)readProperty(THRUST_MAX_PROPERTY);
    }

    public void setTotalImpulseNs(Double totalImpulseNs) {
        writeProperty(TOTAL_IMPULSE_NS_PROPERTY, totalImpulseNs);
    }
    public Double getTotalImpulseNs() {
        return (Double)readProperty(TOTAL_IMPULSE_NS_PROPERTY);
    }

    public void setTypeID(Integer typeID) {
        writeProperty(TYPE_ID_PROPERTY, typeID);
    }
    public Integer getTypeID() {
        return (Integer)readProperty(TYPE_ID_PROPERTY);
    }

    public void setWeight(Double weight) {
        writeProperty(WEIGHT_PROPERTY, weight);
    }
    public Double getWeight() {
        return (Double)readProperty(WEIGHT_PROPERTY);
    }

    public void setCase(MotorCase _case) {
        setToOneTarget(CASE_PROPERTY, _case, true);
    }

    public MotorCase getCase() {
        return (MotorCase)readProperty(CASE_PROPERTY);
    }


    public void setCertificationOrganization(MotorCertOrg certificationOrganization) {
        setToOneTarget(CERTIFICATION_ORGANIZATION_PROPERTY, certificationOrganization, true);
    }

    public MotorCertOrg getCertificationOrganization() {
        return (MotorCertOrg)readProperty(CERTIFICATION_ORGANIZATION_PROPERTY);
    }


    public void setCommonName(MotorName commonName) {
        setToOneTarget(COMMON_NAME_PROPERTY, commonName, true);
    }

    public MotorName getCommonName() {
        return (MotorName)readProperty(COMMON_NAME_PROPERTY);
    }


    public void addToData(MotorData obj) {
        addToManyTarget(DATA_PROPERTY, obj, true);
    }
    public void removeFromData(MotorData obj) {
        removeToManyTarget(DATA_PROPERTY, obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<MotorData> getData() {
        return (List<MotorData>)readProperty(DATA_PROPERTY);
    }


    public void setDiameter(MotorDiameter diameter) {
        setToOneTarget(DIAMETER_PROPERTY, diameter, true);
    }

    public MotorDiameter getDiameter() {
        return (MotorDiameter)readProperty(DIAMETER_PROPERTY);
    }


    public void setImpulse(MotorImpulse impulse) {
        setToOneTarget(IMPULSE_PROPERTY, impulse, true);
    }

    public MotorImpulse getImpulse() {
        return (MotorImpulse)readProperty(IMPULSE_PROPERTY);
    }


    public void setManufacturer(MotorMfg manufacturer) {
        setToOneTarget(MANUFACTURER_PROPERTY, manufacturer, true);
    }

    public MotorMfg getManufacturer() {
        return (MotorMfg)readProperty(MANUFACTURER_PROPERTY);
    }


    public void setPropellant(MotorPropellant propellant) {
        setToOneTarget(PROPELLANT_PROPERTY, propellant, true);
    }

    public MotorPropellant getPropellant() {
        return (MotorPropellant)readProperty(PROPELLANT_PROPERTY);
    }


    public void setType(MotorType type) {
        setToOneTarget(TYPE_PROPERTY, type, true);
    }

    public MotorType getType() {
        return (MotorType)readProperty(TYPE_PROPERTY);
    }


}
