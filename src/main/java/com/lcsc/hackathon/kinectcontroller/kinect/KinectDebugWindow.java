/*
This program is called "Kinect Controller". It is meant to detect gestures with the Kinect
and then simulate keyboard and/or mouse input. The configuration files used by this program are
not intended to be under the following license.

By using Esper without their commercial license we are also required to release our software under
a GPL license.

Copyright (C) 2015  Jacob Waffle, Ryan Spiekerman and Josh Rogers

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

package com.lcsc.hackathon.kinectcontroller.kinect;

import com.primesense.nite.*;
import org.openni.VideoFrameRef;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by jake on 7/20/15.
 * This class' primary purpose is to just display the skeletons of the users visible to the Kinect.
 */
public class KinectDebugWindow extends Component{
    private JFrame              _frame;
    private boolean             _done;

    private float               _histogram[];
    private int[]               _depthPixels;
    private UserTracker         _tracker;
    private BufferedImage       _bufferedImage;
    private int[]               _colors;

    public KinectDebugWindow(UserTracker tracker) {
        _tracker    = tracker;
        _frame      = new JFrame("NiTE User Tracker Viewer");

        // register to key events
        _frame.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent arg) {
            }

            public void keyReleased(KeyEvent arg) {
            }

            public void keyPressed(KeyEvent arg) {
                if (arg.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    done();
                }
            }
        });

        // register to closing event
        _frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                done();
            }
        });

        this.setSize(800, 600);
        _frame.add("Center", this);
        _frame.setSize(this.getWidth(), this.getHeight());
        _frame.setVisible(true);

        _colors = new int[] { 0xFFFF0000, 0xFF00FF00, 0xFF0000FF, 0xFFFFFF00, 0xFFFF00FF, 0xFF00FFFF };
    }

    public synchronized void done() {
        _frame.dispose();
    }

    @Override
    public void paint(Graphics g) {
        UserTrackerFrameRef lastFrame = _tracker.readFrame();

        VideoFrameRef depthFrame    = lastFrame.getDepthFrame();
        int framePosX               = 0;
        int framePosY               = 0;

        if (depthFrame != null) {
            ByteBuffer frameData = depthFrame.getData().order(ByteOrder.LITTLE_ENDIAN);
            ByteBuffer usersFrame = lastFrame.getUserMap().getPixels().order(ByteOrder.LITTLE_ENDIAN);

            // make sure we have enough room
            if (_depthPixels == null || _depthPixels.length < depthFrame.getWidth() * depthFrame.getHeight()) {
                _depthPixels = new int[depthFrame.getWidth() * depthFrame.getHeight()];
            }

            calcHist(frameData);
            frameData.rewind();

            int pos = 0;
            while(frameData.remaining() > 0) {
                short depth = frameData.getShort();
                short userId = usersFrame.getShort();
                short pixel = (short) _histogram[depth];
                int color = 0xFFFFFFFF;
                if (userId > 0) {
                    color = _colors[userId % _colors.length];
                }

                _depthPixels[pos] = color & (0xFF000000 | (pixel << 16) | (pixel << 8) | pixel);
                pos++;
            }
            int width = depthFrame.getWidth();
            int height = depthFrame.getHeight();

            depthFrame.release();

            // make sure we have enough room
            if (_bufferedImage == null || _bufferedImage.getWidth() != width || _bufferedImage.getHeight() != height) {
                _bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            }

            _bufferedImage.setRGB(0, 0, width, height, _depthPixels, 0, width);

            framePosX = (getWidth() - width) / 2;
            framePosY = (getHeight() - height) / 2;

            g.drawImage(_bufferedImage, framePosX, framePosY, null);
        }

        for (UserData user : lastFrame.getUsers()) {
            if (user.getSkeleton().getState() == SkeletonState.TRACKED) {
                drawLimb(g, framePosX, framePosY, user, JointType.HEAD, JointType.NECK);

                drawLimb(g, framePosX, framePosY, user, JointType.LEFT_SHOULDER, JointType.LEFT_ELBOW);
                drawLimb(g, framePosX, framePosY, user, JointType.LEFT_ELBOW, JointType.LEFT_HAND);

                drawLimb(g, framePosX, framePosY, user, JointType.RIGHT_SHOULDER, JointType.RIGHT_ELBOW);
                drawLimb(g, framePosX, framePosY, user, JointType.RIGHT_ELBOW, JointType.RIGHT_HAND);

                drawLimb(g, framePosX, framePosY, user, JointType.LEFT_SHOULDER, JointType.RIGHT_SHOULDER);

                drawLimb(g, framePosX, framePosY, user, JointType.LEFT_SHOULDER, JointType.TORSO);
                drawLimb(g, framePosX, framePosY, user, JointType.RIGHT_SHOULDER, JointType.TORSO);

                drawLimb(g, framePosX, framePosY, user, JointType.LEFT_HIP, JointType.TORSO);
                drawLimb(g, framePosX, framePosY, user, JointType.RIGHT_HIP, JointType.TORSO);
                drawLimb(g, framePosX, framePosY, user, JointType.LEFT_HIP, JointType.RIGHT_HIP);

                drawLimb(g, framePosX, framePosY, user, JointType.LEFT_HIP, JointType.LEFT_KNEE);
                drawLimb(g, framePosX, framePosY, user, JointType.LEFT_KNEE, JointType.LEFT_FOOT);

                drawLimb(g, framePosX, framePosY, user, JointType.RIGHT_HIP, JointType.RIGHT_KNEE);
                drawLimb(g, framePosX, framePosY, user, JointType.RIGHT_KNEE, JointType.RIGHT_FOOT);
            }
        }
    }

    private void drawLimb(Graphics g, int x, int y, UserData user, JointType from, JointType to) {
        com.primesense.nite.SkeletonJoint fromJoint = user.getSkeleton().getJoint(from);
        com.primesense.nite.SkeletonJoint toJoint = user.getSkeleton().getJoint(to);

        if (fromJoint.getPositionConfidence() == 0.0 || toJoint.getPositionConfidence() == 0.0) {
            return;
        }

        com.primesense.nite.Point2D<Float> fromPos = _tracker.convertJointCoordinatesToDepth(fromJoint.getPosition());
        com.primesense.nite.Point2D<Float> toPos = _tracker.convertJointCoordinatesToDepth(toJoint.getPosition());

        // draw it in another color than the use color
        g.setColor(new Color(_colors[(user.getId() + 1) % _colors.length]));
        g.drawLine(x + fromPos.getX().intValue(), y + fromPos.getY().intValue(), x + toPos.getX().intValue(), y + toPos.getY().intValue());
    }

    private void calcHist(ByteBuffer depthBuffer) {
        // make sure we have enough room
        if (_histogram == null) {
            _histogram = new float[10000];
        }

        // reset
        for (int i = 0; i < _histogram.length; ++i)
            _histogram[i] = 0;

        int points = 0;
        while (depthBuffer.remaining() > 0) {
            int depth = depthBuffer.getShort() & 0xFFFF;
            if (depth != 0) {
                _histogram[depth]++;
                points++;
            }
        }

        for (int i = 1; i < _histogram.length; i++) {
            _histogram[i] += _histogram[i - 1];
        }

        if (points > 0) {
            for (int i = 1; i < _histogram.length; i++) {
                _histogram[i] = (int) (256 * (1.0f - (_histogram[i] / (float) points)));
            }
        }
    }
}
