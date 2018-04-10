package club.ncr.cayenne;

import club.ncr.cayenne.auto._MotorDataFormat;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;

import java.util.HashMap;
import java.util.List;

public class MotorDataFormat extends _MotorDataFormat {
	
	public static MotorDataFormat createNew(String name, String extension, DataContext ctx) {
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

	public static List<MotorDataFormat> get(DataContext ctx, Expression filter) {
		SelectQuery query= new SelectQuery(MotorDataFormat.class);
		if (filter != null) {
			query.andQualifier(filter);
		}
		query.addOrdering(new Ordering(MotorDataFormat.NAME_PROPERTY, SortOrder.ASCENDING_INSENSITIVE));
		return (List<MotorDataFormat>)ctx.performQuery(query);
	}
	
	public static HashMap<String, MotorDataFormat> getMap(DataContext ctx, Expression filter) {
		HashMap<String, MotorDataFormat> map= new HashMap<String, MotorDataFormat>();
		for (MotorDataFormat obj : get(ctx, filter)) {
			map.put(obj.getName(), obj);
		}
		return map;
	}
}
