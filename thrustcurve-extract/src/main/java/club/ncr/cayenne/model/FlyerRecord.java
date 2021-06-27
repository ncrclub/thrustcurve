package club.ncr.cayenne.model;

import club.ncr.cayenne.model.auto._FlyerRecord;

public class FlyerRecord extends _FlyerRecord {

	/*
	public static List<FlyerRecord> get(ObjectContext ctx, Expression matchExp) {
		
		SelectQuery query= new SelectQuery(FlyerRecord.class, matchExp);
		
		query.addOrdering(new Ordering(FlyerRecord.LAST_NAME.getName(), SortOrder.ASCENDING));
		query.addOrdering(new Ordering(FlyerRecord.FIRST_NAME.getName(), SortOrder.ASCENDING));
		
		List<FlyerRecord> list= new Retry<>(() -> ctx.performQuery(query)).execute(3);
		
		return list;
		
	}
	 */

}
