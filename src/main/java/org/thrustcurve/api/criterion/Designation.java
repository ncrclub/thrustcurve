package org.thrustcurve.api.criterion;

import org.thrustcurve.api.Criterion;

public class Designation extends Criterion {

	public Designation() {
		this(null);
	}

	public Designation(String value) {
		super("designation", value);
	}

}
