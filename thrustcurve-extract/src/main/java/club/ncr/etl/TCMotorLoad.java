package club.ncr.etl;

import club.ncr.cayenne.MotorDiameter;
import club.ncr.cayenne.MotorImpulse;
import club.ncr.motors.MotorDbCache;
import club.ncr.cayenne.MotorMfg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thrustcurve.TCApiClient;
import org.thrustcurve.api.search.SearchCriteria;
import org.thrustcurve.api.search.SearchResults;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TCMotorLoad {

    private static final Logger LOG= LoggerFactory.getLogger(TCMotorLoad.class);
    private MotorDbCache cache;

	private boolean runlock;

	public TCMotorLoad() {
	}

	public void execute(String impulse) {
		
		if (!getRunLock()) { return; }
		
		try {
		
		cache= new MotorDbCache("cayenne-ncrclub.xml");
		
		MotorImpulse imp = cache.getImpulse(impulse);

		if (imp == null) {
			LOG.error("No Motor Impulses.");
			return;
		}


		SearchCriteria criteria= new SearchCriteria()
				.impulseClass(imp.getImpulse())
				.maxResults(20);

		SearchResults results= update(criteria);
			
		if (results.size() == 0) {

		} if (results.size() < 20) {
			LOG.info("Updated "+ results.size() +" motor records. ["+ imp.getImpulse() +"]");
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

					LOG.info("Updated "+ results.size() +" motor records. ["+ imp.getImpulse() +","+ diam.getDiameter() +"mm]");

				} else if (results.size() >= 49) {

					for (MotorMfg mfg : cache.getManufacturers()) {
						criteria= new  SearchCriteria();
						criteria.impulseClass(imp.getImpulse());

						criteria.diameter(diam.getDiameter());
						criteria.manufacturer(mfg.getName());
						criteria.maxResults(100);

						results= update(criteria);

						if (results == null) {
							LOG.info("null results for "+ mfg.getName() +" ["+ imp.getImpulse() +","+ diam.getDiameter() +"mm]");
						} else if (results.size() > 0) {
							LOG.info("Updated "+ results.size() +" motor records. "+ mfg.getName() +" ["+ imp.getImpulse() +","+ diam.getDiameter() +"mm]");
						}
					}
				} else {
					LOG.info("No motors found to update for "+ imp.getImpulse() +","+ diam.getDiameter() +"mm");
				}
						
			}
		}
			
		
		LOG.info("Done with update sequence.");
		
		} catch (Throwable anything) {
			LOG.error(anything.getMessage(), anything);
		} finally {
			releaseRunLock();
		}
			
	}

	protected MotorImpulse getNextImpulse(MotorImpulse currentImpulse) {
		List<MotorImpulse> impulseArray= cache.getImpulses().stream().sorted().collect(Collectors.toList());

		if (currentImpulse == null && impulseArray.size() > 0) {
			return impulseArray.get(0);
		} else {
			
			for (int i= 0; i < impulseArray.size(); i++) {
				MotorImpulse impulse= impulseArray.get(i);
				if (impulse.equals(currentImpulse)) {
					if (i == impulseArray.size() - 1) {
						return null;
					} else {
						return impulseArray.get(i+1);
					}
				}
			}
			
		}

		return null;
	}

	private SearchResults update(SearchCriteria criteria) {
		TCApiClient api;
		try {
			api= new TCApiClient();
			
			try {
				SearchResults searchResults= api.search(criteria, true);
				
				if (searchResults == null) {
					LOG.error("No results found for search "+ criteria);
					return null;
				}
				
				cache.update(searchResults);
				
				return searchResults;
				
			} catch (IOException e) {
				LOG.error(e.getMessage(), e);
			}
			
			
		} catch (Throwable e) {
			LOG.error(e.getMessage(), e);
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
