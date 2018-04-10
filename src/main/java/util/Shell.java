package util;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Map;


/**
 * This class represents a runtime environment
 * <p>
 * It holds name,value pairs of Strings
 * 
 * 
 * @author Darb
 *
 */
public class Shell extends Hashtable implements StandardIO {
	
	
	protected OutputStream outStream;
	protected OutputStream errStream;
	protected PrintWriter out;
	protected PrintWriter err;
	protected InputStream in;
	
	//private StandardIO io;
	
	
	
	public Shell(StandardIO io) throws IOException {
		
		in= io.in();
		out= io.out();
		err= io.err();
		
	}
	
	public Shell(Map map) { super(map); }
	
	public RuntimeConfiguration rc() { return new RunCfgKit(this); }
	
	public Shell() {
		
	}
	
	public InputStream getFileStream(String path) throws FileNotFoundException {
		return new FileInputStream(path);
	}
	
	/**
	 * Fetch a value from the environment
	 * <p>
	 * @param name - name of environment variable
	 * @param ifNull - value to return if the name is not set
	 * @return
	 */
	public String fetch(String name, String ifNull) {
		String val= (String)get(name);
		if (val == null) { return ifNull; }
//		if ("".equals(val)) { return ifNull; }
		return val;
	}
	
	public boolean fetch(String name, boolean ifNull) {
		boolean val= ifNull;
		
		String value= "false";
		
		if ((value= (String)get(name)) == null) { return ifNull; }
		
		// safety
		value= value.trim();
		
		if ("true".equalsIgnoreCase(value)) { return true; }
		
//		if ("".equals(val)) { return ifNull; }
		return val;
	}
	
	
	/**
	 * same as fetch(String, null)
	 * @param name
	 * @return
	 */
	public String fetch(String name) { return fetch(name, null); }
	
	public String fetchEnv(String name) { return fetch("env."+ name, null); }

	
	public String[][] fetchParmsStartingWith(String prefix) {
		return rc().fetchStartsWith(prefix);
	}
	/**
	 * Fetch a value from the environment
	 * <p>
	 * if the value cannot be converted to an integer, the ifNull value will be returned
	 * 
	 * @param name
	 * @param ifNull
	 * @return
	 */
	public int fetch(String name, int ifNull) {
		String val= null;
		if ((val= fetch(name)) == null) { return ifNull; }
		try { 
			return Integer.parseInt(val);
		} catch (NumberFormatException nfx) {
			return ifNull;
		}
	}
	
	public long fetch(String name, long ifNull) {
		String val= null;
		if ((val= fetch(name)) == null) { return ifNull; }
		try { 
			return Long.parseLong(val);
		} catch (NumberFormatException nfx) {
			return ifNull;
		}
	}
	
	public double fetch(String name, double ifNull) {
		String val= null;
		if ((val= fetch(name)) == null) { return ifNull; }
		try { 
			return Double.parseDouble(val);
		} catch (NumberFormatException nfx) {
			return ifNull;
		}
	}
	/**
	 * Set an environment variable
	 * <p>
	 * If the value is <code>null</code>, the variable will be removed from the environment
	 * 
	 * @param name - name of the variable
	 * @param value - value to associate with the name
	 */
	public void set(String name, String value) {
		if (value == null) { 
			remove(name); 
			return;
		}
		put(name, value);
	}
	
	public void set(String name, int value) {
		set(name, ""+ value);
	}
	
	public void set(String name, long value) {
		set(name, ""+ value);
	}
	
	


	
	public OutputStream getOutputStream() throws IOException {
		return outStream;
	}
	
	
	public PrintWriter err() {
		return err;
	}
	
	public void flush() throws IOException {
		out.flush();
//		outStream.flush();
	}
	
	public PrintWriter out() {
		return out;
	}
	
	public InputStream in() {
		return in;
	}
	
	public void setErrorStream(OutputStream err) {
		if (this.err != null) { this.err.flush(); }
		this.errStream= err;
		this.err= new PrintWriter(err);
	}
	
	public void setOutputStream(OutputStream out) {
		if (this.out != null) { this.out.flush(); }
		this.outStream= out;
		this.out= new PrintWriter(out);
	}
	
	public void setInputStream(InputStream in) {
		this.in= in;
	}
	
	public String xlatePath(String path) {
		return path;
	}
	
}
