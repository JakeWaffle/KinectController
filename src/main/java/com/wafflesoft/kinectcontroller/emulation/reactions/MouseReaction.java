package com.wafflesoft.kinectcontroller.emulation.reactions;

import com.wafflesoft.kinectcontroller.emulation.reactions.config.MouseReactionConfig;
import com.wafflesoft.kinectcontroller.emulation.reactions.config.ReactionConfig;
import com.wafflesoft.kinectcontroller.posturerules.Angle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.lang.System;

/**
 * Created by jake on 12/21/2015.
 * This reaction is activated when one of the user's arms are in front of the user and extended. The reaction will
 * then move the mouse with respect to the direction that the arm is pointing.
 */
public class MouseReaction implements PersistentReaction {
    private static final Logger _logger = LoggerFactory.getLogger(MouseReaction.class);
    private static Robot _rob;

    static {
        try {
            _rob = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private ReactionConfig<String, Object> _config;
    private String _id;
    private long lastTime = -1;

    /**
     * @param config    This contains the angle rules necessary for determining the direction/speed to move the mouse.
     * @param id        The id of this PersistentReaction.
     */
    public MouseReaction(String id, MouseReactionConfig config) {
        _id     = id;
        _config = config;
    }

    public String getId() {
        return _id;
    }

    public ReactionConfig getConfig() {
        return _config;
    }

    public boolean trigger() {
        boolean done = false;
        if (lastTime != -1) {
            //Delta time of time past since last mouse movement, in milliseconds.
            double deltaTime = (System.nanoTime() - lastTime)/1_000_000d;

            //These are the positions on the monitor for the mouse I think.
            int relX = 1;
            int relY = 1;

            Angle armAngle = (Angle)_config.get("armAngle");
            Angle armX = (Angle)_config.get("armX");
            Angle armY = (Angle)_config.get("armY");

            //First we just get the straight up angles for the arm angle rules for now.
            //They are not normalized yet!
            relX = (int)armX.getAngle();
            relY = (int)armY.getAngle();

            //_logger.debug(String.format("ArmY: %d", relY));

            double normX;
            double normY;

            int armXMin = (int)_config.get("armXMin");
            int armXMax = (int)_config.get("armXMax");
            int armYMin = (int)_config.get("armYMin");
            int armYMax = (int)_config.get("armYMax");

            //Normalizes the angles to be
            normX = 2*(relX - armXMin) / (armXMax - armXMin)-1;
            normY = 2*(relY - armYMin) / (armYMax - armYMin)-1;

            //_logger.debug(String.format("Normalized RelX, RelY: %f, %f", normX, normY));

            //Checks to see if the arm's angles aren't within the valid space still.
            if (normX < -1 || normX > 1 ||
                    normY < -1 || normY > 1 ||
                    ((int)armAngle.getAngle()) < (int)_config.get("armAngleMin")) {
                //_logger.debug("Ended persistentReaction");
                done = true;
            }
            //Move the mouse if the arm angles are valid.
            else {
                //TODO This currently moves the mouse 2x as fast for diagonal movements.
                //The mouseMaxVelocity needs to modify the magnitude of the (x,y) vector as a whole.
                //So that the (x,y) vector's highest magnitude is mouseMaxVelocity -- currently it's 2*mouseMaxVelocity.

                //TODO relX and relY are always 9 or 10, (the same as the delta time basically.)
                //Times the relative offset by the elapsed time, in milliseconds.
                relX = (int) (((int)_config.get("mouseXMaxVelocity"))*deltaTime*normX);
                relY = (int) (((int)_config.get("mouseYMaxVelocity"))*deltaTime*normY);

                _logger.debug(String.format("DeltaTime: %f", deltaTime));
                _logger.debug(String.format("RelX, RelY: %d, %d", relX, relY));

                mouseRelativeMove(relX, relY);
            }
        }
        lastTime = System.nanoTime();

        return done;
    }

    /**
     * This will move the mouse x and y relative to the cursor's current position.
     * @param x The x-axis position for the relative position.
     * @param y The y-axis position for the relative position.
     */
    private void mouseRelativeMove(int x, int y) {
        Point curPos = getMousePosition();
        _rob.mouseMove(curPos.x + x, curPos.y + y);
    }

    /**
     * This gets the current position of the mouse cursor.
     * @return A java.awt.Point object is returned and it has the mouse position contained within.
     */
    private Point getMousePosition() {
        return MouseInfo.getPointerInfo().getLocation();
    }
}
