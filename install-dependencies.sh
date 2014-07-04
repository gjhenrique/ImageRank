#! /bin/sh

mvn install:install-file -Dfile=lib/JWave-1.0.jar -DpomFile=lib/JWave-1.0.pom -Dsources=lib/JWave-1.0-sources.jar

mvn install:install-file -Dfile=lib/distributed-slim-1.0.jar -DpomFile=lib/distributed-slim-1.0.pom -Dsources=lib/distributed-slim-1.0-sources.jar

# JFeatureLib external jars
mvn install:install-file -Dfile=lib/imageanalysis-1.0.0.jar -DpomFile=lib/imageanalysis-1.0.0.pom
mvn install:install-file -Dfile=lib/lire-0.9.4-beta-1.jar -DpomFile=lib/lire-0.9.4-beta-1.pom
