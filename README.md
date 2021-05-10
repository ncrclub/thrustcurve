## Thrustcurve.org

All credit due to the individuals who maintain the Thrustcurve.org service as the best source for hobby rocket motor data.


> http://thrustcurve.org

### Thrustcurve/NCR JSON API V1
**Motor Diameters**
> https://ncrocketry.club/flight/thrustcurve/api/v1/list/diameters

List of all motor diameters (mm).
```
[6.0,10.5,13.0,18.0,...,81.0,98.0,132.0,152.0,161.0]
```

**Motor Impulses**
> https://ncrocketry.club/flight/thrustcurve/api/v1/list/impulses

List of all motor impulses.
```
["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R"]
```

**Motor Manufacturers**
> https://ncrocketry.club/flight/thrustcurve/api/v1/list/manufacturers

List of Manufacturers name and abbreviation.
```
[
 {"name":"AeroTech","abbreviation":"AeroTech"},
 {"name":"Alpha Hybrids","abbreviation":"Alpha"},
 {"name":"Animal Motor Works","abbreviation":"AMW"},
 {"name":"Apogee Components","abbreviation":"Apogee"},
 {"name":"Cesaroni Technology","abbreviation":"Cesaroni"},
 {"name":"Contrail Rockets","abbreviation":"Contrail"},
 {"name":"Ellis Mountain","abbreviation":"Ellis"},
 {"name":"Estes Industries","abbreviation":"Estes"},
 {"name":"Gorilla Rocket Motors","abbreviation":"Gorilla"},
 {"name":"Hypertek","abbreviation":"Hypertek"},
 {"name":"Kosdon TRM","abbreviation":"Kosdon"},
 {"name":"Kosdon by AeroTech","abbreviation":"KBA"},
 {"name":"Loki Research","abbreviation":"Loki"},
 {"name":"Propulsion Polymers","abbreviation":"PP"},
 {"name":"Public Missiles, Ltd.","abbreviation":"PML"},
 {"name":"Quest Aerospace","abbreviation":"Quest"},
 {"name":"R.A.T.T. Works","abbreviation":"RATT"},
 {"name":"Raketenmodellbau Klima","abbreviation":"Klima"},
 {"name":"Research","abbreviation":"Research"},
 {"name":"Roadrunner Rocketry","abbreviation":"Roadrunner"},
 {"name":"Rocketvision Flight-Star","abbreviation":"RV"},
 {"name":"Sky Ripper Systems","abbreviation":"SkyR"},
 {"name":"Southern Cross Rocketry","abbreviation":"SCR"},
 {"name":"West Coast Hybrids","abbreviation":"WCH"}
]
```

### Thrustcurve/NCR JSON API V2 (Search)

**Search Parameters**
> name
>
Motor name or designation.

> diameter
>
Motor diameter (mm).

> mfg

Motor manufacturer name or abbreviation.

> impulse

Motor impulse class (A-O).

> with_data=true

Return data file information for motors if it exists.

**Search by Diameter**
> `/flight/thrustcurve/api/v2/search/diameter/{diameter}`

> https://ncrocketry.club/flight/thrustcurve/api/v2/search/diameter/38?impulse=J&mfg=cesaroni

**Search by Impulse**
> `/flight/thrustcurve/api/v2/search/impulse/{impulse}`

> https://ncrocketry.club/flight/thrustcurve/api/v2/search/impulse/I?diameter=38&mfg=cesaroni

Sample
> http://localhost:8080/flight/thrustcurve/api/v2/search/diameter/38?impulse=J&mfg=cesaroni&name=J94
```$xslt
[
  {"name":"J94",
   "brandName":"644-J94-P",
   "designation":"644J94-P",
   "identifier":"644J94-P [Cesaroni]",
   "motorCase":"Pro38-5G",
   "impulse":"J",
   "totalImpulse":644.0,
   "weight":372.9,"burnTime":6.82,
   "averageThrust":94.4,
   "maxThrust":172.5,
   "diameter":38.0,
   "length":367.0,
   "externalId":"1046",
   "propellant":"Mellow",
   "manufacturer":{
      "name":"Cesaroni Technology",
      "abbreviation":"Cesaroni"
   },
   "data":[]
  }
]
```

## Northern Colorado Rocketry
NCR provides JSON endpoints to query the NCR motor database.

### NCR Motor Database
The NCR Motor Database is a combination of the Thrustcurve data and motor data maintained by the club launch log administrators.

### NCR Motors API V1
> https://ncrocketry.club/flight/api/v1/motors

# Thrustcurve Java Client SDK Library
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

