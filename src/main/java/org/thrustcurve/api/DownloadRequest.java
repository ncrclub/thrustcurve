package org.thrustcurve.api;

import org.thrustcurve.api.data.TCMotorRecord;
import util.xml.XmlTag;

import java.util.List;

public class DownloadRequest {
	
	private List<TCMotorRecord> motors;

	private int i= 0;

	public DownloadRequest(List<TCMotorRecord> motors) {
		this.motors= motors;
	}
	
	public XmlTag getRequestXml() {
		
        if (motors == null || motors.size() < 1) {
            throw new IllegalStateException("no motors provided");
        }

        // note that the schema information allows you to validate your document:
        // http://www.w3.org/2001/03/webdata/xsv
        XmlTag request= new XmlTag("download-request");
        
        request.setAttribute("xmlns", "http://www.thrustcurve.org/2008/DownloadRequest");
        request.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        // request.setAttribute("xsi", "http://www.thrustcurve.org/2008/DownloadRequest http://www.thrustcurve.org/2008/download-request.xsd");
        request.setAttribute("xsi:schemaLocation", "http://www.thrustcurve.org/2008/DownloadRequest http://www.thrustcurve.org/2008/download-request.xsd");
        
        XmlTag motorIds= new XmlTag("motor-ids");
        request.addChild(motorIds);

        for (int j= 0; j < 3 && i < motors.size(); j++, i++) {
        	TCMotorRecord motor= motors.get(i);
        	
        	String value = motor.getMotorId();
			if (value != null && !"".equals(value)) {
        		XmlTag tag = new XmlTag("id", value);
        		motorIds.addChild(tag);
			}
			
        }
        
        return request;
	}
	
	public boolean hasMoreResults() {
		return i < motors.size();
	}
	
	@Override
	public String toString() {
		return getRequestXml().toString();
	}
	
}
