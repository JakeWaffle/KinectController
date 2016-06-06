@echo off

cd config
call compileConfigInterpreter.bat
cd ..

call rm lib/KinectController.jar
call gradle clean fatJar

@echo on