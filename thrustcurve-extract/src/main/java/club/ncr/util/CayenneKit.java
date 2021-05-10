package club.ncr.util;

import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.Orderings;
import org.apache.cayenne.query.SelectQuery;

import java.util.Arrays;

public class CayenneKit {

    public static SelectQuery select(Class object, Expression filter, Ordering ... orderBy) {
        if (orderBy == null) {
            return select(object, filter, (Orderings)null);
        }
        return select(object, filter, new Orderings().then(Arrays.asList(orderBy)));
    }

    public static SelectQuery select(Class object, Expression filter, Orderings orderBy) {
        SelectQuery query= new SelectQuery(object);
        if (filter != null) {
            query.andQualifier(filter);
        }
        if (orderBy != null) {
            query.addOrderings(orderBy);
        }
        return query;
    }

}
