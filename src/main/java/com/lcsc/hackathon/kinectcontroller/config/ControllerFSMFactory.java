/* Generated By:JavaCC: Do not edit this line. ControllerFSMFactory.java */
package com.lcsc.hackathon.kinectcontroller.config;

import java.io.*;
import java.util.*;
import com.lcsc.hackathon.kinectcontroller.controller.*;
import com.lcsc.hackathon.kinectcontroller.posturerules.*;

import com.lcsc.hackathon.kinectcontroller.Conversions;

//These are the Reaction objects.

import com.lcsc.hackathon.kinectcontroller.emulation.*;
import com.lcsc.hackathon.kinectcontroller.emulation.reactions.*;
import com.lcsc.hackathon.kinectcontroller.emulation.reactions.config.*;

public class ControllerFSMFactory implements ControllerFSMFactoryConstants {
    private ControllerStateMachine _csm;
    private String _startingStateId = null;

    private void addStartingState(String stateId) {
        //If we can't change the state, then we'll just store the id until the state is added later.
        if (!_csm.changeState(stateId)) {
            _startingStateId = stateId;
        }
    }

    private void addState(ControllerState state) {
        _csm.addState(state);

        //If the starting state hasn't been set and this state is the starting state.
        if (_startingStateId != null && _startingStateId.equals(state.stateId)) {
            if (_csm.changeState(_startingStateId)) {
                _startingStateId = null;
            }
        }
    }

  final public ControllerStateMachine create() throws ParseException {
    Token t;
    int version;
     _csm = new ControllerStateMachine();
    jj_consume_token(VERSION);
    t = jj_consume_token(NUMBER);
     version = Integer.parseInt(t.image);
        switch(version) {
            case 1:
                parseVersion1();
                break;
        }
    jj_consume_token(0);
     {if (true) return _csm;}
    throw new Error("Missing return statement in function");
  }

  final public void parseVersion1() throws ParseException {
    String stateId;
    ControllerState state;
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case STARTING_STATE:
      case STATE:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case STARTING_STATE:
        jj_consume_token(STARTING_STATE);
        jj_consume_token(IS);
        stateId = jj_consume_token(IDENTIFIER).image;
             addStartingState(stateId);
        break;
      case STATE:
        jj_consume_token(STATE);
        stateId = jj_consume_token(IDENTIFIER).image;
        state = parseState(stateId);
             addState(state);
        break;
      default:
        jj_la1[1] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
  }

  final public ControllerState parseState(String stateId) throws ParseException {
    ControllerState state;
    String gestureId;
    String armId;
     state = new ControllerState(stateId, _csm);
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case GESTURE:
      case MOUSE_GESTURE:
        ;
        break;
      default:
        jj_la1[2] = jj_gen;
        break label_2;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case GESTURE:
        jj_consume_token(GESTURE);
        gestureId = jj_consume_token(IDENTIFIER).image;
        parseGesture(gestureId, state);
        break;
      case MOUSE_GESTURE:
        jj_consume_token(MOUSE_GESTURE);
        armId = jj_consume_token(IDENTIFIER).image;
        break;
      default:
        jj_la1[3] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
     {if (true) return state;}
    throw new Error("Missing return statement in function");
  }

  final public void parseGesture(String gestureId, ControllerState state) throws ParseException {
    String          ruleType;
    String          reactionType;
    Token           t;
    Gesture         gesture = new Gesture(gestureId, state);
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case RULE:
      case REACTION:
        ;
        break;
      default:
        jj_la1[4] = jj_gen;
        break label_3;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case RULE:
        jj_consume_token(RULE);
        ruleType = jj_consume_token(IDENTIFIER).image;
                switch(RuleType.fromString(ruleType)) {
                    case ABS_DISTANCE:
                        break;
                    case ABS_DISTANCEX:
                        break;
                    case ABS_DISTANCEY:
                        break;
                    case ABS_DISTANCEZ:
                        break;
                    case ANGLE:
                        parseAngleRule(state, gesture);
                        break;
                    case DISTANCE:
                    case DISTANCEX:
                    case DISTANCEY:
                    case DISTANCEZ:
                        parseDistanceRule(state, gesture, RuleType.fromString(ruleType));
                        break;
                }
        break;
      case REACTION:
        jj_consume_token(REACTION);
        reactionType = jj_consume_token(IDENTIFIER).image;
                switch(ReactionType.fromString(reactionType)) {
                    case KEY_DOWN_UP:
                    case KEY_DOWN:
                    case KEY_UP:
                        parseKeyReaction(gesture, ReactionType.fromString(reactionType));
                        break;
                }
        break;
      default:
        jj_la1[5] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
     state.addGesture(gestureId, gesture);
  }

  final public void parseKeyReaction(Gesture gesture, ReactionType reactionType) throws ParseException {
    String keyId;
    jj_consume_token(KEY);
    jj_consume_token(IS);
    keyId = jj_consume_token(IDENTIFIER).image;
                ButtonReactionConfig config = new ButtonReactionConfig(keyId, "keyboard");
                gesture.addReaction(new ButtonReaction(config));
  }

  final public void parseAngleRule(ControllerState state, Gesture gesture) throws ParseException {
    Token   t;
    String  end1        = null;
    String  vertex      = null;
    String  end2        = null;
    Integer minAngle    = null;
    Integer maxAngle    = null;

    String  patternChunk1;
    String  patternChunk2;
    String  patternChunk3;
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case END1:
      case END2:
      case VERTEX:
      case MIN_ANGLE:
      case MAX_ANGLE:
        ;
        break;
      default:
        jj_la1[6] = jj_gen;
        break label_4;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case END1:
        jj_consume_token(END1);
        jj_consume_token(IS);
        end1 = jj_consume_token(IDENTIFIER).image;
        break;
      case END2:
        jj_consume_token(END2);
        jj_consume_token(IS);
        end2 = jj_consume_token(IDENTIFIER).image;
        break;
      case VERTEX:
        jj_consume_token(VERTEX);
        jj_consume_token(IS);
        vertex = jj_consume_token(IDENTIFIER).image;
        break;
      case MIN_ANGLE:
        jj_consume_token(MIN_ANGLE);
        jj_consume_token(IS);
        t = jj_consume_token(NUMBER);
             minAngle = Integer.parseInt(t.image);
        break;
      case MAX_ANGLE:
        jj_consume_token(MAX_ANGLE);
        jj_consume_token(IS);
        t = jj_consume_token(NUMBER);
             maxAngle = Integer.parseInt(t.image);
        break;
      default:
        jj_la1[7] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
        System.out.printf("MAX_ANGLE: %d\u005cn", maxAngle.intValue());
        System.out.printf("MIN_ANGLE: %d\u005cn", minAngle.intValue());
        System.out.printf("END1: %s\u005cn", end1);
        System.out.printf("END2: %s\u005cn", end2);
        System.out.printf("VERTEX: %s\u005cn", vertex);

        int end1Id     = Conversions.getJointId(end1);
        int vertexId   = Conversions.getJointId(vertex);
        int end2Id     = Conversions.getJointId(end2);

        patternChunk1 = String.format("Angle(end1=%d, vertex=%d, end2=%d, angle > %d, angle < %d)", end1Id,
                                                                                                    vertexId,
                                                                                                    end2Id,
                                                                                                    minAngle.intValue(),
                                                                                                    maxAngle.intValue());

        patternChunk2 = String.format("Angle(end1=%d, vertex=%d, end2=%d, angle < %d)", end1Id,
                                                                                        vertexId,
                                                                                        end2Id,
                                                                                        minAngle.intValue());

        patternChunk3 = String.format("Angle(end1=%d, vertex=%d, end2=%d, angle > %d)", end1Id,
                                                                                        vertexId,
                                                                                        end2Id,
                                                                                        maxAngle.intValue());


        state.addRule(new Angle(end1Id, vertexId, end2Id, 0));
        gesture.addRuleToEsperPattern(patternChunk1, String.format("(%s or %s)", patternChunk3, patternChunk2));
  }

  final public void parseDistanceRule(ControllerState state, Gesture gesture, RuleType ruleType) throws ParseException {
    Token   t;
    String  joint1      = null;
    String  joint2      = null;
    Double  minDistance = null;
    Double  maxDistance = null;

    String  patternChunk1;
    String  patternChunk2;
    String  patternChunk3;
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case JOINT1:
      case JOINT2:
      case MIN_DISTANCE:
      case MAX_DISTANCE:
        ;
        break;
      default:
        jj_la1[8] = jj_gen;
        break label_5;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case JOINT1:
        jj_consume_token(JOINT1);
        jj_consume_token(IS);
        joint1 = jj_consume_token(IDENTIFIER).image;
        break;
      case JOINT2:
        jj_consume_token(JOINT2);
        jj_consume_token(IS);
        joint2 = jj_consume_token(IDENTIFIER).image;
        break;
      case MIN_DISTANCE:
        jj_consume_token(MIN_DISTANCE);
        jj_consume_token(IS);
        t = jj_consume_token(NUMBER);
             minDistance = Double.parseDouble(t.image);
        break;
      case MAX_DISTANCE:
        jj_consume_token(MAX_DISTANCE);
        jj_consume_token(IS);
        t = jj_consume_token(NUMBER);
             maxDistance = Double.parseDouble(t.image);
        break;
      default:
        jj_la1[9] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
        System.out.printf("MAX_DISTANCE: %f\u005cn", maxDistance.doubleValue());
        System.out.printf("MIN_DISTANCE: %f\u005cn", minDistance.doubleValue());
        System.out.printf("JOINT1: %s\u005cn", joint1);
        System.out.printf("JOINT2: %s\u005cn", joint2);

        int joint1Id     = Conversions.getJointId(joint1);
        int joint2Id     = Conversions.getJointId(joint2);

        //ruleType.className is going to be either Distance, DistanceX, DistanceY or DistanceZ.

        patternChunk1 = String.format(ruleType.className+"(joint1=%d, joint2=%d, distance > %f, distance < %f)", joint1Id,
                                                                                                      joint2Id,
                                                                                                      minDistance.doubleValue(),
                                                                                                      maxDistance.doubleValue());

        patternChunk2 = String.format(ruleType.className+"(joint1=%d, joint2=%d, distance < %f)", joint1Id,
                                                                                       joint2Id,
                                                                                       minDistance.doubleValue());

        patternChunk3 = String.format(ruleType.className+"(joint1=%d, joint2=%d, distance > %f)", joint1Id,
                                                                                       joint2Id,
                                                                                       maxDistance.doubleValue());
        switch(ruleType) {
            case DISTANCE:
                state.addRule(new Distance(joint1Id, joint2Id, 0));
                break;
            case DISTANCEX:
                state.addRule(new DistanceX(joint1Id, joint2Id, 0));
                break;
            case DISTANCEY:
                state.addRule(new DistanceY(joint1Id, joint2Id, 0));
                break;
            case DISTANCEZ:
                state.addRule(new DistanceZ(joint1Id, joint2Id, 0));
                break;
        }
        gesture.addRuleToEsperPattern(patternChunk1, String.format("(%s or %s)", patternChunk3, patternChunk2));
  }

  /** Generated Token Manager. */
  public ControllerFSMFactoryTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[10];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x12,0x12,0x60,0x60,0x20080,0x20080,0x7300,0x7300,0x18c00,0x18c00,};
   }

  /** Constructor with InputStream. */
  public ControllerFSMFactory(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public ControllerFSMFactory(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new ControllerFSMFactoryTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 10; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 10; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public ControllerFSMFactory(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new ControllerFSMFactoryTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 10; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 10; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public ControllerFSMFactory(ControllerFSMFactoryTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 10; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(ControllerFSMFactoryTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 10; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[27];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 10; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 27; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
