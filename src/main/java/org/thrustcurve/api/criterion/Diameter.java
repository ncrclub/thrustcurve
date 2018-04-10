package org.thrustcurve.api.criterion;

import org.thrustcurve.api.Criterion;

public class Diameter extends Criterion {

	public Diameter() {
		this(null);
	}

	public Diameter(String value) {
		super("diameter", value);
	}

}
