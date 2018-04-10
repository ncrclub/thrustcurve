package util.kits;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Log4JKit {

	/* public static member variables */
	public static final String STD_OUT= "stdout";
	public static final String STD_ERR= "stderr";
	
	private static boolean configured= false;
	
	private class Unknown {
	 //Empty
	}
	
	public static Logger getLog(Object obj) {
		if (obj== null) { return getLog(Log4JKit.class); }
		return getLog(obj.getClass());
	}
	
	public static Logger getLog(Class c) {
		if (c == null) {
			System.err.println("Cannot get logger for null class.");
			c= Unknown.class;
		}
		
		if (!configured) { 
			configured= true;
		}
		
		Logger log= null;
		if (c == null) { c = Log4JKit.class;}

		log= LogManager.getLogger(c);
		
		return log;
	}
	
	

}
