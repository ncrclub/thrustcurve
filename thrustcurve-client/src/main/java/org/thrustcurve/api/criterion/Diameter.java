package org.thrustcurve.api.criterion;

import org.thrustcurve.api.search.SearchCriteria;

public class Diameter extends Primitive {

	public Diameter(String value) {
		super(SearchCriteria.CRITERIA_DIAMETER, value);
	}

	public Diameter(int value) {
		super(SearchCriteria.CRITERIA_DIAMETER, ""+ value);
	}

}
