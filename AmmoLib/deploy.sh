#!/bin/bash

VERSION=1.4.6
REPO_URI=http://fiddler.aterrasys.com:8081/nexus/content/repositories/vanderbilt/
ARTIFACT=ammolib

mvn deploy:deploy-file \
  -DgroupId=edu.vu.isis \
  -DartifactId=${ARTIFACT} \
  -Dversion=${VERSION} \
  -DpomFile=upload-pom.xml \
  -Dpackaging=jar \
  -DgeneratePom=false \
  -Dfile=${ARTIFACT}.jar \
  -Djavadoc=${ARTIFACT}-javadoc.jar \
  -DrepositoryId=nexus-vanderbilt \
  -Durl=${REPO_URI}

#  -Dsources=dist/lib/${GW_API}-${VERSION}-sources.jar \
