package util;

import java.util.Vector;

/**
 * Various sort routines
 * 
 * @author bmorse
 *
 */
public class Sort {
	
	public static final byte SORT_FLAG_DEFAULTS= 0;
	public static final byte SORT_FLAG_IGNORECASE= 1;
	public static final byte SORT_FLAG_ASCENDING= 2;
	public static final byte SORT_FLAG_DESCENDING= 3;

//	
//	/**
//	 * uniq - see the unix command uniq
//	 * 
//	 * <p>Assumes: sorted list of strings
//	 * <br>This algorithm is a cheap trick.  By assuming a sorted list, the duplicates can easily be filtered out.
//	 * 
//	 * @param list of sorted strings
//	 * @return a list of sorted strings with no duplicates
//	 */
//    public static Vector<String> uniq(Vector<String> list) {
//        if (list == null || list.isEmpty()) {
//                return new Vector<String>();
//        }
//
//        Vector<String> filtered= new Vector<String>();
//
//        for (int i= 0; i < list.size(); i++) {
//                
//               	filtered.add(list.elementAt(i));
//                while (i < list.size() - 1 && list.elementAt(i).equals(list.elementAt(i+1))) {
//                	i++;
//                }
//        }
//
//        return filtered;
//    }
    
	/**
	 * uniq - see the unix command uniq
	 * 
	 * <p>Assumes: sorted list of strings
	 * <br>This algorithm is a cheap trick.  By assuming a sorted list, the duplicates can easily be filtered out.
	 * 
	 * @param list of sorted strings
	 * @return a list of sorted strings with no duplicates
	 */
    public static Vector<Object> uniq(Vector<Comparable<?>> list) {
        if (list == null || list.isEmpty()) {
                return new Vector<Object>();
        }

        Vector<Object> filtered= new Vector<Object>();

        for (int i= 0; i < list.size(); i++) {
                
               	filtered.add(list.elementAt(i));
                while (i < list.size() - 1 && list.elementAt(i).equals(list.elementAt(i+1))) {
                	i++;
                }
        }

        return filtered;
    }

    
    
    /*
    public static Object[] bubble(Object[] data) {
    	return bubble(data, SORT_FLAG_DEFAULTS);
    }
    
    public static Object[] bubble(Object[] data, byte flags) {
    	if (data == null) {
    		return new String[] {};
    	}
    	
    	String[] sorted= new String[data.length];
    	
    	for (int i= 0; i < data.length; i++) {
    		sorted[i]= data[i];
    		if (sorted[i] == null) {
    			sorted[i]= "";
    		}
    	}
    	
    	for (int i= 0; i < sorted.length - 1; i++) {
    		for (int j= i + 1; j < sorted.length; j++) {
    			if ((flags ^ SORT_FLAG_IGNORECASE) == 0) {		// guessing here
    				if (sorted[i].compareToIgnoreCase(sorted[j]) > 0) { 
    					// System.out.println(sorted[i] +" > "+ sorted[j]);
    					String t= sorted[i];
    					sorted[i]= sorted[j];
    					sorted[j]= t;
    				}
    				
    			} else {
    				if (sorted[i].compareTo(sorted[j]) > 0) { 
    					String t= sorted[i];
    					sorted[i]= sorted[j];
    					sorted[j]= t;
    				}
    				
    			}
    		}
    		
    	}
    	
    	return sorted;
    }
    */
}
