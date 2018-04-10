# ThrustCurve Java API Library
## Northern Colorado Rocketry

> https://ncrocketry.club

> http://thrustcurve.org

## Example
```
    public static void main(String[] args) throws Exception {
 
        TCApiClient tc= new TCApiClient();
 
        SearchResults found= tc.search(new SearchCriteria().impulseClass("I"), false);
 
        found.getRecords().forEach(r -> System.out.println(r.toJson()));
  
    }
    
    public static void main(String[] args) throws Exception {
  
        SearchResults mCtiMotors= tc.search(
                new SearchCriteria()
                        .impulseClass("M")
                        .diameter(75)
                        .manufacturerAbbreviation("cti"),
               true); // download data files 
 
        found.getRecords().forEach(r -> System.out.println(r.toJson()));
    }
  ```

