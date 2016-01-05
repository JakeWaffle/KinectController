package com.wafflesoft.kinectcontroller.config.installers;

import com.wafflesoft.kinectcontroller.Conversions;
import com.wafflesoft.kinectcontroller.controller.Gesture;
import com.wafflesoft.kinectcontroller.posturerules.PositionX;
import com.wafflesoft.kinectcontroller.posturerules.PositionY;
import com.wafflesoft.kinectcontroller.posturerules.PositionZ;

/**
 * Created by jake on 1/3/2016.
 * * This supplies functions necessary for installing the different Position rules into a Gesture object.
 *
 * These rules supply an additional requirement to be "matched". If some joint has its position within a threshold,
 * then this requirement would be satisfied. Then the gesture is able to trigger its reactions once it is
 * considered matched.
 *
 * There are 3 different position rules available:
 * - PositionX - (Use installX()) This rule will account for the position of a joint over the x-axis of
 *              the Kinect's viewable coordinate plane (e.g. position on the x-axis.)
 * - PositionY - (Use installY()) This rule will account for the position of a joint over the y-axis of
 *              the Kinect's viewable coordinate plane (e.g. position on the y-axis.)
 * - PositionZ - (Use installZ()) This rule will account for the position of a joint over the z-axis of
 *              the Kinect's viewable coordinate plane (e.g. position on the z-axis.)
 */
public class PositionRuleInstaller {

    /**
     * This rule will add a requirement to the gesture's matched case that will account for the position of a joint
     * on only the x-axis of the Kinect's viewable coordinate plane (e.g. position on the x-axis.)
     *
     * @param gesture     The gesture that the PositionX rule object and respective Esper patterns will be installed into.
     * @param joint       The first joint of interest for position calculation
     * @param minPosition For this rule to be matched, the updated DistanceX event bean must have a
     *                    distance greater than or equal to this number.
     * @param maxPosition For this rule to be matched, the updated DistanceX event bean must have a
     *                    distance less than or equal to this number.
     * @return A reference to the PositionX event bean object that was installed into the gesture. This event bean will be
     * updated with data from the Kinect's skeleton tracking. The reference is great for when a Reaction needs to reference
     * the position of a joint on the user's skeleton (e.g. MouseReaction.)
     */
    public static PositionX installX(Gesture gesture, String joint, Double minPosition, Double maxPosition) {
        System.out.printf("MAX_POSITION: %f\n", maxPosition);
        System.out.printf("MIN_POSITION: %f\n", minPosition);
        System.out.printf("JOINT: %s\n", joint);

        int jointId = Conversions.getJointId(joint);

        //ruleType.className is going to be either PositionX, PositionY or PositionZ.

        String patternChunk1 = String.format("PositionX(joint=%d, pos > %f, pos < %f)", jointId,
                minPosition,
                maxPosition);

        String patternChunk2 = String.format("PositionX(joint=%d, pos < %f)", jointId,
                minPosition);

        String patternChunk3 = String.format("PositionX(joint=%d, pos > %f)", jointId,
                maxPosition);

        PositionX rule = new PositionX(jointId, 0);
        rule = (PositionX)gesture.addRule(rule, patternChunk1, String.format("(%s or %s)", patternChunk3, patternChunk2));

        return rule;
    }

    /**
     * This rule will add a requirement to the gesture's matched case that will account for the position of a joint
     * on only the y-axis of the Kinect's viewable coordinate plane (e.g. position on the y-axis.)
     *
     * @param gesture     The gesture that the PositionY rule object and respective Esper patterns will be installed into.
     * @param joint       The first joint of interest for position calculation
     * @param minPosition For this rule to be matched, the updated DistanceX event bean must have a
     *                    distance greater than or equal to this number.
     * @param maxPosition For this rule to be matched, the updated DistanceX event bean must have a
     *                    distance less than or equal to this number.
     * @return A reference to the PositionY event bean object that was installed into the gesture. This event bean will be
     * updated with data from the Kinect's skeleton tracking. The reference is great for when a Reaction needs to reference
     * the position of a joint on the user's skeleton (e.g. MouseReaction.)
     */
    public static PositionY installY(Gesture gesture, String joint, Double minPosition, Double maxPosition) {
        System.out.printf("MAX_POSITION: %f\n", maxPosition);
        System.out.printf("MIN_POSITION: %f\n", minPosition);
        System.out.printf("JOINT: %s\n", joint);

        int jointId = Conversions.getJointId(joint);

        //ruleType.className is going to be either PositionX, PositionY or PositionZ.

        String patternChunk1 = String.format("PositionY(joint=%d, pos > %f, pos < %f)", jointId,
                minPosition,
                maxPosition);

        String patternChunk2 = String.format("PositionY(joint=%d, pos < %f)", jointId,
                minPosition);

        String patternChunk3 = String.format("PositionY(joint=%d, pos > %f)", jointId,
                maxPosition);

        PositionY rule = new PositionY(jointId, 0);
        rule = (PositionY)gesture.addRule(rule, patternChunk1, String.format("(%s or %s)", patternChunk3, patternChunk2));

        return rule;
    }

    /**
     * This rule will add a requirement to the gesture's matched case that will account for the position of a joint
     * on only the z-axis of the Kinect's viewable coordinate plane (e.g. position on the z-axis.)
     *
     * @param gesture     The gesture thatrthe PositionZ rule object and respective Esper patterns will be installed into.
     * @param joint       The first joint of interest for position calculation
     * @param minPosition For this rule to be matched, the updated DistanceX event bean must have a
     *                    distance greater than or equal to this number.
     * @param maxPosition For this rule to be matched, the updated DistanceX event bean must have a
     *                    distance less than or equal to this number.
     * @return A reference to the PositionZ event bean object that was installed into the gesture. This event bean will be
     * updated with data from the Kinect's skeleton tracking. The reference is great for when a Reaction needs to reference
     * the position of a joint on the user's skeleton (e.g. MouseReaction.)
     */
    public static PositionZ installZ(Gesture gesture, String joint, Double minPosition, Double maxPosition) {
        System.out.printf("MAX_POSITION: %f\n", maxPosition);
        System.out.printf("MIN_POSITION: %f\n", minPosition);
        System.out.printf("JOINT: %s\n", joint);

        int jointId = Conversions.getJointId(joint);

        String patternChunk1 = String.format("PositionZ(joint=%d, pos > %f, pos < %f)", jointId,
                minPosition,
                maxPosition);

        String patternChunk2 = String.format("PositionZ(joint=%d, pos < %f)", jointId,
                minPosition);

        String patternChunk3 = String.format("PositionZ(joint=%d, pos > %f)", jointId,
                maxPosition);

        PositionZ rule = new PositionZ(jointId, 0);
        rule = (PositionZ)gesture.addRule(rule, patternChunk1, String.format("(%s or %s)", patternChunk3, patternChunk2));

        return rule;
    }
}
