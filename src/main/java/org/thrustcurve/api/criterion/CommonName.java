package org.thrustcurve.api.criterion;

import org.thrustcurve.api.SearchCriteria;

public class CommonName extends Primitive {

	public CommonName(String value) {
		super(SearchCriteria.CRITERIA_DESIGNATION, value);
	}

}
