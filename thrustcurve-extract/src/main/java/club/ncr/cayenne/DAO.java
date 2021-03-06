package club.ncr.cayenne;

import org.apache.cayenne.BaseDataObject;
import org.apache.cayenne.exp.Expression;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DAO<T extends BaseDataObject> {

    Map<String, T> getMap(Expression filter);
    List<T> get(Optional<Expression> filter);
    Collection<T> getAll();

}
