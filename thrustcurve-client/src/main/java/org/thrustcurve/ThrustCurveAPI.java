package org.thrustcurve;
// http://www.thrustcurve.org/download/ThrustCurveAPI.java

/*
 * Example program to show usage of ThrustCurve.org API.
 * See http://www.thrustcurve.org/searchapi.shtml for details.
 * Copyright (c) John Coker Software, 2011.
 * All rights under copyright reserved by the author.
 */

import java.io.*;
import java.net.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;
import org.w3c.dom.*;

public class ThrustCurveAPI
{
    public static final String PROGRAM = "ThrustCurveAPI";

    /**
     * This is the entry point into the program.
     * @param args command-line arguments
     */
    public static void main(String[] args)
    {
        new ThrustCurveAPI(args);
    }

    /**
     * The constructor is called by the main function.
     * @param args command-line arguments
     */
    private ThrustCurveAPI(String[] args)
    {
        _url      = "http://www.thrustcurve.org/servlets/search";
        _outDir   = null;
        _search   = new Hashtable<Criterion,String>();
        _motorIds = new ArrayList<String>();

        if (args == null || args.length < 1)
        {
            printUsage(System.out);
            return;
        }

        for (int i = 0; i < args.length; i++)
        {
            if (!args[i].startsWith("-"))
            {
                System.err.printf("%s: unexpected argument \"%s\".\n", PROGRAM, args[i]);
                printUsage(System.err);
                System.exit(1);
            }

            if (args[i].equalsIgnoreCase("-url") ||
                    args[i].equalsIgnoreCase("-api"))
            {
                if (i + 1 >= args.length)
                {
                    System.err.printf("%s: Missing argument to %s option (URL expected).\n",
                            PROGRAM, args[i]);
                    printUsage(System.err);
                    System.exit(1);
                }
                _url = args[++i];
            }
            else if (args[i].equalsIgnoreCase("-out") ||
                    args[i].equalsIgnoreCase("-dir"))
            {
                if (i + 1 >= args.length)
                {
                    System.err.printf("%s: Missing argument to %s option (directory expected).\n",
                            PROGRAM, args[i]);
                    printUsage(System.err);
                    System.exit(1);
                }
                _outDir = args[++i];
            }
            else
            {
                Criterion  found;
                String     value;

                found = null;
                for (Criterion c : getCriteria())
                {
                    if (c.argument.equalsIgnoreCase(args[i]))
                    {
                        found = c;
                        break;
                    }
                }
                if (found == null)
                {
                    System.err.printf("%s: Unknown option \"%s\".\n",
                            PROGRAM, args[i]);
                    printUsage(System.err);
                    System.exit(1);
                }

                if (i + 1 >= args.length)
                {
                    System.err.printf("%s: Missing argument to %s option (value expected).\n",
                            PROGRAM, args[i]);
                    printUsage(System.err);
                    System.exit(1);
                }
                value = args[++i];

                if (_search.containsKey(found))
                {
                    System.err.printf("%s: Duplicate %s argument for search criteria.\n",
                            PROGRAM, args[i]);
                    printUsage(System.err);
                    System.exit(1);
                }
                _search.put(found, value);
            }
        }
        if (_search.size() < 1)
        {
            System.err.printf("%s: No search criteria specified.\n", PROGRAM);
            printUsage(System.err);
            System.exit(1);
        }

        // send the search request
        Document searchResult = sendSearchRequest();
        if (searchResult == null)
            System.exit(3);

        // parse the search response
        extractSearchResults(searchResult);
        if (_motorIds == null || _motorIds.size() < 1)
        {
            System.err.printf("%s: No motors matched the search criteria.\n", PROGRAM);
            System.exit(1);
        }

        // send the download request
        Document downloadResult = sendDownloadRequest();
        if (downloadResult == null)
            System.exit(3);

        // write the downloaded data to local files
        extractDownloadResults(downloadResult);
    }

    /**
     * Format the search request, send it to the configured URL, read
     * the response and parse it as an XML document.
     * @return response document
     */
    private Document sendSearchRequest()
    {
        URL            url;
        URLConnection  conn;
        Document       result;

        // save the search request document
        {
            File  file;

            if (_outDir == null)
                file = new File("search-request.xml");
            else
                file = new File(_outDir, "search-request.xml");
            try
            {
                Writer  w;

                w = new FileWriter(file);
                writeSearchRequest(w);
                w.close();
            }
            catch (Exception ex)
            {
                System.err.printf("%s: Unable to save search request XML to \"%s\".\n",
                        PROGRAM, file);
                printException(ex);
            }
        }

        // parse the URL
        try
        {
            url = new URL(_url);
        }
        catch (Exception ex)
        {
            System.err.printf("%s: Invalid URL for search API!\n", PROGRAM);
            printException(ex);
            return null;
        }

        // send the search API request document
        try
        {
            OutputStream  stream;

            conn = url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);

            stream = conn.getOutputStream();
            writeSearchRequest(new OutputStreamWriter(stream));
            stream.flush();
            stream.close();
        }
        catch (Exception ex)
        {
            System.err.printf("%s: Unable to send XML request to search API!\n", PROGRAM);
            printException(ex);
            return null;
        }

        // get the response from the server
        {
            InputStream  stream;

            try
            {
                stream = conn.getInputStream();
            }
            catch (Exception ex)
            {
                System.err.printf("%s: Unable to send request to search API!\n", PROGRAM);
                printException(ex);
                return null;
            }

            // read the response as an XML document
            try
            {
                DocumentBuilderFactory  factory;
                DocumentBuilder         builder;

                factory = DocumentBuilderFactory.newInstance();
                factory.setValidating(false);

                builder = factory.newDocumentBuilder();
                result  = builder.parse(stream);
            }
            catch (Exception ex)
            {
                System.err.printf("%s: Unable to read XML response from search API!\n", PROGRAM);
                printException(ex);
                return null;
            }
        }

        // save the search response document
        saveResponse("search-response.xml", result);

        return result;
    }

    /**
     * Extract the IDs for motors that matched the search criteria.
     * @param result search result document
     */
    private void extractSearchResults(Document result)
    {
        NodeList  list;

        _motorIds = new ArrayList<String>();

        list = result.getElementsByTagName("motor-id");
        for (int i = 0; i < list.getLength(); i++)
        {
            Node  id;

            id = list.item(i);
            _motorIds.add(id.getTextContent());
        }
    }

    /**
     * Format the download request, send it to the configured URL, read
     * the response and parse it as an XML document.
     * @return response document
     */
    private Document sendDownloadRequest()
    {
        URL            url;
        URLConnection  conn;
        Document       result;

        // save the download request document
        {
            File  file;

            if (_outDir == null)
                file = new File("download-request.xml");
            else
                file = new File(_outDir, "download-request.xml");
            try
            {
                Writer  w;

                w = new FileWriter(file);
                writeDownloadRequest(w);
                w.close();
            }
            catch (Exception ex)
            {
                System.err.printf("%s: Unable to save download request XML to \"%s\".\n",
                        PROGRAM, file);
                printException(ex);
            }
        }

        // parse the URL
        try
        {
            String  altPath;

            altPath = _url.replace("search", "download");
            url     = new URL(altPath);
        }
        catch (Exception ex)
        {
            System.err.printf("%s: Invalid URL for download API!\n", PROGRAM);
            printException(ex);
            return null;
        }

        // send the download API request document
        try
        {
            OutputStream  stream;

            conn = url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);

            stream = conn.getOutputStream();
            writeDownloadRequest(new OutputStreamWriter(stream));
            stream.flush();
            stream.close();
        }
        catch (Exception ex)
        {
            System.err.printf("%s: Unable to send XML request to download API!\n", PROGRAM);
            printException(ex);
            return null;
        }

        // get the response from the server
        {
            InputStream  stream;

            try
            {
                stream = conn.getInputStream();
            }
            catch (Exception ex)
            {
                System.err.printf("%s: Unable to send request to download API!\n", PROGRAM);
                printException(ex);
                return null;
            }

            // read the response as an XML document
            try
            {
                DocumentBuilderFactory  factory;
                DocumentBuilder         builder;

                factory = DocumentBuilderFactory.newInstance();
                factory.setValidating(false);

                builder = factory.newDocumentBuilder();
                result  = builder.parse(stream);
            }
            catch (Exception ex)
            {
                System.err.printf("%s: Unable to read XML response from download API!\n", PROGRAM);
                printException(ex);
                return null;
            }
        }

        // save the download response document
        saveResponse("download-response.xml", result);

        return result;
    }

    /**
     * Extract the data files returned by the download API
     * @param result download result document
     */
    private void extractDownloadResults(Document result)
    {
        NodeList  list;

        _motorIds = new ArrayList<String>();

        list = result.getElementsByTagName("data");
        for (int i = 0; i < list.getLength(); i++)
        {
            Node    one;
            String  data;
            File    file;

            // get the encoded data
            one  = list.item(i);
            data = one.getTextContent();

            // decode and write to file
            file = null;
            try
            {
                String            name;
                FileOutputStream  s;

                name = Integer.toString(i + 1) + ".data";
                if (_outDir == null)
                    file = new File(name);
                else
                    file = new File(_outDir, name);

                s = new FileOutputStream(file);
                decodeData(data, s);
                s.close();
            }
            catch (Exception ex)
            {
                System.err.printf("%s: Unable to save data file to \"%s\".\n",
                        PROGRAM, file);
                printException(ex);
            }
        }
    }

    /**
     * Format the XML for the search API request and write it out.
     * Note that this uses the criteria collected in the _search map.
     * @param w output writer
     */
    private void writeSearchRequest(Writer w) throws IOException
    {
        if (_search == null || _search.size() < 1)
            throw new IllegalStateException("no criteria to search for");

        // note that the schema information allows you to validate your document:
        // http://www.w3.org/2001/03/webdata/xsv
        w.write("<?xml version=\"1.0\" encoding=\"ascii\"?>\n");
        w.write("<search-request\n");
        w.write(" xmlns=\"http://www.thrustcurve.org/2011/SearchRequest\"\n");
        w.write(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
        w.write(" xsi:schemaLocation=\"http://www.thrustcurve.org/2011/SearchRequest http://www.thrustcurve.org/2011/search-request.xsd\">\n");

        for (Criterion criterion : getCriteria())
        {
            String  value;

            value = _search.get(criterion);
            if (value != null)
            {
                w.write("  <");
                w.write(criterion.elementName);
                w.write(">");
                w.write(value);
                w.write("</");
                w.write(criterion.elementName);
                w.write(">\n");
            }
        }

        w.write("  <data-fields>*</data-fields>\n");
        w.write("</search-request>\n");
        w.flush();
    }

    /**
     * Format the XML for the download API request and write it out.
     * Note that this uses the list of motor IDs collected in _motorIds.
     * @param w output writer
     */
    private void writeDownloadRequest(Writer w) throws IOException
    {
        if (_motorIds == null || _motorIds.size() < 1)
            throw new IllegalStateException("no motor IDs to download data for");

        // note that the schema information allows you to validate your document:
        // http://www.w3.org/2001/03/webdata/xsv
        w.write("<?xml version=\"1.0\" encoding=\"ascii\"?>\n");
        w.write("<download-request\n");
        w.write(" xmlns=\"http://www.thrustcurve.org/2014/DownloadRequest\"\n");
        w.write(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
        w.write(" xsi:schemaLocation=\"http://www.thrustcurve.org/2014/DownloadRequest http://www.thrustcurve.org/2014/download-request.xsd\">\n");

        if (_motorIds.size() == 1)
        {
            String  id;

            // a single motor, use compact form (optional)
            id = _motorIds.get(0);

            w.write("  <motor-id>");
            w.write(id);
            w.write("</motor-id>\n");
        }
        else
        {
            // multiple motors, use list form
            w.write("  <motor-ids>\n");
            for (String id : _motorIds)
            {
                w.write("    <id>");
                w.write(id);
                w.write("</id>\n");
            }
            w.write("  </motor-ids>\n");
        }

        // get data points as well as file content
        //w.write("  <data>both</data>\n");

        w.write("</download-request>\n");
        w.flush();
    }

    private boolean saveResponse(String file, Document response)
    {
        File  path;

        if (_outDir == null)
            path = new File(file);
        else
            path = new File(_outDir, file);

        try
        {
            OutputStream        stream;
            TransformerFactory  factory;
            Transformer         serializer;
            DOMSource           source;
            StreamResult        result;

            stream = new FileOutputStream(path);

            factory    = TransformerFactory.newInstance();
            serializer = factory.newTransformer();

            serializer.setOutputProperty(OutputKeys.INDENT, "yes");

            source = new DOMSource(response);
            result = new StreamResult(stream);
            serializer.transform(source, result);
            stream.close();
        }
        catch (Exception ex)
        {
            System.err.printf("%s: Invalid to save response to file \"%s\"\n",
                    PROGRAM, path);
            printException(ex);
            return false;
        }

        return true;
    }

    public static byte[] decodeData(String data) throws IOException {

        final StringBuffer sb = new StringBuffer();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                baos.write(b);
                sb.append((char)b);
            }
        };

        decodeData(data, out);
        out.flush();
        out.close();
        baos.close();

        return baos.toByteArray();
        //return sb.toString();
    }

    /**
     * Decode the specified Base64 string and write binary data
     * to the given stream.
     * @param str Base64 encoded string
     * @param w output stream
     */
    private static void decodeData(String str, OutputStream w)
            throws IOException
    {
        StringReader  r;
        int           c1;

        if (str == null || str.length() < 1)
            return;

        r = new StringReader(str);

        // spin through the input string
        c1 = readToNonSpace(r);
        while (c1 > 0)
        {
            int  c2, c3, c4;
            int  p1, p2, p3, p4;
            int  pad, n;

            pad = 0;

            c2 = readToNonSpace(r);
            c3 = readToNonSpace(r);
            c4 = readToNonSpace(r);
            if (c4 < 0)
                throw new IllegalArgumentException("Encoded string ends prematurely.");

            p1 = charToBits(c1);
            p2 = charToBits(c2);

            if (c3 == PAD_CHAR)
            {
                p3 = 0;
                pad++;
            }
            else
                p3 = charToBits(c3);

            if (c4 == PAD_CHAR)
            {
                p4 = 0;
                pad++;
            }
            else
                p4 = charToBits(c4);

            if (p1 < 0 || p2 < 0 || p3 < 0 || p4 < 0)
                throw new IllegalArgumentException("Encoded string contains invalid characters.");

            n = (p1 << 18) | (p2 << 12) | (p3 << 6) | p4;

            w.write((byte) ((n & 0xFF0000) >> 16));
            if (pad < 2)
                w.write((byte) ((n & 0x00FF00) >> 8));
            if (pad < 1)
                w.write((byte) (n & 0x0000FF));

            c1 = readToNonSpace(r);
            if (c1 > 0 && pad > 0)
                throw new IllegalArgumentException("Extra characters found after padding.");
        }
    }

    private static final String BASE64_CHARS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    private static final char PAD_CHAR = '=';

    private static int readToNonSpace(Reader r)
            throws IOException
    {
        int  c;

        c = r.read();
        while (c >= 0 && Character.isWhitespace(c))
            c = r.read();

        return c;
    }

    static short[]  _charToBits;

    private static int charToBits(int c)
    {
        // build the reverse mapping array
        if (_charToBits == null)
        {
            _charToBits = new short[128];

            for (int i = 0; i < _charToBits.length; i++)
                _charToBits[i] = -1;

            for (int i = 0; i < BASE64_CHARS.length(); i++)
                _charToBits[BASE64_CHARS.charAt(i)] = (byte) i;
            _charToBits[PAD_CHAR] = 0;
        }

        // use it to look up the value
        if (c < 0 || c >= _charToBits.length)
            return -1;
        else
            return _charToBits[c];
    }

    private String                 _url;
    private String                 _outDir;
    private Map<Criterion,String>  _search;
    private List<String>           _motorIds;

    /**
     * This class stores the element name of the search criterion
     * passed in the search-request.
     */
    private static class Criterion
    {
        public Criterion(String e, String a)
        {
            if (e == null || e.length() < 1)
                throw new IllegalArgumentException("element name must be specified");
            if (a == null || a.length() < 1)
                throw new IllegalArgumentException("argument must be specified");

            elementName = e;
            argument    = a;
        }

        public String  elementName;
        public String  argument;

        public int hashCode()
        {
            return elementName.hashCode();
        }

        public boolean equals(Object o)
        {
            if (o instanceof Criterion)
            {
                Criterion  rhs;

                rhs = (Criterion) o;
                return elementName.equals(rhs.elementName);
            }
            else
                return false;
        }
    };

    private static Iterable<Criterion> getCriteria()
    {
        if (_criteria == null)
        {
            _criteria = new ArrayList<Criterion>();
            _criteria.add(new Criterion("manufacturer",       "-mfr"));
            _criteria.add(new Criterion("designation",        "-desig"));
            _criteria.add(new Criterion("brand-name",         "-brand"));
            _criteria.add(new Criterion("common-name",        "-name"));
            _criteria.add(new Criterion("impulse-class",      "-class"));
            _criteria.add(new Criterion("diameter",           "-diam"));
            _criteria.add(new Criterion("type",               "-type"));
            _criteria.add(new Criterion("cert-org",           "-cert"));
            _criteria.add(new Criterion("has-data-files",     "-data"));
            _criteria.add(new Criterion("info-updated-since", "-newinfo"));
            _criteria.add(new Criterion("data-updated-since", "-newdata"));
            _criteria.add(new Criterion("max-results",        "-max"));
        }
        return _criteria;
    }

    private static void printUsage(PrintStream s)
    {
        s.printf("usage: %s [ -url API ] [ -out directory ] criteria ...\n", PROGRAM);

        s.printf("  where the criteria are:\n");
        for (Criterion c : getCriteria())
        {
            String  value;

            value = c.elementName.replaceAll("-", " ");
            s.printf("    %-8s  %s\n", c.argument, value);
        }
    }

    private static void printException(Exception ex)
    {
        Throwable  innermost;

        System.err.printf("  %s: %s\n",
                ex.getClass().getName(),
                ex.getMessage());

        innermost = null;
        for (Throwable cause = ex.getCause(); cause != null; cause = cause.getCause())
        {
            System.err.printf("    %s: %s\n",
                    cause.getClass().getName(),
                    cause.getMessage());
            innermost = cause;
        }

        if (innermost != null)
        {
            int  count = 0;
            for (StackTraceElement ste : innermost.getStackTrace())
            {
                System.err.printf("    [%d] %s\n",
                        count, ste.toString());
                count++;
            }
        }
    }

    private static List<Criterion>  _criteria;
}
