
#RELEASE_VERSION=$(shell cat ../release-version)
#SNAPSHOT_VERSION=$(shell cat ../snapshot-version)

snapshot:
	#mvn versions:set -DnewVersion=$(SNAPSHOT_VERSION)-SNAPSHOT -f thrustcurve-client/pom.xml
	#mvn versions:set -DnewVersion=$(SNAPSHOT_VERSION)-SNAPSHOT -f thrustcurve-extract/pom.xml
	mvn versions:set -DnewVersion=$(SNAPSHOT_VERSION)-SNAPSHOT
	#cd thrustcurve-client && mvn versions:set -DnewVersion=$(SNAPSHOT_VERSION)-SNAPSHOT
	#cd thrustcurve-extract && mvn versions:set -DnewVersion=$(SNAPSHOT_VERSION)-SNAPSHOT

version-release:
	#mvn versions:set -DnewVersion=$(RELEASE_VERSION) -f thrustcurve-client/pom.xml
	#mvn versions:set -DnewVersion=$(RELEASE_VERSION) -f thrustcurve-extract/pom.xml
	mvn versions:set -DnewVersion=$(RELEASE_VERSION)
	#cd thrustcurve-client && mvn versions:set -DnewVersion=$(RELEASE_VERSION)
	#cd thrustcurve-extract && mvn versions:set -DnewVersion=$(RELEASE_VERSION)
