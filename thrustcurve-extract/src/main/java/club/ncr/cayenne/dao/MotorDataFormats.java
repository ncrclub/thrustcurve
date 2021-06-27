package club.ncr.cayenne.dao;

import club.ncr.cayenne.DAO;
import club.ncr.cayenne.func.Retry;
import club.ncr.cayenne.model.MotorDataFormat;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;

import java.util.*;

public class MotorDataFormats implements DAO<MotorDataFormat> {

    private final ObjectContext ctx;

    public MotorDataFormats(ObjectContext ctx) {
        this.ctx = ctx;
    }

    public MotorDataFormat createNew(String name, String extension) {
        MotorDataFormat record= new MotorDataFormat();
        ctx.registerNewObject(record);
        record.setName(name);
        if (extension == null) {
            extension = name.toLowerCase();
            if (extension.length() > 4) {
                extension= extension.substring(0, 4);
            }
        }
        record.setFileExtension(extension);
        ctx.commitChanges();
        return record;

    }

    @Override
    public List<MotorDataFormat> get(Optional<Expression> filter) {
        SelectQuery query= new SelectQuery(MotorDataFormat.class);
        if (filter != null && filter.isPresent()) {
            query.andQualifier(filter.get());
        }
        query.addOrdering(new Ordering(MotorDataFormat.NAME.getName(), SortOrder.ASCENDING_INSENSITIVE));
        return new Retry<>(() -> ctx.performQuery(query)).execute(3);
    }

    @Override
    public Collection<MotorDataFormat> getAll() {
        return this.get(Optional.empty());
    }

    @Override
    public Map<String, MotorDataFormat> getMap(Expression filter) {
        HashMap<String, MotorDataFormat> map= new HashMap<String, MotorDataFormat>();
        for (MotorDataFormat obj : get(Optional.ofNullable(filter))) {
            map.put(obj.getName(), obj);
        }
        return map;
    }
}
