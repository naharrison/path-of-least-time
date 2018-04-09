#!/bin/csh -f

rm -rf least-time-app least-time-app.zip

mvn install
mvn clean compile assembly:single

mkdir least-time-app
cp target/phys2212-1.0-SNAPSHOT-jar-with-dependencies.jar least-time-app
chmod +x least-time-app/phys2212-1.0-SNAPSHOT-jar-with-dependencies.jar
mv least-time-app/phys2212-1.0-SNAPSHOT-jar-with-dependencies.jar least-time-app/least-time.jar
cp -r data least-time-app
zip -r least-time-app.zip least-time-app
rm -rf least-time-app
