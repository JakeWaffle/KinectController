@echo off

REM java -jar target/KinectController-0.0.1-jar-with-dependencies.jar

java -cp "target/KinectController-0.0.1-jar-with-dependencies.jar;lib/*" com.lcsc.hackathon.Main %*

@echo on