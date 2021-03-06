import club.ncr.security.HashVault;
import org.apache.cayenne.configuration.PasswordEncoding;

public class ExternalVault implements PasswordEncoding {

    @Override
    public String decodePassword(String s, String key) {
        return HashVault.read(System.getenv(s));
    }

    @Override
    public String encodePassword(String s, String key) {
        return HashVault.read(System.getenv(s));
    }
}
