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
### Output
```
SearchCriteria:[(diameter=54), (max-results=3), (impulse-class=K), (manufacturer=aerotech)]
{
	"data":
		 [
		 	{
		 	"license":"PD"
		 	, "motor-id":"1034"
		 	, "format":"RASP"
		 	, "info-url":"http://www.thrustcurve.org/simfilesearch.jsp?id=2047"
		 	, "source":"user"
		 	, "simfile-id":"2047"
		 	, "data-url":"http://www.thrustcurve.org/simfilesearch.jsp?id=2047"
		 }
		 ]
	, "motor-id":"1034"
	, "length":358.0
	, "impulse-class":"K"
	, "burn-time-s":2.8
	, "prop-info":"White Lightning"
	, "manufacturer":"AeroTech"
	, "common-name":"K535"
	, "data-files":"1"
	, "case-info":""
	, "updated-on":"2014-07-31"
	, "manufacturer-abbrev":"AeroTech"
	, "prop-weight-g":745.0
	, "avg-thrust-n":535.0
	, "info-url":"http://www.thrustcurve.org/motorsearch.jsp?id=1034"
	, "designation":"HP-K535W"
	, "max-thrust-n":655.0
	, "tot-impulse-ns":1434.0
}
{
	"data":
		 [
		 	{
		 	"license":null
		 	, "motor-id":"590"
		 	, "format":"RASP"
		 	, "info-url":"http://www.thrustcurve.org/simfilesearch.jsp?id=1380"
		 	, "source":"mfr"
		 	, "simfile-id":"1380"
		 	, "data-url":"http://www.thrustcurve.org/simfilesearch.jsp?id=1380"
		 }
		 ]
	, "motor-id":"590"
	, "length":627.0
	, "impulse-class":"K"
	, "burn-time-s":2.14
	, "prop-info":"White Lightning"
	, "manufacturer":"AeroTech"
	, "common-name":"K1050"
	, "data-files":"1"
	, "case-info":"RMS-54/2800"
	, "updated-on":"2014-07-22"
	, "manufacturer-abbrev":"AeroTech"
	, "prop-weight-g":1265.0
	, "avg-thrust-n":1132.92
	, "info-url":"http://www.thrustcurve.org/motorsearch.jsp?id=590"
	, "designation":"K1050W"
	, "max-thrust-n":2172.0
	, "tot-impulse-ns":2426.35
}
{
	"data":
		 [
		 	{
		 	"license":"PD"
		 	, "motor-id":"356"
		 	, "format":"ALT4"
		 	, "info-url":"http://www.thrustcurve.org/simfilesearch.jsp?id=98"
		 	, "source":"cert"
		 	, "simfile-id":"98"
		 	, "data-url":"http://www.thrustcurve.org/simfilesearch.jsp?id=98"
		 }
		 	, {
		 	"license":"PD"
		 	, "motor-id":"356"
		 	, "format":"RASP"
		 	, "info-url":"http://www.thrustcurve.org/simfilesearch.jsp?id=99"
		 	, "source":"cert"
		 	, "simfile-id":"99"
		 	, "data-url":"http://www.thrustcurve.org/simfilesearch.jsp?id=99"
		 }
		 	, {
		 	"license":"PD"
		 	, "motor-id":"356"
		 	, "format":"CompuRoc"
		 	, "info-url":"http://www.thrustcurve.org/simfilesearch.jsp?id=524"
		 	, "source":"cert"
		 	, "simfile-id":"524"
		 	, "data-url":"http://www.thrustcurve.org/simfilesearch.jsp?id=524"
		 }
		 	, {
		 	"license":null
		 	, "motor-id":"356"
		 	, "format":"RockSim"
		 	, "info-url":"http://www.thrustcurve.org/simfilesearch.jsp?id=971"
		 	, "source":"user"
		 	, "simfile-id":"971"
		 	, "data-url":"http://www.thrustcurve.org/simfilesearch.jsp?id=971"
		 }
		 ]
	, "motor-id":"356"
	, "length":676.0
	, "impulse-class":"K"
	, "burn-time-s":2.28
	, "prop-info":"White Lightning"
	, "manufacturer":"AeroTech"
	, "common-name":"K1050"
	, "data-files":"4"
	, "case-info":"SU 54x676"
	, "updated-on":"2014-07-22"
	, "manufacturer-abbrev":"AeroTech"
	, "prop-weight-g":1362.2
	, "avg-thrust-n":1050.0
	, "info-url":"http://www.thrustcurve.org/motorsearch.jsp?id=356"
	, "designation":"K1050W-SU"
	, "max-thrust-n":2164.0
	, "tot-impulse-ns":2530.0
}

Process finished with exit code 0
```

