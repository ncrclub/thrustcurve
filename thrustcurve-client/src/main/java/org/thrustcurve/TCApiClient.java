package org.thrustcurve;

import org.thrustcurve.api.search.DownloadRequest;
import org.thrustcurve.api.search.SearchCriteria;
import org.thrustcurve.api.data.TCMotorRecord;
import org.thrustcurve.api.search.SearchRequest;
import org.thrustcurve.api.search.SearchResults;
import org.thrustcurve.api.XmlParser;
import org.thrustcurve.api.xml.XmlTag;
import org.thrustcurve.api.xml.XmlTagList;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class TCApiClient {
	
    private URL serviceUrl;
    private URL searchEndpoint;
    private URL downloadEndpoint;
    
    public TCApiClient() {
    	try {
			init("http://www.thrustcurve.org/servlets", "search", "download");
		} catch (MalformedURLException mux) {
    		throw new RuntimeException(mux.getMessage(), mux);
		}
    }

	/**
	 * @param serviceUrl
	 * @param searchPath
	 * @param downloadPath
	 * @throws MalformedURLException
	 */
	public TCApiClient(String serviceUrl, String searchPath, String downloadPath) throws MalformedURLException {
		init(serviceUrl, searchPath, downloadPath);
	}

	private void init(String serviceUrl, String searchPath, String downloadPath) throws MalformedURLException {
    	this.serviceUrl= new URL(serviceUrl);
    	this.searchEndpoint= new URL(serviceUrl +"/"+ searchPath);
    	this.downloadEndpoint= new URL(serviceUrl +"/"+ downloadPath);
    }

    public SearchResults search(SearchCriteria criteria, boolean downloadData) throws IOException {
        return search(new SearchRequest(criteria), downloadData);
    	
    }

	public SearchResults search(SearchRequest request, boolean downloadData) throws IOException {
		// send the search API request document
		XmlTag xml= request.getRequestXml();
		
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
					//log.info("Failed download ["+ iox.getMessage() +"] "+ motor);
					System.err.println("Failed download ["+ iox.getMessage() +"] "+ motor);

				}
			}
		}
		
		return results;
		
		} catch (IOException iox) {
			
			//log.info("Failed search ["+ iox.getMessage() +"] "+ xml);
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
        
        XmlParser parser= new XmlParser();
        parser.setInput(in);
        
        if (!parser.parse()) {
        	// error
        }
        
        return parser.xmlData;
	}
	
	
	public XmlTagList download(DownloadRequest request) throws IOException {
		// send the download request
		XmlTag requestXml = request.getRequestXml();

    	XmlTagList xml= download(requestXml);
   	 
   	 	while (request.hasMoreResults()) {
   	 		
   	 		//log.info("Fetching more results from search... ");
   	 		
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
        XmlParser parser= new XmlParser();
        parser.setInput(in);
        
        parser.parse();
        
        XmlTagList xml= parser.xmlData;
		return xml;
	}

    public URL getServiceUrl() {
		return serviceUrl;
	}
    
}
