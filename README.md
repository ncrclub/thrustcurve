# ThrustCurve Java API Library
## Northern Colorado Rocketry

> https://ncrocketry.club

> http://thrustcurve.org

## Example
```
    public static void main(String[] args) throws Exception {
 
        SearchCriteria criteria= new SearchCriteria()
                .impulseClass("K")
                .diameter(54)
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
  ```

