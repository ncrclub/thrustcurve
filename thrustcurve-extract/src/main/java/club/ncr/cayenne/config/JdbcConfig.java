package club.ncr.cayenne.config;

public class JdbcConfig {

    private final String url;
    private final String user;
    private final String password;
    private final String driver;

    public JdbcConfig(String url, String user, String password, String driver) {
        this.url= url;
        this.user= user;
        this.password= password;
        this.driver= driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDriver() {
        return driver;
    }
}
