package club.ncr.util;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public interface StandardIO {

	public abstract void setOutputStream(OutputStream out);

	public abstract void setErrorStream(OutputStream err);

	public abstract void setInputStream(InputStream in);
	
	public abstract void flush() throws IOException;
	
	public abstract InputStream in() throws IOException;
	public abstract PrintWriter out() throws IOException;
	public abstract PrintWriter err() throws IOException;

}