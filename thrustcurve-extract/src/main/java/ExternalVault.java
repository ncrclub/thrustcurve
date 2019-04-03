import club.ncr.security.Crypto;
import club.ncr.security.HashVault;
import org.apache.cayenne.configuration.PasswordEncoding;

public class ExternalVault implements PasswordEncoding {

    @Override
    public String decodePassword(String s, String key) {
        return HashVault.read(System.getenv(s));
    }

    @Override
    public String encodePassword(String s, String key) {
        System.out.println("Password: "+ HashVault.read(System.getenv(s)));
        return HashVault.read(System.getenv(s));
    }
}
