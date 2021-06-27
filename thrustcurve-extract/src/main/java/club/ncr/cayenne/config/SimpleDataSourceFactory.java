package club.ncr.cayenne.config;

import org.apache.cayenne.configuration.DataNodeDescriptor;
import org.apache.cayenne.configuration.server.DataSourceFactory;
import org.apache.cayenne.datasource.DataSourceBuilder;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class SimpleDataSourceFactory implements DataSourceFactory {

    private static Map<String, JdbcConfig> config = new HashMap<>();
    private static Map<String, DataSource> sources = new HashMap<>();

    @Override
    public DataSource getDataSource(DataNodeDescriptor db) throws Exception {
        DataSource source = sources.get(db.getName());
        if (source == null) {
            JdbcConfig jdbc = config.get(db.getName());

            if (jdbc == null) {
                throw new Exception("Missing database configuration: "+ db.getName());
            }

            source= DataSourceBuilder.url(jdbc.getUrl())
                    .driver(jdbc.getDriver())
                    .userName(jdbc.getUser())
                    .password(jdbc.getPassword())
                    .pool(1, 5)
                    .build();

        }

        return source;
    }

    public static void addConfiguration(String name, JdbcConfig config) {
        SimpleDataSourceFactory.config.put(name, config);
    }

}
