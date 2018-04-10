package club.ncr.etl;

import club.ncr.cayenne.MotorDiameter;
import club.ncr.cayenne.MotorImpulse;
import club.ncr.motors.MotorDbCache;
import club.ncr.cayenne.MotorMfg;
import org.apache.cayenne.access.DataContext;
import org.thrustcurve.TCApiClient;
import org.thrustcurve.api.search.SearchCriteria;
import org.thrustcurve.api.search.SearchResults;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class TCMotorLoad {

    private MotorDbCache cache;
	private DataContext ctx;
	
	private boolean runlock;
	
	static private MotorImpulse nextImpulse= null;
	
	public TCMotorLoad() {
	}
	
	public void execute() {
		
		if (!getRunLock()) { return; }
		
		try {
		
		//ctx= Andromeda.createDataContext();
		
		//cache= new MotorDbCache(ctx);
		
		MotorImpulse imp = getNextImpulse();

		SearchCriteria criteria= new  SearchCriteria();
		criteria.impulseClass(imp.getImpulse());
		criteria.maxResults(20);
		SearchResults results= update(criteria);
			
		if (results.size() == 0) {

		} if (results.size() > 0 && results.size() < 20) {
			// log.info("Updated "+ results.size() +" motor records. ["+ imp.getImpulse() +"]");
		} else if (results.size() >= 20) {

			Collection<MotorDiameter> diameters = cache.getDiameters();
			for (MotorDiameter diam : diameters) {
				criteria= new  SearchCriteria();
				criteria.impulseClass(imp.getImpulse());
				criteria.diameter(diam.getDiameter());
				criteria.maxResults(49);

				results= update(criteria);
				if (results == null) {

				} else if (results.size() > 0 && results.size() < 49) {

					// log.info("Updated "+ results.size() +" motor records. ["+ imp.getImpulse() +","+ diam.getDiameter() +"mm]");

				} else if (results.size() >= 49) {

					for (MotorMfg mfg : cache.getManufacturers()) {
						criteria= new  SearchCriteria();
						criteria.impulseClass(imp.getImpulse());

						criteria.diameter(diam.getDiameter());
						criteria.manufacturer(mfg.getName());
						criteria.maxResults(100);

						results= update(criteria);

						if (results.size() > 0) {
							// log.info("Updated "+ results.size() +" motor records. "+ mfg.getName() +" ["+ imp.getImpulse() +","+ diam.getDiameter() +"mm]");
						}
								
					}
				} else {
					// log.info("No motors found to update for "+ imp.getImpulse() +","+ diam.getDiameter() +"mm");
				}
						
			}
		}
			
		
		// log.info("Done with update sequence.");
		
		} catch (Throwable anything) {
			// log.error(anything);
		} finally {
			releaseRunLock();
		}
			
	}

	protected MotorImpulse getNextImpulse() {
		ArrayList<MotorImpulse> impulseArray= new ArrayList<MotorImpulse>();

		/*
		for (MotorImpulse i : cache.getImpulses()) {
			impulseArray.add(i);
		}
		*/
		
		// impulseArray.clear();
		// impulseArray.add(cache.getImpulse("K"));
		
		Collections.sort(impulseArray);
		
		
		if (nextImpulse == null) {
			nextImpulse= impulseArray.get(0);
		} else {
			
			for (int i= 0; i < impulseArray.size(); i++) {
				MotorImpulse impulse= impulseArray.get(i);
				if (impulse.equals(nextImpulse)) {
					if (i == impulseArray.size() - 1) {
						nextImpulse= impulseArray.get(0);		// back to first impulse
						break;
					} else {
						nextImpulse= impulseArray.get(i+1);
						break;
					}
				}
			}
			
		}
		
		
		MotorImpulse imp= nextImpulse;
		return imp;
	}

	private SearchResults update(SearchCriteria criteria) {
		TCApiClient api;
		try {
			api= new TCApiClient();
			
			try {
				SearchResults searchResults= api.search(criteria, true);
				
				if (searchResults == null) { return null; }
				
				cache.update(searchResults);
				
				return searchResults;
				
			} catch (IOException e) {
				// log.error(e.getMessage(), e);
			}
			
			
		} catch (Throwable e) {
			// log.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	

	private boolean getRunLock() {
		return runlock= (runlock ? false : true);
	}
	
	private void releaseRunLock() {
		runlock= false;
	}




}
