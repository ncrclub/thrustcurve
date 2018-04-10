package org.thrustcurve.api;

import util.json.JsonObject;
import util.xml.XmlTag;


public class TCMotorData {
	

	private XmlTag record;
	
	public TCMotorData(XmlTag record) {
		this.record= record;
	}
	
	
	public String getMotorId() { return getValue("motor-id"); }

	public String getFileId() { return getValue("simfile-id"); }
	public String getFormat() { return getValue("format"); }
	public String getSource() { return getValue("source"); }
	public String getLicense() { return getValue("license"); }
	public String getData() { return getValue("data"); }
	public String getInfoUrl() { return getValue("info-url"); }
	public String getDataUrl() { return getValue("info-url"); }
	
	private String getValue(String key) {
		XmlTag tag = record.getChildren().getTag(key);
		if (tag == null) { return ""; }
		return tag.getValue();
	}

	public JsonObject toJson() {
		JsonObject json= new JsonObject();
		
		json.set("motor-id", getMotorId());
		json.set("simfile-id", getFileId());
		json.set("format", getFormat());
		json.set("source", getSource());
		json.set("license", getLicense());
		// json.set("data", getData());
		json.set("info-url", getInfoUrl());
		json.set("data-url", getDataUrl());

		return json;
		
	}

	public XmlTag toXml() {
		return record;
	}



}
