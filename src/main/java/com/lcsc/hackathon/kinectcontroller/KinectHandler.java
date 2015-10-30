package com.lcsc.hackathon.kinectcontroller;

import com.primesense.nite.*;
import org.openni.Device;
import org.openni.DeviceInfo;
import org.openni.OpenNI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Created by Student on 10/29/2015.
 * This class will be tasked with keeping track of the libraries that are needed to talk to the Kinect. So this class will
 * be handing off the positions of joints to Esper and Esper will then use that data to detect gestures.
 */
public class KinectHandler implements UserTracker.NewFrameListener{
    private static final Logger             _logger = LoggerFactory.getLogger(KinectHandler.class);
    public         final KinectDebugWindow  kinectWindow;
    private              UserTracker        _tracker;

    public KinectHandler(boolean debug) {
        System.out.println("Initializing the things.");
        OpenNI.initialize();
        NiTE.initialize();

        List<DeviceInfo> devicesInfo = OpenNI.enumerateDevices();
        if (devicesInfo.size() == 0) {
            _logger.error("No Kinect device is connected!");
            System.exit(1);
        }

        Device device = Device.open(devicesInfo.get(0).getUri());
        _tracker = UserTracker.create();

        if (debug) {
            kinectWindow = new KinectDebugWindow(UserTracker.create());
        }
            else {
            kinectWindow = null;
        }

        _tracker.addNewFrameListener(this);
    }

    /**
     * This delegate method will be called by the _tracker periodically.
     * @param tracker The tracker that called the method probably. Examples I've seen don't even utilize it because
     *                there is already a copy of it saved in the class' private scope.
     */
    public void onNewFrame(UserTracker tracker) {
        //TODO Pull the posturerules Event Beans from the current ControllerState, fill those beans with data and give them to Esper.
    }
}
