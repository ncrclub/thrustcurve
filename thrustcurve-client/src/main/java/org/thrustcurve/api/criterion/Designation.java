package org.thrustcurve.api.criterion;

import org.thrustcurve.api.search.SearchCriteria;

public class Designation extends Primitive {

	public Designation(String value) {
		super(SearchCriteria.CRITERIA_DESIGNATION, value);
	}

}
