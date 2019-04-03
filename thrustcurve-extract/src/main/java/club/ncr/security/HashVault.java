package club.ncr.security;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

public class HashVault {

    public static final String read(String key) {
        String value = decypher(key);
        value = Crypto.decode(value);
        value = unsalted(value);
        return value;
    }

    public static final String write(String value) {
        System.out.println("write");
        String escaped = escape(value);
        String salted = salted(escaped);
        String encoded = Crypto.encode(salted);
        String key = cypher(encoded);
        return key;
        //return cypher(Crypto.encode(salted(escape(value))));
    }

    private static String decypher(String key) {
        try {
            return new String(new BASE64Decoder().decodeBuffer(key));
        } catch (IOException iox) {
            //throw new Exception("Failed "+ key +" ["+ iox.getMessage() +"]", iox);
            return null;
        }
    }

    private static String salted(String key) {
        String salted= System.currentTimeMillis() +"|" + key;
        //System.out.println(" + salt("+ key +"): "+ salted);
        return salted;
    }

    private static String unsalted(String key) {
        //System.out.println(" - unsalt("+ key +")");
        return (key.substring(key.indexOf("|") + 1));
    }

    private static String cypher(String key) {
        String cypher = new String(new BASE64Encoder().encode(key.getBytes()));
        //System.out.println(" + cypher("+ key +"): "+ cypher);
        return cypher;
    }

    private static String escape(String key) {
        //System.out.println(" + escape("+ key +")");
        return key;
    }

    private static String unescape(String key) {
        //System.out.println(" - unescape("+ key +")");
        return key;
    }

}
