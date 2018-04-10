package org.thrustcurve.api.criterion;

import org.thrustcurve.api.SearchCriteria;

public class Manufacturer extends Primitive {

	public Manufacturer() {
		this(null);
	}

	public Manufacturer(String value) {
		super(SearchCriteria.CRITERIA_MANUFACTURER, value);
	}

}
