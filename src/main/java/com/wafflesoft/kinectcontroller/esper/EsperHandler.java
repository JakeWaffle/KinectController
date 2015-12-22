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

package com.wafflesoft.kinectcontroller.esper;

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

    /**
     * This will just configure Esper with the posturerules event beans that this program deals supports.
     */
    private void config() {
        Configuration config = new Configuration();

        config.getEngineDefaults().getExecution().setPrioritized(true);
        config.getEngineDefaults().getEventMeta().setDefaultEventRepresentation(Configuration.EventRepresentation.MAP);

        config.addEventType("Angle", "com.wafflesoft.kinectcontroller.posturerules.Angle");
        config.addEventType("Distance", "com.wafflesoft.kinectcontroller.posturerules.Distance");
        config.addEventType("DistanceX", "com.wafflesoft.kinectcontroller.posturerules.DistanceX");
        config.addEventType("DistanceY", "com.wafflesoft.kinectcontroller.posturerules.DistanceY");
        config.addEventType("DistanceZ", "com.wafflesoft.kinectcontroller.posturerules.DistanceZ");
        config.addEventType("PositionZ", "com.wafflesoft.kinectcontroller.posturerules.PositionZ");
        config.addEventType("PositionX", "com.wafflesoft.kinectcontroller.posturerules.PositionX");
        config.addEventType("PositionY", "com.wafflesoft.kinectcontroller.posturerules.PositionY");
        config.addEventType("DistanceFromPoint", "com.wafflesoft.kinectcontroller.posturerules.DistanceFromPoint");

        _engine = EPServiceProviderManager.getDefaultProvider(config);
        _engine.initialize();
    }

    /**
     * This will assign an Esper pattern to some patternId.
     * @param patternId The id for the given pattern.
     * @param pattern   Some Esper pattern that this program is interested in matching.
     */
    public void setPattern(String patternId, String pattern) {
        EPStatement statement = _engine.getEPAdministrator().createEPL(pattern);

        _patterns.put(patternId, statement);
    }

    /**
     * This will add an UpdateListener class to an Esper pattern. Multiple UpdateListeners can be assigned to a single
     * pattern. An UpdateListener is only added to existing Esper patterns!
     * @param patternId The id of the pattern that we're adding this UpdateListener to (this pattern should already exist!)
     * @param listener  The UpdateListener that will be called when the pattern has been matched.
     */
    public void addListener(String patternId, UpdateListener listener) {
        if (_patterns.containsKey(patternId)) {
            _patterns.get(patternId).addListener(listener);
        }
    }

    /**
     * This is used to send event beans to Esper for processing. These events are going to be used to match the
     * patterns that have been registered with Esper.
     * @param event This can be any of the event beans registered in the config().
     */
    public void sendEvent(Object event) {
        _engine.getEPRuntime().sendEvent(event);
    }
}