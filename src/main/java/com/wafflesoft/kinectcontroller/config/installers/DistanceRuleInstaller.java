package com.wafflesoft.kinectcontroller.config.installers;

import com.wafflesoft.kinectcontroller.Conversions;
import com.wafflesoft.kinectcontroller.controller.Gesture;
import com.wafflesoft.kinectcontroller.posturerules.Distance;
import com.wafflesoft.kinectcontroller.posturerules.DistanceX;
import com.wafflesoft.kinectcontroller.posturerules.DistanceY;
import com.wafflesoft.kinectcontroller.posturerules.DistanceZ;

/**
 * Created by jake on 1/2/2016.
 * This supplies functions necessary for installing the different Distance rules into a Gesture object.
 *
 * These rules supply an additional requirement to be "matched". If some joints are within a distance threshold of
 * each other, then this requirement would be satisfied. Then the gesture is able to trigger its reactions once it is
 * considered matched.
 *
 * There are 4 different Distance rules available:
 * - Distance - (Use install()) This rule will account for distance between two joints across all 3 major axis of
 *              the Kinect's viewable coordinate plane (e.g. distance over the x-, y- and z-axis'.)
 * - DistanceX - (Use installX()) This rule will account for distance between two joints on only the x-axis of
 *              the Kinect's viewable coordinate plane (e.g. distance over the x-axis.)
 * - DistanceY - (Use installY()) This rule will account for the distance between two joints on only the y-axis of
 *              the Kinect's viewable coordinate plane (e.g. distance over the y-axis.)
 * - DistanceZ - (Use installZ()) This rule will account for the distance between two joints on only the z-axis of
 *              the Kinect's viewable coordinate plane (e.g. distance over the z-axis.)
 */
public class DistanceRuleInstaller {

    /**
     * This rule will add a requirement to the gesture's matched case that will account for distance between two joints
     * across all 3 major axis of the Kinect's viewable coordinate plane (e.g. distance over the x-, y- and z-axis'.)
     * @param gesture The gesture that the Distance rule object and respective Esper patterns will be installed into.
     * @param joint1 The first joint of interest for the calculation of the distance.
     * @param joint2 The second joint of interest for the calculation of the distance.
     * @param minDistance For this rule to be matched, the updated Distance event bean must have a
     *                    distance greater than or equal to this number.
     * @param maxDistance For this rule to be matched, the updated Distance event bean must have a
     *                    distance less than or equal to this number.
     * @return A reference to the Distance event bean object that was installed into the gesture. This event bean will be
     * updated with data from the Kinect's skeleton tracking. The reference is great for when a Reaction needs to reference
     * the distance between joints on the user's skeleton (e.g. MouseReaction.)
     */
    public static Distance install(Gesture gesture, String joint1, String joint2, Double minDistance, Double maxDistance) {
        System.out.printf("MAX_DISTANCE: %f\n", maxDistance.doubleValue());
        System.out.printf("MIN_DISTANCE: %f\n", minDistance.doubleValue());
        System.out.printf("JOINT1: %s\n", joint1);
        System.out.printf("JOINT2: %s\n", joint2);

        int joint1Id     = Conversions.getJointId(joint1);
        int joint2Id     = Conversions.getJointId(joint2);

        String patternChunk1 = String.format("Distance(joint1=%d, joint2=%d, distance > %f, distance < %f)", joint1Id,
                                                                                                      joint2Id,
                                                                                                      minDistance.doubleValue(),
                                                                                                      maxDistance.doubleValue());

        String patternChunk2 = String.format("Distance(joint1=%d, joint2=%d, distance < %f)", joint1Id,
                                                                                       joint2Id,
                                                                                       minDistance.doubleValue());

        String patternChunk3 = String.format("Distance(joint1=%d, joint2=%d, distance > %f)", joint1Id,
                                                                                       joint2Id,
                                                                                       maxDistance.doubleValue());

        Distance rule = new Distance(joint1Id, joint2Id, 0);
        rule = (Distance)gesture.addRule(rule, patternChunk1, String.format("(%s or %s)", patternChunk3, patternChunk2));

        return rule;
    }

    /**
    * This rule will add a requirement to the gesture's matched case that will account for distance between two joints
    * on only the x-axis of the Kinect's viewable coordinate plane (e.g. distance over the x-axis.)
    * @param gesture The gesture that the DistanceX rule object and respective Esper patterns will be installed into.
    * @param joint1 The first joint of interest for the calculation of the distance.
    * @param joint2 The second joint of interest for the calculation of the distance.
    * @param minDistance For this rule to be matched, the updated DistanceX event bean must have a
    *                    distance greater than or equal to this number.
    * @param maxDistance For this rule to be matched, the updated DistanceX event bean must have a
    *                    distance less than or equal to this number.
    * @return A reference to the DistanceX event bean object that was installed into the gesture. This event bean will be
    * updated with data from the Kinect's skeleton tracking. The reference is great for when a Reaction needs to reference
    * the distance between joints on the user's skeleton (e.g. MouseReaction.)
    */
    public static DistanceX installX(Gesture gesture, String joint1, String joint2, Double minDistance, Double maxDistance) {
        System.out.printf("MAX_DISTANCE: %f\n", maxDistance.doubleValue());
        System.out.printf("MIN_DISTANCE: %f\n", minDistance.doubleValue());
        System.out.printf("JOINT1: %s\n", joint1);
        System.out.printf("JOINT2: %s\n", joint2);

        int joint1Id     = Conversions.getJointId(joint1);
        int joint2Id     = Conversions.getJointId(joint2);

        String patternChunk1 = String.format("DistanceX(joint1=%d, joint2=%d, distance > %f, distance < %f)", joint1Id,
                                                                                                      joint2Id,
                                                                                                      minDistance.doubleValue(),
                                                                                                      maxDistance.doubleValue());

        String patternChunk2 = String.format("DistanceX(joint1=%d, joint2=%d, distance < %f)", joint1Id,
                                                                                       joint2Id,
                                                                                       minDistance.doubleValue());

        String patternChunk3 = String.format("DistanceX(joint1=%d, joint2=%d, distance > %f)", joint1Id,
                                                                                       joint2Id,
                                                                                       maxDistance.doubleValue());

        DistanceX rule = new DistanceX(joint1Id, joint2Id, 0);
        rule = (DistanceX)gesture.addRule(rule, patternChunk1, String.format("(%s or %s)", patternChunk3, patternChunk2));

        return rule;
    }

    /**
    * This rule will add a requirement to the gesture's matched case that will account for distance between two joints
    * on only the y-axis of the Kinect's viewable coordinate plane (e.g. distance over the y-axis.)
    * @param gesture The gesture that the DistanceY rule object and respective Esper patterns will be installed into.
    * @param joint1 The first joint of interest for the calculation of the distance.
    * @param joint2 The second joint of interest for the calculation of the distance.
    * @param minDistance For this rule to be matched, the updated DistanceY event bean must have a
    *                    distance greater than or equal to this number.
    * @param maxDistance For this rule to be matched, the updated DistanceY event bean must have a
    *                    distance less than or equal to this number.
    * @return A reference to the DistanceY event bean object that was installed into the gesture. This event bean will be
    * updated with data from the Kinect's skeleton tracking. The reference is great for when a Reaction needs to reference
    * the distance between joints on the user's skeleton (e.g. MouseReaction.)
    */
    public static DistanceY installY(Gesture gesture, String joint1, String joint2, Double minDistance, Double maxDistance) {
        System.out.printf("MAX_DISTANCE: %f\n", maxDistance.doubleValue());
        System.out.printf("MIN_DISTANCE: %f\n", minDistance.doubleValue());
        System.out.printf("JOINT1: %s\n", joint1);
        System.out.printf("JOINT2: %s\n", joint2);

        int joint1Id     = Conversions.getJointId(joint1);
        int joint2Id     = Conversions.getJointId(joint2);

        String patternChunk1 = String.format("DistanceY(joint1=%d, joint2=%d, distance > %f, distance < %f)", joint1Id,
                                                                                                      joint2Id,
                                                                                                      minDistance.doubleValue(),
                                                                                                      maxDistance.doubleValue());

        String patternChunk2 = String.format("DistanceY(joint1=%d, joint2=%d, distance < %f)", joint1Id,
                                                                                       joint2Id,
                                                                                       minDistance.doubleValue());

        String patternChunk3 = String.format("DistanceY(joint1=%d, joint2=%d, distance > %f)", joint1Id,
                                                                                       joint2Id,
                                                                                       maxDistance.doubleValue());

        DistanceY rule = new DistanceY(joint1Id, joint2Id, 0);
        rule = (DistanceY)gesture.addRule(rule, patternChunk1, String.format("(%s or %s)", patternChunk3, patternChunk2));

        return rule;
    }
    /**
    * This rule will add a requirement to the gesture's matched case that will account for distance between two joints
    * on only the z-axis of the Kinect's viewable coordinate plane (e.g. distance over the z-axis.)
    * @param gesture The gesture that the DistanceZ rule object and respective Esper patterns will be installed into.
    * @param joint1 The first joint of interest for the calculation of the distance.
    * @param joint2 The second joint of interest for the calculation of the distance.
    * @param minDistance For this rule to be matched, the updated DistanceZ event bean must have a
    *                    distance greater than or equal to this number.
    * @param maxDistance For this rule to be matched, the updated DistanceZ event bean must have a
    *                    distance less than or equal to this number.
    * @return A reference to the DistanceZ event bean object that was installed into the gesture. This event bean will be
    * updated with data from the Kinect's skeleton tracking. The reference is great for when a Reaction needs to reference
    * the distance between joints on the user's skeleton (e.g. MouseReaction.)
    */
    public static DistanceZ installZ(Gesture gesture, String joint1, String joint2, Double minDistance, Double maxDistance) {
        System.out.printf("MAX_DISTANCE: %f\n", maxDistance.doubleValue());
        System.out.printf("MIN_DISTANCE: %f\n", minDistance.doubleValue());
        System.out.printf("JOINT1: %s\n", joint1);
        System.out.printf("JOINT2: %s\n", joint2);

        int joint1Id     = Conversions.getJointId(joint1);
        int joint2Id     = Conversions.getJointId(joint2);

        String patternChunk1 = String.format("DistanceZ(joint1=%d, joint2=%d, distance > %f, distance < %f)", joint1Id,
                                                                                                      joint2Id,
                                                                                                      minDistance.doubleValue(),
                                                                                                      maxDistance.doubleValue());

        String patternChunk2 = String.format("DistanceZ(joint1=%d, joint2=%d, distance < %f)", joint1Id,
                                                                                       joint2Id,
                                                                                       minDistance.doubleValue());

        String patternChunk3 = String.format("DistanceZ(joint1=%d, joint2=%d, distance > %f)", joint1Id,
                                                                                       joint2Id,
                                                                                       maxDistance.doubleValue());

        DistanceZ rule = new DistanceZ(joint1Id, joint2Id, 0);
        rule = (DistanceZ)gesture.addRule(rule, patternChunk1, String.format("(%s or %s)", patternChunk3, patternChunk2));

        return rule;
    }
}
