import org.thrustcurve.api.ThrustCurveApi;
import org.thrustcurve.api.search.SearchCriteria;
import org.thrustcurve.api.search.SearchRequest;
import org.thrustcurve.api.search.SearchResults;

public class RunMe {

    public static void main(String[] args) throws Exception {

        ThrustCurveApi tc= new ThrustCurveApi();

        SearchResults found= tc.search(new SearchCriteria().impulseClass("I"), false);

        found.getRecords().forEach(r -> System.out.println(r.toJson()) );
    }
}
