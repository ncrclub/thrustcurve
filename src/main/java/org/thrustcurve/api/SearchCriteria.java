package org.thrustcurve.api;

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
	
	private HashMap<String, Criterion> criteria= new HashMap<String, Criterion>();
	
	public SearchCriteria() {
		this(null);
	}
	
	public SearchCriteria(ArrayList<Criterion> criteria) {
		if (criteria == null) { return; }
		for (Criterion c : criteria) {
			addCriteria(c);
		}
	}
	
	public void addCriteria(Criterion c) {
		if (c != null) {
			this.criteria.put(c.getName(), c);
		}
	}
	
	public Collection<Criterion> getCriteria() {
		return criteria.values();
	}

	public int size() {
		return criteria.size();
	}
	
	public void manufacturerAbbreviation(String value) {
		if (value != null) {
			addCriteria(new Criterion(CRITERIA_MANUFACTURER_ABBREV, value));
		} else {
			remove(CRITERIA_MANUFACTURER_ABBREV);
		}
	}
	
	public void remove(String criterion) {
		criteria.remove(criterion);
	}
	
	public void manufacturer(String value) {
		if (value != null) {
			addCriteria(new Criterion(CRITERIA_MANUFACTURER, value));
		} else {
			remove(CRITERIA_MANUFACTURER);
		}
	}
	
	public void designation(String value) {
		if (value != null) {
			addCriteria(new Criterion(CRITERIA_DESIGNATION, value));
		} else {
			remove(CRITERIA_DESIGNATION);
		}
	}
	
	public void brandName(String value) {
		if (value != null) {
			addCriteria(new Criterion(CRITERIA_BRAND_NAME, value));
		} else {
			remove(CRITERIA_BRAND_NAME);
		}
	}
	
	public void commonName(String value) {
		if (value != null) {
			addCriteria(new Criterion(CRITERIA_COMMON_NAME, value));
		} else {
			remove(CRITERIA_COMMON_NAME);
		}
	}
	
	public void impulseClass(String value) {
		if (value != null) {
			addCriteria(new Criterion(CRITERIA_IMPULSE_CLASS, value));
		} else {
			remove(CRITERIA_IMPULSE_CLASS);
		}
	}
	
	public void diameter(Integer value) {
		if (value != null) {
			addCriteria(new Criterion(CRITERIA_DIAMETER, ""+ value));
		} else {
			remove(CRITERIA_DIAMETER);
		}
	}
	
	public void type(String value) {
		if (value != null) {
			addCriteria(new Criterion(CRITERIA_TYPE, value));
		} else {
			remove(CRITERIA_TYPE);
		}
	}
	
	public void dataUpdatedSince(Date date) {
		if (date != null) {
			addCriteria(new Criterion(CRITERIA_DATA_UPDATED_SINCE, DateKit.format(date, DateKit.STD_DATE)));
		} else {
			remove(CRITERIA_DATA_UPDATED_SINCE);
		}
	}
	
	public void infoUpdatedSince(Date date) {
		if (date != null) {
			addCriteria(new Criterion(CRITERIA_INFO_UPDATED_SINCE, DateKit.format(date, DateKit.STD_DATE)));
		} else {
			remove(CRITERIA_INFO_UPDATED_SINCE);
		}
		
	}
	
	public void maxResults(int max) {
		if (max > 0) {
			if (max > 100) { max= 100; }
			addCriteria(new Criterion(CRITERIA_MAX_RESULTS, ""+ max));
		} else {
			remove(CRITERIA_MAX_RESULTS);
		}
	}
	
}
