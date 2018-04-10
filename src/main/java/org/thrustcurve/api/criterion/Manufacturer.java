package org.thrustcurve.api.criterion;

import org.thrustcurve.api.Criterion;

public class Manufacturer extends Criterion {

	public Manufacturer() {
		this(null);
	}

	public Manufacturer(String value) {
		super("manufacturer", value);
	}

}
