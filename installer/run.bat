@echo off

if "%1"=="" goto BLANK

cd lib

call java -cp KinectController.jar com.wafflesoft.kinectcontroller.Main -d -f ../%1

cd ..

goto DONE

:BLANK
    echo "Missing Parameter!"
    echo "usage:"
    echo "  $run.bat <config filename>"
    echo "NOTE: <config filename> is a .gdef file relative to the root directory of this project."
    
:DONE
@echo on