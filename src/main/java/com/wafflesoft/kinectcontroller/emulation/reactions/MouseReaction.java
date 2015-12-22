package com.wafflesoft.kinectcontroller.emulation.reactions;

import com.wafflesoft.kinectcontroller.emulation.reactions.config.MouseReactionConfig;
import com.wafflesoft.kinectcontroller.emulation.reactions.config.ReactionConfig;

import java.awt.*;

/**
 * Created by jake on 12/21/2015.
 * This reaction is activated when one of the user's arms are in front of the user and extended. The reaction will
 * then move the mouse with respect to the direction that the arm is pointing.
 */
public class MouseReaction implements Reaction {
    private ReactionConfig<String, Object> _config;
    private double lastTime = -1;

    public MouseReaction(MouseReactionConfig config) {
        _config = config;
    }

    public ReactionConfig getConfig() {
        return _config;
    }

    public void trigger() {
        if (lastTime != -1) {
            //Determine dif in time
            //Figure out direction for the mouse.
            //move mouse some dist along that direction.
        }
        //update lastTime
    }

    private class MouseMoveThread implements Runnable {

        private Robot robot;
        private int startX;
        private int startY;
        private int currentX;
        private int currentY;
        private int xAmount;
        private int yAmount;
        private int xAmountPerIteration;
        private int yAmountPerIteration;
        private int numberOfIterations;
        private long timeToSleep;

        public MouseMoveThread( int xAmount, int yAmount,
                                int numberOfIterations, long timeToSleep ) {

            this.xAmount = xAmount;
            this.yAmount = yAmount;
            this.numberOfIterations = numberOfIterations;
            this.timeToSleep = timeToSleep;

            try {

                robot = new Robot();

                Point startLocation = MouseInfo.getPointerInfo().getLocation();
                startX = startLocation.x;
                startY = startLocation.y;

            } catch ( AWTException exc ) {
                exc.printStackTrace();
            }

        }

        @Override
        public void run() {

            currentX = startX;
            currentY = startY;

            xAmountPerIteration = xAmount / numberOfIterations;
            yAmountPerIteration = yAmount / numberOfIterations;

            while ( currentX < startX + xAmount &&
                    currentY < startY + yAmount ) {

                currentX += xAmountPerIteration;
                currentY += yAmountPerIteration;

                robot.mouseMove( currentX, currentY );

                try {
                    Thread.sleep( timeToSleep );
                } catch ( InterruptedException exc ) {
                    exc.printStackTrace();
                }

            }

        }

    }
}
