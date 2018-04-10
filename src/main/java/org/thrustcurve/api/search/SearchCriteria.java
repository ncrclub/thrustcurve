package org.thrustcurve.api.search;

import org.thrustcurve.api.criterion.Primitive;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

public class SearchCriteria {


	public static final String CRITERIA_INFO_UPDATED_SINCE = "info-updated-since";
	public static final String CRITERIA_MAX_RESULTS = "max-results";
	public static final String CRITERIA_DATA_UPDATED_SINCE = "data-updated-since";
	public static final String CRITERIA_TYPE = "type";
	public static final String CRITERIA_DIAMETER = "diameter";
	public static final String CRITERIA_IMPULSE_CLASS = "impulse-class";
	public static final String CRITERIA_COMMON_NAME = "common-name";
	public static final String CRITERIA_BRAND_NAME = "brand-name";
	public static final String CRITERIA_DESIGNATION = "designation";
	public static final String CRITERIA_MANUFACTURER = "manufacturer";
	public static final String CRITERIA_MANUFACTURER_ABBREV = "manufacturer-abbrev";

	public static final String STD_FMT="yyyy-MM-dd HH:mm:ss";
	private static final SimpleDateFormat formatter= new SimpleDateFormat(STD_FMT);

	private HashMap<String, Primitive> criteria= new HashMap<String, Primitive>();
	
	public SearchCriteria() {
		this(null);
	}
	
	public SearchCriteria(ArrayList<? extends Primitive> criteria) {
		if (criteria == null) { return; }
		for (Primitive c : criteria) {
			addCriteria(c);
		}
	}
	
	public SearchCriteria addCriteria(Primitive c) {
		if (c != null) {
			this.criteria.put(c.getName(), c);
		}
		return this;
	}
	
	public Collection<Primitive> getCriteria() {
		return criteria.values();
	}

	public int size() {
		return criteria.size();
	}
	
	public SearchCriteria manufacturerAbbreviation(String value) {
		if (value != null) {
			addCriteria(new Primitive(CRITERIA_MANUFACTURER_ABBREV, value));
		} else {
			remove(CRITERIA_MANUFACTURER_ABBREV);
		}
		return this;
	}
	
	public SearchCriteria remove(String criterion) {
		criteria.remove(criterion);
		return this;
	}
	
	public SearchCriteria manufacturer(String value) {
		if (value != null) {
			addCriteria(new Primitive(CRITERIA_MANUFACTURER, value));
		} else {
			remove(CRITERIA_MANUFACTURER);
		}
		return this;
	}
	
	public SearchCriteria designation(String value) {
		if (value != null) {
			addCriteria(new Primitive(CRITERIA_DESIGNATION, value));
		} else {
			remove(CRITERIA_DESIGNATION);
		}
		return this;
	}
	
	public SearchCriteria brandName(String value) {
		if (value != null) {
			addCriteria(new Primitive(CRITERIA_BRAND_NAME, value));
		} else {
			remove(CRITERIA_BRAND_NAME);
		}
		return this;
	}
	
	public SearchCriteria commonName(String value) {
		if (value != null) {
			addCriteria(new Primitive(CRITERIA_COMMON_NAME, value));
		} else {
			remove(CRITERIA_COMMON_NAME);
		}
		return this;
	}
	
	public SearchCriteria impulseClass(String value) {
		if (value != null) {
			addCriteria(new Primitive(CRITERIA_IMPULSE_CLASS, value));
		} else {
			remove(CRITERIA_IMPULSE_CLASS);
		}
		return this;
	}
	
	public SearchCriteria diameter(Integer value) {
		if (value != null) {
			addCriteria(new Primitive(CRITERIA_DIAMETER, ""+ value));
		} else {
			remove(CRITERIA_DIAMETER);
		}
		return this;
	}
	
	public SearchCriteria type(String value) {
		if (value != null) {
			addCriteria(new Primitive(CRITERIA_TYPE, value));
		} else {
			remove(CRITERIA_TYPE);
		}
		return this;
	}
	
	public SearchCriteria dataUpdatedSince(Date date) {
		if (date != null) {
			addCriteria(new Primitive(CRITERIA_DATA_UPDATED_SINCE, formatter.format(date)));
		} else {
			remove(CRITERIA_DATA_UPDATED_SINCE);
		}
		return this;
	}
	
	public SearchCriteria infoUpdatedSince(Date date) {
		if (date != null) {
			addCriteria(new Primitive(CRITERIA_INFO_UPDATED_SINCE, formatter.format(date)));
		} else {
			remove(CRITERIA_INFO_UPDATED_SINCE);
		}
		return this;
	}
	
	public SearchCriteria maxResults(int max) {
		if (max > 0) {
			if (max > 100) { max= 100; }
			addCriteria(new Primitive(CRITERIA_MAX_RESULTS, ""+ max));
		} else {
			remove(CRITERIA_MAX_RESULTS);
		}
		return this;
	}
	
}
