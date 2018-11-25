package org.thrustcurve.api.json;

import org.thrustcurve.api.JsonParser;

public abstract class JsonValue {
	
	private Object value;
	
	protected JsonValue() { this.value= null; }
	protected JsonValue(Object value) { this.value= value; }
	
	//public JsonValue(String value) { this.value= value; }
	//public JsonValue(Integer value) { this.value= value; }
	//public JsonValue(Long value) { this.value= value; }
	//public JsonValue(Double value) { this.value= value; }
	//public JsonValue(Boolean value) { this.value= value; }
	
	public Object getValue() { return value; }
	
	protected void setValue(Object value) {
		this.value= value;
	}
	
	@Override
	public String toString() {
		
		Object value= getValue();
		
		if (value == null) { return "null"; }
		
		if (value instanceof String) { return "\""+ JsonParser.escape((String)(value.toString())) +"\""; }
		
		return value.toString();
	}
	


}
