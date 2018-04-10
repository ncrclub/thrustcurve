package org.thrustcurve.api;

import org.apache.logging.log4j.Logger;
import util.kits.Log4JKit;
import util.xml.FullXmlParser;
import util.xml.XmlTag;
import util.xml.XmlTagList;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class ThrustCurveApi {
	
    private URL serviceUrl;
    private URL searchEndpoint;
    private URL downloadEndpoint;
    
    public ThrustCurveApi() throws MalformedURLException {
    	this("http://www.thrustcurve.org/servlets", "search", "download");
    }

	/**
	 * @param serviceUrl
	 * @param search
	 * @param download
	 * @throws MalformedURLException
	 */
	public ThrustCurveApi(String serviceUrl, String search, String download) throws MalformedURLException {
    	this.serviceUrl= new URL(serviceUrl);
    	this.searchEndpoint= new URL(serviceUrl +"/"+ search);
    	this.downloadEndpoint= new URL(serviceUrl +"/"+ download);
    }

    public SearchResults search(SearchCriteria criteria, boolean downloadData) throws IOException {
		SearchRequest request = new SearchRequest(criteria);
        return search(request, downloadData);
    	
    }

	public SearchResults search(SearchRequest request, boolean downloadData) throws IOException {
		// send the search API request document
		XmlTag xml= request.getRequestXml();
		
		Logger log= Log4JKit.getLog(this);
		// log.info("Search Request XML:\n"+ xml +"\n");
    	
		try {
		SearchResults results= new SearchResults(search(xml));
	
		if (downloadData && results.size() > 0) {
			
			try {
				DownloadRequest downloadRequest= new DownloadRequest(results.getRecords());
				XmlTagList data= download(downloadRequest);
			
				for (XmlTag motorId : data.seek("download-response.results.result.motor-id")) {
					String id= motorId.getValue();
					XmlTag result= motorId.getParent();
					
					TCMotorRecord motor= results.getMotorById(id);
					motor.addData(result);
				}
			} catch (IOException iox) {
				
				for (TCMotorRecord motor : results.getRecords()) {
					log.info("Failed download ["+ iox.getMessage() +"] "+ motor);
					
				}
			}
		}
		
		return results;
		
		} catch (IOException iox) {
			
			log.info("Failed search ["+ iox.getMessage() +"] "+ xml);
		}
		
		return null;
	}

	private XmlTagList search(XmlTag request) throws IOException {
		URLConnection conn = searchEndpoint.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
	
		OutputStreamWriter out= new OutputStreamWriter(conn.getOutputStream());
		out.write("<?xml version=\"1.0\" encoding=\"ascii\"?>");
		out.write(request.toString());
		out.flush();
		out.close();
		
        InputStream in= conn.getInputStream();
        
        FullXmlParser parser= new FullXmlParser();
        parser.setInput(in);
        
        if (!parser.parse()) {
        	// error
        }
        
        return parser.xmlData;
	}
	
	
	public XmlTagList download(DownloadRequest request) throws IOException {
		// send the download request
		XmlTag requestXml = request.getRequestXml();
		
		Logger log= Log4JKit.getLog(this);

    	XmlTagList xml= download(requestXml);
   	 
   	 	while (request.hasMoreResults()) {
   	 		
   	 		log.info("Fetching more results from search... ");
   	 		
   	 		XmlTagList chunk= download(request.getRequestXml());
   	 		for (XmlTag result : chunk.seek("download-response.results.result")) {
   	 			xml.getTag("download-response.results").addChild(result);
   	 		}
   	 	}
        
        return xml;
	}

	private XmlTagList download(XmlTag requestXml) throws IOException {
		URLConnection conn = downloadEndpoint.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
	
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
		out.write("<?xml version=\"1.0\" encoding=\"ascii\"?>");
		out.write(requestXml.toString());
		out.flush();
		out.close();
		
        InputStream in= conn.getInputStream();
        FullXmlParser parser= new FullXmlParser();
        parser.setInput(in);
        
        parser.parse();
        
        XmlTagList xml= parser.xmlData;
		return xml;
	}
	
    
    public static void main(String[] args) throws Exception { 
    	
    	ThrustCurveApi api= new ThrustCurveApi();
    	
    	SearchCriteria criteria= new SearchCriteria();
    	
    	criteria.manufacturer("cti");
    	criteria.diameter(38);
    	criteria.addCriteria(new Criterion("has-data-files", "true"));
    	criteria.maxResults(100);
    	
    	SearchRequest request= new SearchRequest(criteria);
    		
    	// System.out.println(request.getRequestXml());
    	
    	SearchResults results= api.search(criteria, true);
    	
    	for (TCMotorRecord motor : results) {
    		System.out.println(motor.getMotorId());
    		for (TCMotorData data : motor.getData()) {
    			System.out.println(data.toJson());
    		}
    	}
    	// DownloadRequest download= new DownloadRequest(results.getRecords());
    	// System.out.println(api.download(download));
    	
    }
    
    public URL getServiceUrl() {
		return serviceUrl;
	}
    
}
