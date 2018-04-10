/**
 * 
 */
package util.xml;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;


/**
 * @author bmorse
 *
 */
public class XmlTag implements Serializable {

	private static final long serialVersionUID = 4646849156440182170L;
	
	protected int level;					// level in the xml tree
	protected String name;					// tag name
	protected String value;					// tag value, null if none
	protected HashMap<String, String> attributes;		// attribute nv pairs
	
	protected XmlTag parent;
	protected XmlTagList children;
	
	/**
	 * Construct a new XML tag given a name
	 * @param name
	 */
	public XmlTag(String name) {
		this(0, null, name, null, new Properties(), new XmlTagList());
	}
	
	/**
	 * Construct a new XML tag given a name and list of children
	 * 
	 * <br> &lt;name&gt;
	 * <br>&lt;child&gt;<i>...</i>&lt;/child&gt;
	 * <br>&lt;/name&gt;
	 * 
	 * @param name
	 * @param children
	 */
	public XmlTag(String name, XmlTagList children) {
		this(0, null, name, null, new Properties(), children);
	}
	
	/**
	 * Construct a new XML tag given a name and value
	 * 
	 * <br> &lt;name&gt;<i>value</i>&lt;/name&gt;
	 * 
	 * @param name
	 * @param value
	 */
	public XmlTag(String name, String value) {
		this(0, null, name, value, new Properties(), new XmlTagList());
	}
	
	/**
	 * Construct a new XML tag given a parent, and a name
	 * 
	 * <br> &lt;parent&gt;
	 * <br>&lt;name /&gt;
	 * <br>&lt;/parent&gt;
	 * 
	 * @param parent
	 * @param name
	 */
	public XmlTag(XmlTag parent, String name) {
		this((parent != null ? parent.level + 1 : 0), parent, name, null, new Properties(), new XmlTagList());
	}
	
	/**
	 * Construct a new XML tag given a parent, name and value
	 * 
	 * <br> &lt;parent&gt;
	 * <br>&lt;name&gt;<i>value</i>&lt;/name&gt;
	 * <br>&lt;/parent&gt;
	 * 
	 * @param parent
	 * @param name
	 * @param value
	 */
	public XmlTag(XmlTag parent, String name, String value) {
		this((parent != null ? parent.level + 1 : 0), parent, name, value, new Properties(), new XmlTagList());
	}
	
	/**
	 * Construct a new XML tag given a name and attributes
	 * 
	 * <br>&lt;name attribute="value" ... /&gt;
	 * 
	 * @param name
	 * @param attributes
	 */
	public XmlTag(String name, Properties attributes) {
		this(0, null, name, null, attributes, new XmlTagList());
	}
	
	/**
	 * Construct a new XML tag given a parent, name and attributes
	 * 
	 * <br> &lt;parent&gt;
	 * <br>&lt;name attribute="value" ... /&gt;
	 * <br>&lt;/parent&gt;
	 * 
	 * @param parent
	 * @param name
	 * @param attributes
	 */
	public XmlTag(XmlTag parent, String name, Properties attributes) {
		this((parent != null ? parent.level + 1 : 0), parent, name, null, attributes, new XmlTagList());
	}
	
	public XmlTag(int lvl, XmlTag parent, String name) {
		this(lvl, parent, name, null, new Properties(), new XmlTagList());
	}
	
	public XmlTag(int lvl, XmlTag parent, String name, String value) {
		this(lvl, parent, name, value, new Properties(), new XmlTagList());
	}
	
	public XmlTag(int lvl, XmlTag parent, String name, String value, Properties attributes) {
		this(lvl, parent, name, value, (attributes == null ? new Properties() : attributes), new XmlTagList());
	}
	public XmlTag(int lvl, XmlTag parent, String name, String value, XmlTagList children) {
		this(lvl, parent, name, value, new Properties(), children);
	}
	
	@Deprecated
	public XmlTag(int lvl, XmlTag parent, String name, String value, Properties attributes, XmlTagList children) {
		this.level= lvl;
		this.parent= parent;
		this.name= name;
		this.value= value;
		this.attributes= new HashMap<String, String>();
		if (attributes != null) {
			Enumeration keys= attributes.keys();
			while (keys.hasMoreElements()) {
				String key= (String)keys.nextElement();
				this.attributes.put(key, (String)attributes.get(key));
			}
		}
		this.children= (children == null ? new XmlTagList() : children);
	}
	
	public XmlTag(int lvl, XmlTag parent, String name, String value, HashMap<String, String> attributes, XmlTagList children) {
		this.level= lvl;
		this.parent= parent;
		this.name= name;
		this.value= value;
		this.attributes= (attributes == null ? new HashMap<String, String>() : attributes);
		this.children= (children == null ? new XmlTagList() : children);
	}
	
	public XmlTag getParent() {
		return parent;
	}
	
	public XmlTagList getChildren() {
		return children;
	}
	
	public void setChildren(XmlTagList children) {
		this.children= (children == null ? new XmlTagList() : children);
		for (int i= 0; i < this.children.length(); i++) {
			this.children.getTagAt(i).level= level + 1;
			this.children.getTagAt(i).parent= this;
		}
	}
	
	public XmlTagList get(String namespace) {
		XmlTagList matches= new XmlTagList();
		
		int k= namespace.indexOf('.');
		
		if (k == -1) {
			// this is the end of the namespace...
			if (name.equalsIgnoreCase(namespace)) {
				matches.add(this);
			}
			
		} else {
			matches.add(getChildren().seek(namespace.substring(k + 1)));
			
		}
		
		return matches;
	}
	
	public boolean hasChildren() {
		return (children.length() > 0);
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) { this.value= value; }
	public void setValue(int value) { setValue(""+ value); }
	public void setValue(long value) { setValue(""+ value); }
	public void setValue(double value) { setValue(""+ value); }
	public void setValue(boolean value) { setValue(""+ value); }
	public void setValue(Object value) { 
		if (value == null) { 
			setValue(null);
		} else {
			setValue(value.toString()); 
		}
	}
	
	/**
	 * get an attribute of this tag
	 * @param key the name of the attribute to fetch
	 * @return
	 *  the attribute value
	 *  <code>null</code> if the attribute is not set
	 */
	public String getAttribute(String key) {
		return getAttribute(key, null);
	}
	
	public String getAttribute(String key, String ifNull) {
		String value= attributes.get(key);
		if (value == null) {
			value= ifNull;
		}
		return value;
	}
	
	/**
	 * set / overwrite an attribute for this tag
	 * @param key attribute name to add or overwrite
	 * @param value new attribute value
	 */
	public void setAttribute(String key, String value) {
		attributes.put(key, value);
	}
	
	/**
	 * augments / overwrites current attributes with new set
	 * 
	 * @param attributes
	 *  properties bag of new / updated attributes
	 */
	public void setAttributes(Properties attributes) {
		if (attributes == null) {
			return;
		}
		
		Enumeration keys= attributes.keys();
		while (keys.hasMoreElements()) {
			String key= (String)keys.nextElement();
			String attr= (attributes.getProperty(key));
			if (attr == null) {
				attr= "";
			}
			this.attributes.put(key, attr);
		}
	}
	
	/** 
	 * deletes all attributes for this tag
	 *
	 */
	public void clearAttributes() {
		attributes.clear();
	}
	
	public String toString() {
		return toString(level);
	}
	
	public void addChild(XmlTag tag) {
		children.add(tag);
		tag.level= level + 1;
		tag.parent= this;
	}
	
	public void setChildren(XmlTag[] tags) {
		children.clear();
		for (int i= 0; i < tags.length; i++) {
			if (tags[i] != null) {
				addChild(tags[i]);
			}
		}
	}
	
	public void clearChildren() {
		children.clear();
	}
	
	public boolean equals(XmlTag tag) {
		if (!name.equalsIgnoreCase(tag.name)) {
			return false;
		}
		
		if (value != null && tag.value != null) {
			if (!value.equals(tag.value)) {
				return false;
			}
		} else if (value == null && tag.value != null) {
			return false;
		} else if (value != null && tag.value == null) {
			return false;
		}
		
		// compare attributes
		// Enumeration keys= attributes.keys();
		// while (keys.hasMoreElements()) {
			// String key= (String)keys.nextElement();
		for (String key : attributes.keySet()) {
			String val1= attributes.get(key);
			String val2= tag.attributes.get(key);
			
			if (val1 != null && val2 != null) {
				if (!val1.equals(val2)) {
					return false;
				}
			} else {
				return false;
			}
		}
		
		
		if (children != null && tag.children != null) {
			return (children.equals(tag.children));
		} else if (children == null && tag.children == null) {
			return true;
		} else {
			return false;
		}
		
		
	}
	
	public String toString(int lvl) {
		String indent= "";
		for (int i= 0; i < lvl; i++) {
			indent+= "\t";
		}
		
		String xml= indent;
		xml+= "<"+ name;
		
		for (String n : attributes.keySet()) {
			// String n= (String) keys.nextElement();
			String v= attributes.get(n);
			xml+= " "+ n +"=\""+ v +"\"";
			
		}
		
		xml+= ">";
		String nl= "";
		
		if (value != null) {
			
			StringTokenizer st= new StringTokenizer(value, "\r\n");
		
			if (st.countTokens() > 1) {
				// more than one line of data
				nl= "\n"+ indent;
			} else {
				nl= "";
				// only one line of data
			}
			while (st.hasMoreElements()) {
				xml+= nl+ st.nextToken();
			}
			
		}
		
		for (int i= 0; i < children.length(); i++) {
			xml+= "\n"+ children.getTagAt(i).toString(lvl + 1);
		}
		
		xml+= (hasChildren() ? "\n" + indent : "") + nl +"</"+ name +">";
		
		return xml;
	}
	
	public Document toXmlDocument() {
		// int inputType= INPUT_STREAM;
		
		InputStream xmlStream= new ByteArrayInputStream(new String("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" + toString()).getBytes());
		
		DOMParser parser= new DOMParser();
		Document xmlDoc= null;
		
		InputSource src= new InputSource(xmlStream);
		
		try {
			parser.parse(src);
			
			xmlDoc= parser.getDocument();
			
		} catch (IOException iox) {
		//	lastEx= iox;
		//	errMsg= "I/O Exception while parsing xml InputStream ["+ iox.toString() +"]";
			return null;
			
		} catch (SAXException sx) {
		//	lastEx= sx;
		//	errMsg= "Malformed XML or XML Parsing Error ["+ sx.toString() +"]";
			return null;
		}	
		
		return xmlDoc;
		
	}

	public Set<String> getAttributes() {
		return attributes.keySet();
	}

}
