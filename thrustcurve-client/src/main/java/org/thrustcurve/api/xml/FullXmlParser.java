/**
 * 
 */
package org.thrustcurve.api.xml;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


/**
 * 
 * An XML parser
 * 
 * 
 * @author brad
 *
 */
public class FullXmlParser {

	private InputStream xmlStream;
	
	private Exception lastEx;
	public XmlTagList xmlData;
	
	public Document xmlDoc;
	
	
	public String errMsg= null;
	
	public FullXmlParser() {
		
	}
	
	public boolean setInput(String fn) {
		
		try {
			xmlStream= new FileInputStream(fn);
		} catch (FileNotFoundException fnfx) {
			// file not found
			lastEx= fnfx;
			errMsg= "File not found '"+ fn +"'";
			return false;
		}
		
		return true;
	}
	
	public boolean setInput(InputStream is) {
		xmlStream= is;
		return true;
	}
	
	// uses pre-set inputstream for xml input
	public boolean parse() {
		return parse(null);
	}
	
	public boolean parse(String xml) {
		// final int INPUT_STREAM= 0;
		// final int STRING= 1;
		
		// int inputType= INPUT_STREAM;
		
		if (xml == null) {
			if (xmlStream == null) {
				errMsg= "xml InputStream is null, use 'setInput()'";
				return false;
			}
		} else {
			// inputType= STRING;
			xmlStream= new ByteArrayInputStream(xml.getBytes());
		}
		
		DOMParser parser= new DOMParser();
		xmlDoc= null;
		
		InputSource src= new InputSource(xmlStream);
		
		try {
			parser.parse(src);
			
			xmlDoc= parser.getDocument();
		} catch (IOException iox) {
			lastEx= iox;
			errMsg= "I/O Exception while parsing xml InputStream ["+ iox.toString() +"]";
			return false;
			
		} catch (SAXException sx) {
			lastEx= sx;
			errMsg= "Malformed XML or XML Parsing Error ["+ sx.toString() +"]";
			return false;
		}	
		
		NodeList nl= xmlDoc.getChildNodes();
		
		if (nl == null) {
			errMsg= "XML document does not contain any data";
			return false;
		}
		
		xmlData= parse(nl, 0, null);		
			
		return true;
	}
	
	private XmlTagList parse(NodeList nl, int lvl, XmlTag parent) {
		if (nl == null) {
			return null;
		}
		
		XmlTagList list= new XmlTagList();
		
		for (int i= 0; i < nl.getLength(); i++) {
			Node n= nl.item(i);
			
			switch (n.getNodeType()) {
			case Node.ELEMENT_NODE:
				XmlTag t= new XmlTag(lvl, parent, n.getNodeName());
				t.setChildren(parse(n.getChildNodes(), lvl + 1, t));
				list.add(t);
				
				if (n.hasAttributes()) {
					NamedNodeMap attribs= n.getAttributes();
					for (int j= 0; j < attribs.getLength(); j++) {
						t.setAttribute(attribs.item(j).getNodeName(), attribs.item(j).getNodeValue());
					}
				}
				
				if (n.hasChildNodes()) {
					NodeList children= n.getChildNodes();
					for (int j= 0; j < children.getLength(); j++) {
						Node child= children.item(j);
						if (child.getNodeType() == Node.TEXT_NODE || child.getNodeType() == Node.CDATA_SECTION_NODE) {
							if (!child.getNodeValue().trim().equals("")) {
								String value= child.getNodeValue();
								
								
								StrFieldKit sfk= new StrFieldKit(value, '\n');
								String line= null;
								value= "";
								String crlf= "";
								
								while ((line= sfk.nextField()) != null) {
									value+= crlf + line.trim();
									crlf= "\n";
								}
								
								t.setValue(value.trim());
							}
						}
					}
				}
				
				
				break;
			}
			
		}
		
		return list;
			
	}
	
	
	/**
	 * 
	 * @return the last exception caught
	 */
	public Exception lastException() {
		return lastEx;
	}

	/**
	 * 
	 * @return a simple message indicating the cause of the last error
	 */
	public String getErrMsg() {
		return errMsg;
	}

}
