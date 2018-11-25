import club.ncr.security.Crypto;
import org.apache.cayenne.configuration.PasswordEncoding;

public class DbCrypto implements PasswordEncoding {

    @Override
    public String decodePassword(String s, String key) {
        return Crypto.decode(s);
    }

    @Override
    public String encodePassword(String s, String key) {
        return Crypto.encode(s);
    }
}
