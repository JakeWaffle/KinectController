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

package com.lcsc.hackathon.kinectcontroller;

import java.util.Map;
import java.util.HashMap;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EsperHandler {
    private static final Logger                     _logger     = LoggerFactory.getLogger(EsperHandler.class);
    private              EPServiceProvider          _engine;
    private              Map<String, EPStatement>   _patterns;
    
    public EsperHandler() {
        _patterns = new HashMap<String, EPStatement>();
        config();
    }
    
    private void config() {
        Configuration config = new Configuration();
        config.getEngineDefaults().getExecution().setPrioritized(true);
        config.getEngineDefaults().getEventMeta().setDefaultEventRepresentation(Configuration.EventRepresentation.MAP);
        config.addEventType("Angle", "com.lcsc.hackathon.kinectcontroller.rules.Angle");
        config.addEventType("Distance", "com.lcsc.hackathon.kinectcontroller.rules.Distance");
        config.addEventType("DistanceX", "com.lcsc.hackathon.kinectcontroller.rules.DistanceX");
        config.addEventType("DistanceY", "com.lcsc.hackathon.kinectcontroller.rules.DistanceY");
        config.addEventType("DistanceZ", "com.lcsc.hackathon.kinectcontroller.rules.DistanceZ");
        config.addEventType("AbsoluteDistZ", "com.lcsc.hackathon.kinectcontroller.rules.AbsoluteDistZ");
        config.addEventType("AbsoluteDistX", "com.lcsc.hackathon.kinectcontroller.rules.AbsoluteDistX");
        config.addEventType("AbsoluteDistY", "com.lcsc.hackathon.kinectcontroller.rules.AbsoluteDistY");
        config.addEventType("AbsoluteDistance", "com.lcsc.hackathon.kinectcontroller.rules.AbsoluteDistance");

        _engine = EPServiceProviderManager.getDefaultProvider(config);
        _engine.initialize();
    }
    
    public void setPattern(String patternId, String pattern) {
        EPStatement statement = _engine.getEPAdministrator().createEPL(pattern);

        _patterns.put(patternId, statement);
    }
    
    public void addListener(String patternId, UpdateListener listener) {
        _patterns.get(patternId).addListener(listener);
    }
    
    public void sendEvent(Object event) {
        _engine.getEPRuntime().sendEvent(event);
    }
}