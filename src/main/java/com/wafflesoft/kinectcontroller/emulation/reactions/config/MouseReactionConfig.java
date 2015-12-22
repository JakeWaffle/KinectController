package com.wafflesoft.kinectcontroller.emulation.reactions.config;

import com.wafflesoft.kinectcontroller.controller.Gesture;

/**
 * Created by jake on 12/21/2015.
 */
public class MouseReactionConfig extends ReactionConfig<String,Object> {
    /**
     * This saves a reference to the gesture that this reaction belongs to.
     *
     * @param gesture       The gesture that the mouse reaction belongs to. This gesture holds the information about
     *                      the user's arm so that the mouse can be moved accordingly.
     *
     */
    public MouseReactionConfig(Gesture gesture) {
        _config.put("gesture", gesture);
    }
}