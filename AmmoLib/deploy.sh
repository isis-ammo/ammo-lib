#!/bin/bash

VERSION=1.3.4
REPO_URI=http://fiddler.aterrasys.com:8081/nexus/content/repositories/vanderbilt/
ARTIFACT=ammolib

mvn deploy:deploy-file \
  -DgroupId=edu.vu.isis \
  -DartifactId=${ARTIFACT} \
  -Dversion=${VERSION} \
  -Dpackaging=jar \
  -DgeneratePom=false \
  -DpomFile=update-pom.xml \
  -Dfile=${ARTIFACT}.jar \
  -Djavadoc=${ARTIFACT}-javadoc.jar \
  -DrepositoryId=nexus-vanderbilt \
  -Durl=${REPO_URI}

#  -Dsources=dist/lib/${GW_API}-${VERSION}-sources.jar \
