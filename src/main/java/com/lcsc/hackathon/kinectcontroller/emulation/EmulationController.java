package com.lcsc.hackathon.kinectcontroller.emulation;

/**
 * Created by jake on 10/5/2015.
 * This class will receive constant updates, will exist at the ControllerState level and be used by the
 * Gesture classes within the current ControllerState being used.
 *
 * Esper's UpdateListeners will essentially tell this class that some reactions need to be emulated on the
 * keyboard, mouse or whatever else. Reactions may take several steps to emulate correctly though, so it may take
 * some reactions a few program cycles to complete. Each of the steps will be called in different program cycles
 * essentially.
 *
 * A queue also gives us the chance to allow a lazy execution just in case the user's computer may not be as fast
 * as we might hope. A priority queue could be used to improve the delay also.
 */
public class EmulationController {
    public EmulationController() {

    }

    /**
     * This class will execute some of the tasks in the execution queue.
     */
    public void update() {

    }
}
