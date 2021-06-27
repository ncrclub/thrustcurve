package club.ncr.cayenne.model;

import club.ncr.cayenne.model.auto._Motor;
import club.ncr.dto.MotorDTO;
import org.thrustcurve.api.json.JsonArray;
import org.thrustcurve.api.json.JsonObject;
import org.thrustcurve.api.json.JsonValue;

import java.util.Date;

public class Motor extends _Motor {

	@Deprecated
	public JsonValue toJsonValue() {
		JsonObject json= new JsonObject();

		json.set("external-id", getExternalId());
		json.set("common-name", getCommonName().getName());
		json.set("name", getCommonName().getName());
		json.set("designation", getDesignation());
		json.set("impulse", getImpulse().getImpulse());
		json.set("diameter", getDiameter().getDiameter());
		json.set("length", getLength());
		json.set("burn-time-s", getBurnTime());
		json.set("weight-g", getWeight());
		json.set("propellant", getPropellant().getName());
		json.set("manufacturer", getManufacturer().getName());
		json.set("manufacturer-abbv", getManufacturer().getAbbreviation());
		json.set("case-info", getCase().getName());

		JsonArray dataArray= new JsonArray();
		json.set("data", dataArray);
		for (MotorData data : getData()) {
			dataArray.add(data.toJsonValue());
		}


		return json;
	}

	public MotorDTO asDTO(boolean withData) {
		return new MotorDTO(this, withData);
	}

	public void update(
			Double burnTime,
			Double grossWeight,
			String brandName,
			Double weight,
			Double averageThrust,
			Double maxThrust,
			Double totalImpulse,
			String designation,
			Double length
	) {
		boolean updated = false;

		if (burnTime != null && getBurnTime() != burnTime) {
			setBurnTime(burnTime);
			updated = true;
		}

		if (grossWeight != null && getGrossWeight() != grossWeight) {
			setGrossWeight(grossWeight);
			updated = true;
		}

		if (brandName != null && !brandName.equals(getBrandName())) {
			setBrandName(brandName);
			updated = true;
		}

		if (weight != null && getWeight() != weight) {
			setWeight(weight);
			updated = true;
		}

		if (averageThrust != null && getThrustAvg() != averageThrust) {
			setThrustAvg(averageThrust);
			updated = true;
		}

		if (maxThrust != null && getThrustMax() != maxThrust) {
			setThrustMax(maxThrust);
			updated = true;
		}

		if (totalImpulse != null && getTotalImpulseNs() != totalImpulse) {
			setTotalImpulseNs(totalImpulse);
			updated = true;
		}

		if (designation != null && !designation.equals(getDesignation())) {
			setDesignation(designation);
			updated = true;
		}

		if (length != null && getLength() != length) {
			setLength(length);
			updated = true;
		}

		if (updated) {
			setLastUpdated(new Date());
		}
	}
}
