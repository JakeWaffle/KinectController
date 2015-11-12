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

package com.lcsc.hackathon.kinectcontroller.esper;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.EventBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Robot;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Point;

//TODO This class hopefully has been replaced by EventListener, but we still need to determine how to get kinectcontroller
//information to the EmulationController's scheduled reactions.
//Maybe it's possible to update the Reaction object via the EventListener and it will in turn update the
//EmulationController? Some shared information is probably needed.

public class MouseMove implements UpdateListener {
    private static final Logger _logger      = LoggerFactory.getLogger(MouseMove.class);
    private              Robot  _rob;

    public MouseMove() {
        try {
            _rob = new Robot();
        } catch (AWTException e) {
            _logger.error("", e);
        }
    }
    
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        for (EventBean event : newEvents) {
            String direction = (String)event.get("direction");
            //_logger.info("DIrection: "+direction);
            if (direction.equals("LEFT")) {
                PointerInfo a = MouseInfo.getPointerInfo();
                Point b = a.getLocation();
                int x = (int) b.getX();
                int y = (int) b.getY();

                _logger.info(String.format("X: %d Y: %d", x, y));

                _rob.mouseMove(x-5, y);
            }
        }
    }
}