package org.thrustcurve.api.criterion;

import org.thrustcurve.api.search.SearchCriteria;

public class ManufacturerAbbreviation extends Primitive {

	public ManufacturerAbbreviation(String value) {
		super(SearchCriteria.CRITERIA_MANUFACTURER_ABBREV, value);
	}

}
