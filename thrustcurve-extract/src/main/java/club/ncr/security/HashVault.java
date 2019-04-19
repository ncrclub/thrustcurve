package club.ncr.security;

import java.util.Base64;

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
    }

    private static String decypher(String key) {
        return new String(Base64.getDecoder().decode(key));
    }

    private static String salted(String key) {
        String salted= System.currentTimeMillis() +"|" + key;
        return salted;
    }

    private static String unsalted(String key) {
        return (key.substring(key.indexOf("|") + 1));
    }

    private static String cypher(String key) {
        String cypher = new String(Base64.getEncoder().encode(key.getBytes()));
        return cypher;
    }

    private static String escape(String key) {
        return key;
    }

    private static String unescape(String key) {
        return key;
    }

}
