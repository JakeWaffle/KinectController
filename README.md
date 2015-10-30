# Kinect Controller

## Description

This project's goal is to make the Kinect compatible with any possible video game on a computer. The idea behind it is that the user supplies a
configuration file that defines the gestures that the Kinect Controller will observe. These gestures will be tied to one or many reactions that
have the potential to do a lot of different things from emulating keyboard events to executing programs or macros.

After this project gets some headway and we reach a near 1.0 release, then there should be a good amount of configuration
files already created for various games and applications of the project.

Refer to the wiki for more information on the project and how the configuration files work.

## Building the Project

The project still needs this part worked out a bit better, but I've been using Intellij for now (Gradle will be the goto later.)

1. Load up the project in Intellij.
2. Make sure there is an Artifact in the Project Settings of the KinectController. If there isn't, then create a JAR artifact from the KinectController'should
modules. Then make sure the project's jar is being built on make (there's a checkbox for it) and change the output directory to KinectController/lib.
3. Get Gradle to refresh its dependencies (one way to do this is to go to View->Tool Windows->Gradle, then press the refresh icon in the Gradle window.)
4. Get Intellij to make the project. There's a button for it at the top right of Intellij -- the icon has a downward arrow along with some 0's and 1's.
5. Go into the KinectController/lib directory and execute the KinectController.jar with the below command.
$java -cp KinectController.jar com.lcsc.hackathon.kinectcontroller.Main -d -f ../config/config.gdef