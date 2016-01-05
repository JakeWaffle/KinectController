package com.wafflesoft.kinectcontroller.config.installers;

import com.wafflesoft.kinectcontroller.Conversions;
import com.wafflesoft.kinectcontroller.controller.Gesture;
import com.wafflesoft.kinectcontroller.posturerules.DistanceFromPoint;

/**
 * Created by jake on 1/3/2016.
 * This supplies functions necessary for installing a DistanceFromPoint rule into a Gesture object.
 *
 * This rules supplies an additional requirement to be "matched". If some joint's position is within some distance threshold of
 * a fixed point, then this requirement would be satisfied. Then the gesture is able to trigger its reactions once it is
 * considered matched.
 *
 * - Angle - (Use install()) This rule will account for the distance between some point and a joint on the user that the
 *           Kinect is tracking. If those 3 joints form an angle that is within the given thresholds, the Angle rule
 *           will be considered satisfied.
 */
public class DistanceFromPointRuleInstaller {
    /**
     * This rule adds a requirement to the gesture's "matched" case. This rule will account for the distance between
     * some point and a joint on the user that the Kinect is tracking. If the user's joint is within
     * the given threshold from the fixed point, the Angle rule will be considered satisfied.
     * @param gesture The gesture that the DistanceFromPoint rule object and respective Esper patterns will be installed into.
     * @param joint The joint of interest for calculating the distance.
     * @param point The fixed point for calculating the distance from the joint.
     * @param minDistance For this rule to be matched, the updated DistanceFromPoint event bean must have a
     *                    distance greater than or equal to this number.
     * @param maxDistance For this rule to be matched, the updated DistanceFromPoint event bean must have a
     *                    distance less than or equal to this number.
     * @return A reference to the DistanceFromPoint event bean object that was installed into the gesture. This event bean will be
     * updated with data from the Kinect's skeleton tracking. The reference is great for when a Reaction needs to reference
     * a joint's distance from some fixed point on the user's skeleton (e.g. MouseReaction.)
     */
    public static DistanceFromPoint install(Gesture gesture, String joint, double[] point, Double minDistance, Double maxDistance) {
        System.out.printf("MAX_DISTANCE: %f\n", maxDistance);
        System.out.printf("MIN_DISTANCE: %f\n", minDistance);
        System.out.printf("JOINT: %s\n", joint);

        int jointId = Conversions.getJointId(joint);

        String patternChunk1 = String.format("DistanceFromPoint(point[0]=%f, point[1]=%f, point[2]=%f, joint=%d, distance > %f, distance < %f)",
                                                                                        point[0],
                                                                                        point[1],
                                                                                        point[2],
                                                                                        jointId,
                                                                                        minDistance,
                                                                                        maxDistance);

        String patternChunk2 = String.format("DistanceFromPoint(point[0]=%f, point[1]=%f, point[2]=%f, joint=%d, distance < %f)",
                                                                                        point[0],
                                                                                        point[1],
                                                                                        point[2],
                                                                                        jointId,
                                                                                        minDistance);

        String patternChunk3 = String.format("DistanceFromPoint(point[0]=%f, point[1]=%f, point[2]=%f, joint=%d, distance > %f)",
                                                                                        point[0],
                                                                                        point[1],
                                                                                        point[2],
                                                                                        jointId,
                                                                                        maxDistance);

        DistanceFromPoint rule = new DistanceFromPoint(point, jointId);
        rule = (DistanceFromPoint)gesture.addRule(rule, patternChunk1, String.format("(%s or %s)", patternChunk3, patternChunk2));

        return rule;
    }
}
