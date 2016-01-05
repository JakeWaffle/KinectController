package com.wafflesoft.kinectcontroller.config.installers;

import com.wafflesoft.kinectcontroller.Conversions;
import com.wafflesoft.kinectcontroller.controller.Gesture;
import com.wafflesoft.kinectcontroller.posturerules.Angle;

/**
 * Created by jake on 1/1/2016.
 * This supplies functions necessary for installing an Angle rule into a Gesture object.
 *
 * This rules supplies an additional requirement to be "matched". If some joints form an angle within some threshold,
 * then this requirement would be satisfied. Then the gesture is able to trigger its reactions once it is
 * considered matched.
 *
 * - Angle - (Use install()) This rule will account for an angle between 3 joints on the user that the Kinect is tracking.
 *           If those 3 joints form an angle that is within the given thresholds, the Angle rule will be considered
 *           satisfied.
 */
public class AngleRuleInstaller {
    /**
     * This rule adds a requirement to the gesture's "matched" case. This rule will account for an angle between 3
     * joints on the user that the Kinect is tracking. If those 3 joints form an angle that is within the given
     * thresholds, the Angle rule will be considered satisfied.
     * @param gesture The gesture that the Distance rule object and respective Esper patterns will be installed into.
     * @param end1 A joint on the user's skeleton that acts as one of the end points of the angle.
     * @param vertex A joint on the user's skeleton that acts as the vertex of the angle.
     * @param end2 A joint on the user's skeleton that acts as one of the end points of the angle.
     * @param minAngle For this rule to be matched, the updated Angle event bean must have an
     *                 angle greater than or equal to this number.
     * @param maxAngle For this rule to be matched, the updated Angle event bean must have an
     *                 angle less than or equal to this number.
     * @return A reference to the Angle event bean object that was installed into the gesture. This event bean will be
     * updated with data from the Kinect's skeleton tracking. The reference is great for when a Reaction needs to reference
     * an angle on the user's skeleton (e.g. MouseReaction.)
     */
    public static Angle install(Gesture gesture, String end1, String vertex, String end2, Integer minAngle, Integer maxAngle) {
        System.out.printf("MAX_ANGLE: %d\n", maxAngle.intValue());
        System.out.printf("MIN_ANGLE: %d\n", minAngle.intValue());
        System.out.printf("END1: %s\n", end1);
        System.out.printf("END2: %s\n", end2);
        System.out.printf("VERTEX: %s\n", vertex);

        int end1Id     = Conversions.getJointId(end1);
        int vertexId   = Conversions.getJointId(vertex);
        int end2Id     = Conversions.getJointId(end2);

        String patternChunk1 = String.format("Angle(end1=%d, vertex=%d, end2=%d, angle > %d, angle < %d)", end1Id,
                                                                                                    vertexId,
                                                                                                    end2Id,
                                                                                                    minAngle.intValue(),
                                                                                                    maxAngle.intValue());

        String patternChunk2 = String.format("Angle(end1=%d, vertex=%d, end2=%d, angle < %d)", end1Id,
                                                                                        vertexId,
                                                                                        end2Id,
                                                                                        minAngle.intValue());

        String patternChunk3 = String.format("Angle(end1=%d, vertex=%d, end2=%d, angle > %d)", end1Id,
                                                                                        vertexId,
                                                                                        end2Id,
                                                                                        maxAngle.intValue());

        Angle angleRule = new Angle(end1Id, vertexId, end2Id, 0);

        //arguments are the empty event bean and the matched and negated rule conditions for this specific angle rule.
        angleRule = (Angle)gesture.addRule(angleRule, patternChunk1, String.format("(%s or %s)", patternChunk3, patternChunk2));

        return angleRule;
    }
}
