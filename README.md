# Kinect Controller

## Description

This project's goal is to make the Kinect compatible with any possible video game on a computer. The idea behind it is that the user supplies a
configuration file that defines the gestures that the Kinect Controller will observe. These gestures will be tied to one or many reactions that
have the potential to do a lot of different things from emulating keyboard events to executing programs or macros.

After this project gets some headway and we reach a near 1.0 release, then there should be a good amount of configuration
files already created for various games and applications of the project.

Refer to the wiki for more information on the project and how the configuration files work.

## Building Prerequisites

### Windows

- Microsoft Kinect SDK v1.8

    - Download and install from: https://download.microsoft.com/download/E/1/D/E1DEC243-0389-4A23-87BF-F47DE869FC1A/KinectSDK-v1.8-Setup.exe

- JDK 8.0

    - Download and install from: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
    
    You must also define an environment variable called `JAVA_HOME` that points to the JDK installation directory. For example:

    	set JAVA_HOME=c:\Program Files (x86)\Java\jdk1.8.0_32

### Linux

- GCC 5.x

	- Download and install from: http://gcc.gnu.org/releases.html

    - Or use `apt`:
    	
	    	sudo apt-get install g++

- LibUSB 1.0.x

    - Download and install from: http://sourceforge.net/projects/libusb/files/libusb-1.0/

    - Or use `apt`:

	    	sudo apt-get install libusb-1.0-0-dev

- LibUDEV

		sudo apt-get install libudev-dev

- JDK 8.0

    - Download and install from: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

    - Or use the package manager for you flavor of Linux.

- FreeGLUT3

    - Download and install from: http://freeglut.sourceforge.net/index.php#download

    - Or use `apt`:

	    	sudo apt-get install freeglut3-dev
            
- libfreenect

    - I still haven't tried the project out on Linux yet, but it seems like we'd need to have a alternative driver for the Kinect.
    OpenNi2's website says that it doesn't require a driver on Linux though. So I'll just figure this one out later. http://structure.io/openni

### Android

- NOTE: This should theoretically work since the OpenNI2 project supports it, but I've not tried it out.

- Download and install the Android NDK version **r8d**. Newer versions will **NOT** work.

- For Mac OS X: http://dl.google.com/android/ndk/android-ndk-r8d-darwin-x86.tar.bz2
- For Windows:  http://dl.google.com/android/ndk/android-ndk-r8d-windows.zip
- For Linux:    http://dl.google.com/android/ndk/android-ndk-r8d-linux-x86.tar.bz2

    Building Android packages requires the NDK_ROOT environment variable to be defined, and its value must be pointing to the NDK installation dir: `NDK_ROOT=/path/to/android-ndk-r8d`


## Building the Project

The project still needs this part worked out a bit better, but I've been using Intellij for now (Gradle will be the goto later.)

1. Load up the project in Intellij.
2. Make sure there is an Artifact in the Project Settings of the KinectController. If there isn't, then create a JAR artifact from the KinectController'should
modules. Then make sure the project's jar is being built on make (there's a checkbox for it) and change the output directory to KinectController/lib.
3. Get Gradle to refresh its dependencies (one way to do this is to go to View->Tool Windows->Gradle, then press the refresh icon in the Gradle window.)
4. Get Intellij to make the project. There's a button for it at the top right of Intellij -- the icon has a downward arrow along with some 0's and 1's.
5. Go into the KinectController/lib directory and execute the KinectController.jar with the below command.
$java -cp KinectController.jar com.lcsc.hackathon.kinectcontroller.Main -d -f ../config/config.gdef