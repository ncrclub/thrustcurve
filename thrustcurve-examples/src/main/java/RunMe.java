import org.thrustcurve.TCApiClient;
import org.thrustcurve.api.search.SearchCriteria;
import org.thrustcurve.api.search.SearchResults;

public class RunMe {

    public static void main(String[] args) throws Exception {

        TCApiClient tc= new TCApiClient();

        SearchResults found= tc.search(new SearchCriteria().impulseClass("I"), false);

        // found.getRecords().forEach(r -> System.out.println(r.toJson()));

        SearchCriteria criteria= new SearchCriteria()
                .impulseClass("K")
                .diameter(54.0f)
                .manufacturer("aerotech")
                .maxResults(3);

        System.out.println(criteria);

        SearchResults results = tc.search(criteria, true);

        if (results == null) {
            // nothing found
            System.err.println("No Results.");
            System.exit(0);
        }

        results.getRecords().forEach(r -> System.out.println(r.toJson()));

    }
}
