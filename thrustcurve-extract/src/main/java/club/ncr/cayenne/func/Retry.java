package club.ncr.cayenne.func;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import org.apache.cayenne.CayenneRuntimeException;

import java.util.List;
import java.util.function.Supplier;


public class Retry<T> {
    private final Supplier<List<T>> call;
    public Retry(Supplier<List<T>> call) {
        this.call = call;
    }
    public List<T> execute(int retries) {
        try {
            return call.get();
        } catch (Exception e) {
            if (retries > 0 &&
                    (e.getCause() != null
                            && e.getCause() instanceof CommunicationsException
                            || e instanceof CayenneRuntimeException
                    )
            ) {
                return execute(retries - 1);
            }
            throw e;
        }
    }

}
