package org.thrustcurve.api.xml;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author bmorse
 *
 */
public class XmlTagList implements Serializable, Iterable<XmlTag>{

	public Iterator<XmlTag> iterator() {
		return list.iterator();
	}
	
	private static final long serialVersionUID = -256840522714749759L;
	Vector<XmlTag> list;
	
	public XmlTagList() {
		list= new Vector<XmlTag>();
	}
	
	public int length() {
		return list.size();
	}
	
	public void add(XmlTag tag) {
		list.add(tag);
	}
	
	public void add(XmlTagList tags) {
		for (int i= 0; i < tags.length(); i++) {
			list.add(tags.getTagAt(i));
		}
	}
	
	/**
	 * get the xml tag at the specified index
	 * 
	 * although xml has no enforced order to the tags, other than
	 * parent-child relationships, some order can be assertained
	 * by the order in which the xml document was written.
	 * 
	 * index in this context, represents the ordinal position of
	 * as read in from the source document
	 * 
	 * @param index
	 * @return the xml tag (null if no tag exists at that index)
	 */
	public XmlTag getTagAt(int index) {
		try {
			return (XmlTag) list.get(index);
		} catch (IndexOutOfBoundsException ioobx) {
			return null;
		}
	}
	
	/**
	 * get a chunk of xml tags at the specified namespace
	 * 
	 * in this context, namespace is a series of tag names separated by a dot (.)
	 * 
	 * namespace example:
	 * root.tag.child.leaf and root.branch.[..].branch.leaf
	 * 
	 * root.tag1 would return all child tags of tag1 in a list
	 * 
	 * if the document had multiple tag1's, then seek will return 
	 *               
	 * @param namespace a string representation of the branch to get from this xml structure
	 * @return the tags at the specified branch
	 */
	public XmlTagList seek(String namespace) {
		
		XmlTagList matches= new XmlTagList();
		
		// namespace example:
		// xml.tag.child.leaf would return all leaves that match
		// or
		// xml.tag.child - would return all leaves in this path

		
		int k= namespace.indexOf('.');
		
		if (k == -1) {
			// this is the end of the namespace...
			
			for (int i= 0; i < list.size(); i++) {
				XmlTag t= (XmlTag) list.elementAt(i);
				if (t.name.equalsIgnoreCase(namespace)) {
					matches.add(t);
				}
			}
			return matches;	
			
		} else {
			
			// recurse through children, seeking for matches
			
			String lookingFor= namespace.substring(0, k);
			
			for (int i= 0; i < list.size(); i++) {
				XmlTag t= (XmlTag) list.elementAt(i);
				
				if (t.name.equalsIgnoreCase(lookingFor)) {
					matches.add(t.getChildren().seek(namespace.substring(k + 1)));
				}
			}
		}
		
		return matches;	
	}
	
	public boolean equals(XmlTagList tags) {
		
		if (list.size() != tags.list.size()) {
			return false;
		}
		
		for (int i= 0; i < list.size(); i++) {
			XmlTag tag1= (XmlTag) list.elementAt(i);
			XmlTag tag2= (XmlTag) tags.list.elementAt(i);
			if (!tag1.equals(tag2)) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Format and return the XML structure represented by this tag list
	 */
	public String toString() {
		String xml= "";
		for (int i= 0; i < list.size(); i++) {
			xml+= ((XmlTag) list.elementAt(i)).toString(0) + (i < list.size() - 1 ? "\n" : "");
		}
		return xml;
	}
	
	
	/**
	 * removes all tags from this list
	 *
	 */
	public void clear() {
		list.clear();
	}
	
	/**
	 * get a specific xml tag by namespace (see getBranch() for a definition of namespace)
	 * @param namespace
	 * @return
	 */
	public XmlTag getTag(String namespace) {
		
		XmlTagList list= null;
		
		if ((list= seek(namespace)) != null) {
			return list.getTagAt(0);
		}
		
		return null;
	}

	
}
