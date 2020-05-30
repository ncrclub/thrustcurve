import club.ncr.security.Crypto;
import org.apache.cayenne.configuration.PasswordEncoding;

public class External implements PasswordEncoding {

    @Override
    public String decodePassword(String s, String key) {
        return Crypto.decode(System.getenv(s));
    }

    @Override
    public String encodePassword(String s, String key) {
        return Crypto.encode(System.getenv(s));
    }
}
