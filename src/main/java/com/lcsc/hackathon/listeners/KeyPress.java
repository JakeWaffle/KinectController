/*
This program is called "Kinect Controller". It is meant to detect gestures with the Kinect
and then simulate keyboard and/or mouse input. The configuration files used by this program are
not intended to be under the following license.

The Kinect Controller makes use of the J4K library and Esper and we have done
nothing to change their source.

By using J4K we are required to site their research article:
A. Barmpoutis. 'Tensor Body: Real-time Reconstruction of the Human Body and Avatar Synthesis from RGB-D',
IEEE Transactions on Cybernetics, Special issue on Computer Vision for RGB-D Sensors: Kinect and Its
Applications, October 2013, Vol. 43(5), Pages: 1347-1356.

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

package com.lcsc.hackathon.listeners;

import com.lcsc.hackathon.Triggers;
import com.lcsc.hackathon.Trigger;

import java.util.Map;

import com.lcsc.hackathon.Conversions;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.EventBean;

import org.apache.log4j.Logger;

import java.awt.Robot;
import java.awt.AWTException;

import java.lang.Thread;
import java.lang.InterruptedException;

public class KeyPress implements UpdateListener {
    static Logger log = Logger.getRootLogger();
    private Robot rob;
    
    public KeyPress() {
        try {
            this.rob = new Robot();
        } catch (AWTException e) {
            log.error("", e);
        }
    }
    
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        for (EventBean event : newEvents) {
            String triggerId = (String)event.get("triggerId");
            //log.info(String.format("TriggerId: %s", triggerId));
            Trigger trigger = Triggers.getTrigger(triggerId);
            
            for (Map<String, String> attributes : trigger.getDefinition()) {
                String type = attributes.get("type");
                if (type.equals("KeyDownUp")) {
                    log.info(String.format("\n\nKeyPressRelease: %s %d\n\n", attributes.get("key"), Conversions.getKeyId(attributes.get("key"))));
                    this.rob.keyPress(Conversions.getKeyId(attributes.get("key")));
                    try {
                        Thread.sleep(100);
                    } catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    this.rob.keyRelease(Conversions.getKeyId(attributes.get("key")));
                }
                else if (type.equals("KeyDown")) {
                    log.info(String.format("\n\nKeyPress: %s %d\n\n", attributes.get("key"), Conversions.getKeyId(attributes.get("key"))));
                    this.rob.keyPress(Conversions.getKeyId(attributes.get("key")));
                }
                else if (type.equals("KeyUp")) {
                    log.info(String.format("\n\nKeyRelease: %s %d\n\n", attributes.get("key"), Conversions.getKeyId(attributes.get("key"))));
                    this.rob.keyRelease(Conversions.getKeyId(attributes.get("key")));
                }
                else {
                    log.error(String.format("Invalude Tigger Type: %s", type));
                }
            }
        }
    }
}