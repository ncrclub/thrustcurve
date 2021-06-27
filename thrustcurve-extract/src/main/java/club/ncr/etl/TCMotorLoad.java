package club.ncr.etl;

import club.ncr.cayenne.model.MotorDiameter;
import club.ncr.cayenne.model.MotorImpulse;
import club.ncr.dto.ImpulseDTO;
import club.ncr.MotorDbCache;
import club.ncr.cayenne.model.MotorMfg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thrustcurve.TCApiClient;
import org.thrustcurve.api.search.SearchCriteria;
import org.thrustcurve.api.search.SearchResults;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;

public class TCMotorLoad {

    private static final Logger LOG= LoggerFactory.getLogger(TCMotorLoad.class);
    private MotorDbCache cache;

	private boolean runlock;

	public TCMotorLoad() {
	}

	public void execute(String impulse, PrintStream out) {
		
		if (!getRunLock()) { return; }
		
		try {
		
		cache= new MotorDbCache("cayenne-ncrclub.xml");
		cache.setAutoCreate(true);

		MotorImpulse imp = cache.getImpulse(impulse);

		if (imp == null) {
			LOG.error("No Motor Impulses.");
			return;
		}

		SearchCriteria criteria= new SearchCriteria()
				.impulseClass(imp.getImpulse())
				.maxResults(20);

		SearchResults results= update(criteria, out);

		if (results == null) {
			LOG.error("Data Query Returned null.");
		} else if (results.size() == 0) {
			LOG.info("No results.");
		} else if (results.size() < 20) {
			LOG.info("Updated "+ results.size() +" motor records. ["+ imp +"]");
		} else if (results.size() >= 20) {

			Collection<MotorDiameter> diameters = cache.getDiameters();
			for (MotorDiameter diam : diameters) {
				criteria= new  SearchCriteria();
				criteria.impulseClass(imp.getImpulse());
				criteria.diameter(diam.getDiameter());
				criteria.maxResults(49);

				results= update(criteria, out);
				if (results == null) {

				} else if (results.size() > 0 && results.size() < 49) {

					LOG.info("Updated "+ results.size() +" motor records. ["+ imp +","+ diam.getDiameter() +"mm]");

				} else if (results.size() >= 49) {

					for (MotorMfg mfg : cache.getManufacturers()) {
						criteria= new  SearchCriteria();
						criteria.impulseClass(imp.getImpulse());

						criteria.diameter(diam.getDiameter());
						criteria.manufacturer(mfg.getName());
						criteria.maxResults(100);

						results= update(criteria, out);

						if (results == null) {
							LOG.info("null results for "+ mfg.getName() +" ["+ imp +","+ diam.getDiameter() +"mm]");
						} else if (results.size() > 0) {
							LOG.info("Updated "+ results.size() +" motor records. "+ mfg.getName() +" ["+ imp +","+ diam.getDiameter() +"mm]");
						}
					}
				} else {
					LOG.info("No motors found to update for "+ imp +","+ diam.getDiameter() +"mm");
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

	protected ImpulseDTO getNextImpulse(ImpulseDTO currentImpulse) {
		ImpulseDTO[] impulseArray= cache.getImpulses();

		if (currentImpulse == null && impulseArray.length > 0) {
			return impulseArray[0];
		} else {
			
			for (int i= 0; i < impulseArray.length; i++) {
				ImpulseDTO impulse= impulseArray[i];
				if (impulse.equals(currentImpulse)) {
					if (i == impulseArray.length - 1) {
						return null;
					} else {
						return impulseArray[i+1];
					}
				}
			}
			
		}

		return null;
	}

	private SearchResults update(SearchCriteria criteria, PrintStream out) {
		TCApiClient api;
		try {
			api= new TCApiClient();
			
			try {
				SearchResults searchResults= api.search(criteria, true);
				
				if (searchResults == null) {
					LOG.error("No results found for search "+ criteria);
					return null;
				}

				cache.update(searchResults, out);
				
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
