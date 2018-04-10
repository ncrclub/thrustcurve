package org.thrustcurve.api.data;

import org.thrustcurve.api.json.JsonArray;
import org.thrustcurve.api.json.JsonObject;
import org.thrustcurve.api.xml.XmlTag;

import java.util.ArrayList;
import java.util.List;

public class TCMotorRecord {

	private XmlTag record;
	
	private List<TCMotorData> data;
	
	public TCMotorRecord(XmlTag record) {
		this.record= record;
		this.data= new ArrayList<TCMotorData>();
	}
	
	public String getMotorId() { return getValue("motor-id"); }

	public String getManufacturer() { return getValue("manufacturer"); }
	public String getDesignation() { return getValue("designation"); }
	public String getBrandName() { return getValue("brand-name"); }
	public String getCommonName() { return getValue("common-name"); }
	public String getImpulseClass() { return getValue("impulse-class"); }
	public String getDiameter() { return getValue("diameter"); }
	public String getType() { return getValue("type"); }
	public String getCertificationOrganization() { return getValue("cert-org"); }
	public String getDataFiles() { return getValue("data-files"); }
	public String getInfoUrl() { return getValue("info-url"); }
	public String getPropellant() { return getValue("prop-info"); }
	public String getManufacturerAbbv() { return getValue("manufacturer-abbrev"); }
	public String getUpdated() { return getValue("updated-on"); }
	
	public String getMotorCase() { return getValue("case-info"); }
	
	public double getAverageThrust() { 
		String key= "avg-thrust-n";
		return get(key);
	}

	public double getMaxThrust() {
		String key= "max-thrust-n";
		return get(key);
	}
	
	public double getTotalImpulse() {
		String key= "tot-impulse-ns";
		return get(key);
	}
	
	public Double getBurnTime() {
		String key= "burn-time-s";
		return get(key);
	}
	
	public double getWeight() {
		String key= "prop-weight-g";
		return get(key);
	}
	
	public double getGrossWeight() {
		String key= "total-weight-g";
		return get(key);
	}
	
	public double getLength() {
		String key= "length";
		return get(key);
	}
	
	private double get(String key) {
		try {
			return Double.parseDouble(getValue(key));
		} catch (NullPointerException npx) {
			return 0;
		} catch (NumberFormatException nfx) {
			return -1;
		}
	}
	
	private String getValue(String key) {
		XmlTag tag = record.getChildren().getTag(key);
		if (tag == null) { return ""; }
		return tag.getValue();
	}

	public JsonObject toJson() {
		JsonObject json= new JsonObject();
		
		json.set("motor-id", getMotorId());
		json.set("manufacturer", getManufacturer());
		json.set("designation", getDesignation());
		json.set("common-name", getCommonName());
		json.set("impulse-class", getImpulseClass());
		json.set("avg-thrust-n", getAverageThrust());
		json.set("max-thrust-n", getMaxThrust());
		json.set("tot-impulse-ns", getTotalImpulse());
		json.set("burn-time-s", getBurnTime());
		json.set("data-files", getDataFiles());
		json.set("data", dataToJson());
		json.set("info-url", getInfoUrl());
		json.set("prop-weight-g", getWeight());
		json.set("prop-info", getPropellant());
		json.set("case-info", getMotorCase());
		json.set("manufacturer-abbrev", getManufacturerAbbv());
		json.set("length", getLength());
		json.set("updated-on", getUpdated());

		return json;
		
	}
	
	private JsonArray dataToJson() {
		JsonArray array= new JsonArray();
		
		for (TCMotorData md : this.data) {
			array.add(md.toJson());
		}
		
		return array;
	}

	public XmlTag toXml() {
		return record;
	}
	
	@Override
	public String toString() {
		return getCommonName() +" ("+ getManufacturer() +")";
	}

	/** 
	 * 
	 * <pre>
	 * 		&lt;result&gt;
			&lt;motor-id&gt;979&lt;/motor-id&gt;
			&lt;simfile-id&gt;2001&lt;/simfile-id&gt;
			&lt;format&gt;RASP&lt;/format&gt;
			&lt;source&gt;cert&lt;/source&gt;
			&lt;license&gt;PD&lt;/license&gt;
			&lt;data&gt;
			OyBXaGl0ZSAzOG1tIDZHWEwNCjsgMTAxMy1KNDUzLVdILTE2QQ0KMTAxMy1KNDUzLVdILTE2QSAz
			OCA1MDAgMTYtMTMtMTEtOS03IDAuNjEzMiAwLjk2NDMgQ1RJDQogICAwLjAxOCA2NjMuNzg5DQog
			ICAwLjA0IDcyNS4xOA0KICAgMC4xMDUgNjMwLjIxNg0KICAgMC4yMyA1NzguNDE3DQogICAwLjQ1
			MSA1NDMuODg1DQogICAxLjQ1NCA1MzUuMjUyDQogICAxLjc5NyAyOTEuNjA3DQogICAxLjkxIDE4
			OC45NjkNCiAgIDIuMDg4IDEyOC41MzcNCiAgIDIuMjc2IDE5LjE4NQ0KICAgMi4zNjQgMC4wDQo=
			&lt;/data&gt;
			&lt;info-url&gt;http://www.thrustcurve.org/simfilesearch.jsp?id=2001&lt;/info-url&gt;
			&lt;data-url&gt;http://www.thrustcurve.org/download.jsp?id=2001&lt;/data-url&gt;
		&lt;/result&gt;
	 * </pre>
	 * @param result
	 */
	public void addData(XmlTag record) {
		TCMotorData data= new TCMotorData(record);
		this.data.add(data);
	}

	public List<TCMotorData> getData() {
		return data;
	}

}
