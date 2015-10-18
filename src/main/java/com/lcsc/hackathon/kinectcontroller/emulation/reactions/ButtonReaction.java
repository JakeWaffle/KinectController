package com.lcsc.hackathon.kinectcontroller.emulation.reactions;

import com.lcsc.hackathon.kinectcontroller.emulation.reactions.config.ReactionConfig;

import java.awt.Robot;
import java.awt.AWTException;

/**
 * Created by jake on 10/15/2015.
 */
public class ButtonReaction implements Reaction {
    private ReactionConfig<String, Object> _config;

    public ButtonReaction(ReactionConfig config) {
        _config = config;
    }

    public void trigger() {
        try {
            Robot rob = new Robot();
            switch ((String)_config.get("DeviceType")) {
                case "keyboard":
                    rob.keyPress((int)_config.get("ButtonId"));
                    break;
                case "mouse":
                    rob.mousePress((int)_config.get("ButtonId"));
                    break;
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
