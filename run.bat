@echo off

if "%1"=="" goto BLANK

SET JAR=KinectController-all-0.0.3.jar

cp build/libs/%JAR% lib

cd lib

call java -cp %JAR% com.wafflesoft.kinectcontroller.Main -d -f ../%1

cd ..

goto DONE

:BLANK
    echo "Missing Parameter!"
    echo "usage:"
    echo "  $run.bat <config filename>"
    echo "NOTE: <config filename> is a .gdef file relative to the root directory of this project."
    
:DONE
@echo on