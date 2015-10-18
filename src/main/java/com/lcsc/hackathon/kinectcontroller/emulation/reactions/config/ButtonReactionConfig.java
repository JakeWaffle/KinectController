package com.lcsc.hackathon.kinectcontroller.emulation.reactions.config;


/**
 * Created by jake on 10/18/2015.
 * This is the configuration object for the ButtonReaction. Each of these Config objects are designed
 * to hold the configuration of a Reaction in a standardized way.
 */
public class ButtonReactionConfig extends ReactionConfig<String,Object> {
    /**
     * This loads buttons into the config map according to the
     * specifications of the ButtonReaction's needs.
     *
     * @param btnId         ID of the button that's being utilized.
     * @param deviceType    The device that the key exists on (e.g. 'keyboard' or 'mouse'.)
     */
    public void loadKey(int btnId, String deviceType) {
        //TODO Add support for more a sequence of keys to be executed.
        _config.put("DeviceType", deviceType);
        _config.put("ButtonId", btnId);
    }
}
