package com.lcsc.hackathon.kinectcontroller;

import jogamp.graph.font.typecast.ot.table.Device;

import javax.swing.*;

import com.primesense.nite.*;

import java.awt.*;

/**
 * Created by Student on 10/29/2015.
 * This class will be tasked with keeping track of the libraries that are needed to talk to the Kinect. So this class will
 * be handing off the positions of joints to Esper and Esper will then use that data to detect gestures.
 */
public class KinectHandler implements UserTracker.NewFrameListener{
    private KinectDebugWindow   _kinectWindow;
    private UserTracker         _tracker;

    public KinectHandler(boolean debug) {
        OpenNI.initialize();
        NiTE.initialize();

        List<DeviceInfo> devicesInfo = OpenNI.enumerateDevices();
        if (devicesInfo.size() == 0) {
            JOptionPane.showMessageDialog(null, "No device is connected", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Device device = Device.open(devicesInfo.get(0).getUri());
        _tracker = UserTracker.create();

        if (debug) {
            _kinectWindow = KinectDebugWindow(_tracker);
            _kinectWindow.start();
        }
        else {
            _kinectWindow = null;
        }

        _tracker.addNewFrameListener(this);
    }

    public void onNewFrame(UserTracker tracker) {

    }
}
