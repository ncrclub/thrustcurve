package org.thrustcurve.api.search;

import org.thrustcurve.api.data.TCMotorRecord;
import org.thrustcurve.api.json.JsonArray;
import org.thrustcurve.api.json.JsonObject;
import org.thrustcurve.api.xml.XmlTag;
import org.thrustcurve.api.xml.XmlTagList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SearchResults implements Iterable<TCMotorRecord>{
	
	private List<TCMotorRecord> records;
	
	public SearchResults(XmlTagList results) {
		
		records= new ArrayList<TCMotorRecord>();
		
		for (XmlTag tag : results.seek("search-response.results.result")) {
			records.add(new TCMotorRecord(tag));
		}
		
		
	}

	public Iterator<TCMotorRecord> iterator() {
		return records.iterator();
	}
	
	public List<TCMotorRecord> getRecords() {
		return records;
	}
	
	public int size() {
		return records.size();
	}
	
	public JsonObject toJson() {
		JsonObject json= new JsonObject();
		JsonArray results= new JsonArray();
		
		for (TCMotorRecord record : this) {
			
			JsonObject r= record.toJson();
			results.add(r);
			
		}
		
		json.set("results", results);
		
		return json;
	}
	
	public XmlTag toXml() {
		XmlTag xml= new XmlTag("results");
		
		
		for (TCMotorRecord record : this) {
			XmlTag r= record.toXml();
			xml.addChild(r);
		}
		
		return xml;
	}


	public TCMotorRecord getMotorById(String id) {
		
		TCMotorRecord motor= null;
		
		for (TCMotorRecord m : records) {
			if (m.getMotorId().equals(id)) {
				return m;
			}
		}
		
		return motor;
		
	}
}
