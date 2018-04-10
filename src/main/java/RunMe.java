import org.thrustcurve.TCApiClient;
import org.thrustcurve.api.SearchCriteria;
import org.thrustcurve.api.search.SearchResults;

public class RunMe {

    public static void main(String[] args) throws Exception {

        TCApiClient tc= new TCApiClient();

        SearchResults found= tc.search(new SearchCriteria().impulseClass("I"), false);

        found.getRecords().forEach(r -> System.out.println(r.toJson()));

        SearchResults mCtiMotors= tc.search(
                new SearchCriteria()
                        .impulseClass("M")
                        .diameter(75)
                        .manufacturerAbbreviation("cti"),
               true);

        found.getRecords().forEach(r -> System.out.println(r.toJson()));

    }
}
