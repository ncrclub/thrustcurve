package club.ncr.cayenne.dao;

import club.ncr.cayenne.DAO;
import club.ncr.cayenne.model.MotorDataFormat;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<MotorDataFormat> get(Expression filter) {
        SelectQuery query= new SelectQuery(MotorDataFormat.class);
        if (filter != null) {
            query.andQualifier(filter);
        }
        query.addOrdering(new Ordering(MotorDataFormat.NAME.getName(), SortOrder.ASCENDING_INSENSITIVE));
        return (List<MotorDataFormat>)ctx.performQuery(query);
    }

    @Override
    public Collection<MotorDataFormat> getAll() {
        return this.get((Expression)null);
    }

    @Override
    public Map<String, MotorDataFormat> getMap(Expression filter) {
        HashMap<String, MotorDataFormat> map= new HashMap<String, MotorDataFormat>();
        for (MotorDataFormat obj : get(filter)) {
            map.put(obj.getName(), obj);
        }
        return map;
    }
}
