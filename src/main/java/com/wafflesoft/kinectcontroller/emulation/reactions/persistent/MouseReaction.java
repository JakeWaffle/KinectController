package com.wafflesoft.kinectcontroller.emulation.reactions.persistent;

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

        //Delta time of time past since last mouse movement, in milliseconds.
        double deltaTime = (System.nanoTime() - lastTime)/1_000_000d;

        //We will be ignoring any delta times that are above 1 second.
        //This prevents the mouse from moving around really fast spontaneously.
        if (deltaTime < 1_000) {
            //Grabs some Angle Rules
            Angle armAngle = (Angle)_config.get("armAngle");
            Angle armX = (Angle)_config.get("armX");
            Angle armY = (Angle)_config.get("armY");

            //First we just get the straight up angles for the arm angle rules for now.
            //They are not normalized yet!
            int armXAngle = (int)armX.getAngle();
            int armYAngle = (int)armY.getAngle();

            //_logger.debug(String.format("ArmY: %d", relY));

            double normX;
            double normY;

            //Grabs angles supplied by the config object.
            int armXMin = (int)_config.get("armXMin");
            int armXMax = (int)_config.get("armXMax");
            int armYMin = (int)_config.get("armYMin");
            int armYMax = (int)_config.get("armYMax");

            int armAngleMin = (int)_config.get("armAngleMin");

            //These formulas are wrong. They don't keep normX and Y between -1 and 1.

            //The below formula, when taking in (armXAngle=0), will output -3.
            //-3 is not within -1 and 1 and makes no sense. So we're ignoring this case.
            if (armXAngle != 0) {
                //Normalizes the angles to be between -1 and 1.
                normX = 2 * ((double) (armXAngle - armXMin)) / ((double) (armXMax - armXMin)) - 1;
            }
            else {
                normX = 0;
            }

            //The below formula, when taking in (armYAngle=0), will output -3.
            //-3 is not within -1 and 1 and makes no sense. So we're ignoring this case.
            if (armYAngle != 0) {
                normY = 2*((double)(armYAngle - armYMin)) / ((double)(armYMax - armYMin))-1;
            }
            else {
                normY = 0;
            }

            //Gets distance between 0 and 1 that represents the user's hand's distance from the middle of the defined
            //gesture space.
            double distance = Math.sqrt(Math.pow(normX, 2) + Math.pow(normY, 2));

            _logger.debug(String.format("armXAngle, armYAngle: %d, %d", armXAngle, armYAngle));
            _logger.debug(String.format("normX, normY: %f, %f", normX, normY));
            _logger.debug(String.format("normXY magnitude: %f", distance));

            //Checks to see if the user isn't doing the gesture.
            if (distance > 1 || armAngle.getAngle() < armAngleMin) {
                //_logger.debug("Ended persistentReaction");
                done = true;
            }
            //Move the mouse if the arm is outside of the center spot of the user's arm's movement circle.
            else if (distance > 0.2) {

                //Prefetches some information from the config and calculates the angle of the normalized
                //relative x position -- before adding speed and the elapsed time.
                double maxXVel  = (double)((int)_config.get("mouseXMaxVelocity"));
                double maxYVel  = (double)((int)_config.get("mouseYMaxVelocity"));

                //Gives an angle between -90 and 90 degrees (in radians!)
                double angle = Math.atan(normY / normX);

                //Updates the angle if the normX is negative, because the above angle only takes into account
                //positive x-values even though it accepts positive and negative y-values without issue.
                if (normX < 0 && normY < 0) {
                    angle = -1*Math.PI + angle;
                } else if (normX < 0 && normY >= 0) {
                    angle = Math.PI + angle;
                }

                //Calculates the relative positions for the mouse.
                //The elapsed time makes the movement relatively consistent -- there are better ways to do this.
                //The sin and cos are there to make the actual speed of the mouse the same no matter if it's going
                //diagonal or not.
                int relX = (int) (maxXVel * deltaTime * Math.cos(angle));
                int relY = (int) (maxYVel * deltaTime * Math.sin(angle));

                _logger.debug(String.format("Angle: %f", angle));
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
