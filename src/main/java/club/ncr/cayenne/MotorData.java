package club.ncr.cayenne;

import club.ncr.cayenne.auto._MotorData;
import org.thrustcurve.api.json.JsonObject;
import org.thrustcurve.api.json.JsonValue;

public class MotorData extends _MotorData {

	public JsonValue toJsonValue() {
		
		JsonObject json= new JsonObject();
		
		json.set("tc-info-url", getInfoUrl());
		json.set("tc-data-url", getDataUrl());
		
		json.set("format", getFormat().getName());
		// json.set("file-extension", getFormat().getFileExtension());
		
		json.set("source", getSource());
		json.set("tc-id", getTcFileID());
		
		return json;
		
	}

}
