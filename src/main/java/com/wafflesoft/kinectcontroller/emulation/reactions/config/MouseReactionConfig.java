package com.wafflesoft.kinectcontroller.emulation.reactions.config;

import com.wafflesoft.kinectcontroller.controller.Gesture;
import com.wafflesoft.kinectcontroller.posturerules.Angle;

/**
 * Created by jake on 12/21/2015.
 */
public class MouseReactionConfig extends ReactionConfig<String,Object> {
    /**
     * This saves a reference to the gesture that this reaction belongs to.
     *
     * @param mouseXMaxVelocity The pixels per millisecond at which the travels at its maximum speed on the x-axis.
     * @param mouseYMaxVelocity The pixels per millisecond at which the travels at its maximum speed on the y-axis.
     * @param armAngle      The angle of the chosen arm. Used to determine if it's straight or now.
     * @param armX          The angle between the chosen arm and the opposite shoulder. 90 is going to count
     *                      as "zero" for the x-axis relative to the user's body. The degrees along this
     *                      x-axis away from "zero" will count towards
     *                      how fast the mouse moves along the x-axis of the monitor.
     * @param armXMin       The minimum angle for the x-axis angle.
     * @param armXMax       The maximum angle for the x-axis angle.
     * @param armY          The angle between the chosen arm and the same side hip. 90 is going to count
     *                      as "zero" for the y-axis relative to the user's body. The degrees along this
     *                      y-axis away from "zero" will count towards
     *                      how fast the mouse moves along the y-axis of the monitor.
     * @param armYMin       The minimum angle for the y-axis angle.
     * @param armYMax       The maximum angle for the y-axis angle.
     */
    public MouseReactionConfig(int mouseXMaxVelocity, int mouseYMaxVelocity, Angle armAngle, int armAngleMin, Angle armX, int armXMin, int armXMax, Angle armY, int armYMin, int armYMax) {
        _config.put("mouseXMaxVelocity", mouseXMaxVelocity);
        _config.put("mouseYMaxVelocity", mouseYMaxVelocity);

        _config.put("armAngle", armAngle);
        _config.put("armAngleMin", armAngleMin);

        _config.put("armX", armX);
        _config.put("armXMin", armXMin);
        _config.put("armXMax", armXMax);

        _config.put("armY", armY);
        _config.put("armYMin", armYMin);
        _config.put("armYMax", armYMax);
    }
}