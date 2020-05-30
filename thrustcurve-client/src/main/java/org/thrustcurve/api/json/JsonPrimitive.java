package org.thrustcurve.api.json;

public class JsonPrimitive extends JsonValue {

	public JsonPrimitive() {
		super();
	}

	// public JsonPrimitive(String value) { super("null".equals(value) ? null : value); }
	public JsonPrimitive(String value) { super(value); }
	public JsonPrimitive(Integer value) { super(value); }
	public JsonPrimitive(Long value) { super(value); }
	public JsonPrimitive(Double value) { super(value); }
	public JsonPrimitive(Boolean value) { super(value); }
	public JsonPrimitive(Float value) { super(value); }

}
