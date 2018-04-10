package org.thrustcurve.api.criterion;

import org.thrustcurve.api.search.SearchCriteria;

public class CommonName extends Primitive {

	public CommonName(String value) {
		super(SearchCriteria.CRITERIA_COMMON_NAME, value);
	}

}
