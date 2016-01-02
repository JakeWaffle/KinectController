package com.wafflesoft.kinectcontroller.config.factories;

import com.wafflesoft.kinectcontroller.controller.ControllerState;
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
     * This creates a Gesture (or any number of gestures) with the loaded config map and then installs
     * it into the given ControllerState.
     * @param state This is the _state that the premade gesture will be installed into.
     */
    void installGesture(ControllerState state);
}
