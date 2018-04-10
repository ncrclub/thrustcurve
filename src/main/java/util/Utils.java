package util;

import util.kits.StrFieldKit;

import java.text.NumberFormat;
import java.util.Properties;


public class Utils {

	
	public static StatsKit availMem= new StatsKit(null);
	public static StatsKit freeMem= new StatsKit(null);
	public static StatsKit usedMem= new StatsKit(null);

/**
 * Parses command line options and returns them in a Properties Object for easy reference
 * expects that command-line options are always in the form &lt;optionIdentifier&gt;&lt;name&gt;[=&lt;value&gt;]
 * 
 * <br>
 * <br> <b>example command line</b>:
 * <br>
 * <pre> -option1=value -option2=value standardArg1 standardArg2
 *  where '-' is the optionIdentifier
 *  'option1' and 'option2' are the option names to be used as a key into the Properties Object
 *  standardArg1 and standardArg2 will not be processed by this routine
 *  the return value would be 2, pointing to standardArg1's position in the argument array list
 *  
 * note:
 *  if no '=&lt;value&gt;' is found, the option is assumed to be a boolean flag and it's value is true
 * </pre>
 * @return integer index denoting the end of 'options' section in the command line argument list
 */
public static int parseNVOptions(Properties opts, String[] args, String optionIdentifier) {
	
	int a= 0; 
	
	while (a < args.length && (optionIdentifier != null && args[a].startsWith(optionIdentifier))) {
		String opt= args[a++];
		String name= "";
		String value= null;
		
		int k= opt.indexOf('=');
		if (k > 0) {
			name= opt.substring(0, k);
			value= opt.substring(k + 1);
		} else {
			name= opt;
			value= "true";
		}
		
		opts.put(name, value);
		
	}
	
	
	return a;
	
}


public static int parseOptions(Properties opts, String[] args, String optionIdentifier) {
//public static Hashtable parseOptions(String[] args) {

	int a= 0;
	
	while (a < args.length && args[a].startsWith(optionIdentifier)) {
		String opt= args[a];
		String val= null;
		
		// args[a++]= null;
		a++;
		
		if (a >= args.length) {
			val= "true";
		} else {
		
			val= args[a];
			if (val.startsWith(optionIdentifier)) {
				// next parameter is actually another option
				// this option must be a boolean flag value
				val= "true";
			} else {
				// args[a++]= null;
				a++;
			}

		}
		opts.setProperty(opt, val);
	}
	
	return a;
}

public static void updateMemStats() {
	if (freeMem == null) {
		availMem= new StatsKit(null);
		freeMem= new StatsKit(null);
		usedMem= new StatsKit(null);
	}
	
	Runtime rt= Runtime.getRuntime();
	long available= rt.totalMemory() / 1024;
	long free= rt.freeMemory() / 1024;
	long used= (available - free);
//	long max= rt.maxMemory() / 1024;
	
	availMem.update(available);
	usedMem.update(used);
	freeMem.update(free);
}

public static String memStats() {
	String stats= "";
	Runtime rt= Runtime.getRuntime();
	double available= rt.totalMemory() / (1024.0);
	double free= rt.freeMemory() / (1024.0);
	double used= (available - free);
	double max= rt.maxMemory() / (1024.0);
	
	String aUom= "Kb";
	String fUom= "Kb";
	String uUom= "Kb";
	String mUom= "Kb";
	
	double aStat= available;
	double fStat= free;
	double uStat= used;
	double mStat= max;
	
	if (aStat > 1000) {
		aStat/= 1024;
		aUom= "Mb";
		if (aStat > 1000) {
			aStat/= 1024;
			aUom= "Gb";
		}
	}
	
	if (fStat > 1000) {
		fStat/= 1024;
		fUom= "Mb";
		if (fStat > 1000) {
			fStat/= 1024;
			fUom= "Gb";
		}
	}
	
	if (uStat > 1000) {
		uStat/= 1024;
		uUom= "Mb";
		if (uStat > 1000) {
			uStat/= 1024;
			uUom= "Gb";
		}
	}
	
	if (mStat > 1000) {
		mStat/= 1024;
		mUom= "Mb";
		if (mStat > 1000) {
			mStat/= 1024;
			mUom= "Gb";
		}
	}
	
	NumberFormat nf= NumberFormat.getNumberInstance();
	nf.setMaximumFractionDigits(2);
	nf.setMinimumFractionDigits(2);
	
	NumberFormat pcnt= NumberFormat.getPercentInstance();
	pcnt.setMaximumFractionDigits(2);
	pcnt.setMinimumFractionDigits(2);
	
	stats= 
		   "[ MemUtil ]      Used: "+ nf.format(uStat) +" "+ uUom +"\t"+ pcnt.format(used / available)
		+"\n[ MemUtil ]      Free: "+ nf.format(fStat) +" "+ fUom +"\t"+ pcnt.format(free / available)
		+"\n[ MemUtil ] Allocated: "+ nf.format(aStat) +" "+ aUom +"\t"+ pcnt.format(available / max)
		+"\n[ MemUtil ]   Maximum: "+ nf.format(mStat) +" "+ mUom
		;
	
	return stats;
}


public static String prefixText(String prefix, String text) {
	if (text == null) {
		text= "{null}";
	}
	
	if (prefix.equals("")) {
		return text;
	}
	
	
	int k= text.indexOf('\n');
	if (k == -1) {
		// no new lines
		return prefix +" "+ text;
	}
	
	StrFieldKit parser= new StrFieldKit(text, '\n');
	parser.keepWhitespace();
	String msg= "";
	
	String[] lines= parser.fields();
	
	for (int i= 0; i < lines.length; i++) {
		msg+= prefix +" "+ lines[i] + (i < (lines.length - 1) ? "\n" : "");
		
	}
	
	return msg;
}


/*
public static boolean loadJdbcProperties(String jdbcFn, StringBuffer errMsg, boolean encrypted) {
	if (jdbcFn == null || jdbcFn.equals("")) {
		if (errMsg != null) {
			errMsg.append("jdbcFn is null or empty, cannot load properties from file");
		}
		return false;
	}
	
	RunCfgKit props= new RunCfgKit();
	
	if (!props.load(jdbcFn)) {
		if (errMsg != null) {
			errMsg.append(props.errMsg);
		}
		return false;
	}
	
	String[][] nv= props.fetchStartsWith("jdbc");
	
	try {
		for (int i= 0; i < nv.length; i++) {
			if (encrypted) {
				System.setProperty(nv[i][0], Secret.decrypt(nv[i][1]));
			} else {
				System.setProperty(nv[i][0], nv[i][1]);
			}
		}
	} catch (Exception e) {
		if (errMsg != null) {
			errMsg.append(e.toString());
		}
		return false;
	}
	
	return true;
}
*/

public static String[] bubbleSort(String[] data, boolean ignoreCase) {
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
			if (ignoreCase) {
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
} // class



