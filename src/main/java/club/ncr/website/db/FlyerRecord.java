package club.ncr.website.db;

import club.ncr.website.db.auto._FlyerRecord;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;

import java.util.List;

public class FlyerRecord extends _FlyerRecord {

	public static List<FlyerRecord> get(DataContext ctx, Expression matchExp) {
		
		SelectQuery query= new SelectQuery(FlyerRecord.class, matchExp);
		
		query.addOrdering(new Ordering(FlyerRecord.LAST_NAME_PROPERTY, SortOrder.ASCENDING));
		query.addOrdering(new Ordering(FlyerRecord.FIRST_NAME_PROPERTY, SortOrder.ASCENDING));
		
		List<FlyerRecord> list= ctx.performQuery(query);
		
		return list;
		
	}

}
