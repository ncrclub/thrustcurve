
#RELEASE_VERSION=$(shell cat ../release-version)
#SNAPSHOT_VERSION=$(shell cat ../snapshot-version)

snapshot:
	mvn versions:set -DnewVersion=$(SNAPSHOT_VERSION)-SNAPSHOT

version-release:
	mvn versions:set -DnewVersion=$(RELEASE_VERSION)
