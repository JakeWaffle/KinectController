package com.wafflesoft.kinectcontroller.controller.gestures;

import com.wafflesoft.kinectcontroller.controller.Gesture;
import com.wafflesoft.kinectcontroller.emulation.reactions.hapaxlegomenon.Reaction;
import com.wafflesoft.kinectcontroller.posturerules.Rule;

import java.util.List;
import java.util.Map;

/**
 * Created by jake on 12/24/2015.
 * The config.jj file -- javacc uses it to generate config parsing Java classes -- will use this interface in order to
 * write a system for working with creating premade gestures for the users.
 */
public interface GestureFactory {
    /**
     * Loads the configuration options for the premade gesture.
     *
     * @param config This map consists of whatever properties the user decided to enter into their config file.
     *               Each factory is responsible for their own data validation.
     * @return True if the configuration options were valid, false otherwise.
     */
    boolean loadConfig(Map<String,String> config);

    /**
     * This essentially just loads a bunch of prewritten rules and reactions into some amount of gestures. Then it spits
     * out the resulting one. These gestures will then be put within a ControllerState object and loaded when the ControllerStateMachine's
     * transitions to that particular ControllerState
     *
     * @return Some premade gestures might need to be several gestures under the hood in order to make the user's configuration
     * easier. So a list of gestures will be returned from this.
     */
    List<Gesture> createGestures();
}
