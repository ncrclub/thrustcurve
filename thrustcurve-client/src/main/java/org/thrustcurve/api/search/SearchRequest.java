package org.thrustcurve.api.search;


import org.thrustcurve.api.criterion.Primitive;
import org.thrustcurve.api.xml.XmlTag;

import java.util.Collection;

public class SearchRequest {
	
	private SearchCriteria criteria;

	public SearchRequest(SearchCriteria criteria) {
		this.criteria= criteria;
	}
	
	public XmlTag getRequestXml() {
		
        if (criteria == null || criteria.size() < 1) {
            throw new IllegalStateException("no criteria to search for");
        }

        // note that the schema information allows you to validate your document:
        // http://www.w3.org/2001/03/webdata/xsv
        XmlTag request= new XmlTag("search-request");
        
        request.setAttribute("xmlns", "http://www.thrustcurve.org/2008/SearchRequest");
        request.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        request.setAttribute("xsi", "http://www.thrustcurve.org/2008/SearchRequest http://www.thrustcurve.org/2008/search-request.xsd");
        request.setAttribute("xsi:schemaLocation", "http://www.thrustcurve.org/2008/SearchRequest http://www.thrustcurve.org/2008/search-request.xsd");

        for (Primitive criterion : getCriteria())
        {
        	String value = criterion.getValue();
			if (value != null && !"".equals(value)) {
        		XmlTag tag = new XmlTag(criterion.getName());
        		tag.setValue(value);
        		request.addChild(tag);
			}
			
        }
        
		request.addChild(new XmlTag("data-fields", "*"));
        
        return request;
	}
	
	public Collection<Primitive> getCriteria() {
		return criteria.getCriteria();
	}
	
	@Override
	public String toString() {
		return getRequestXml().toString();
	}
	
}
