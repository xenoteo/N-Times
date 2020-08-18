package com.example.ntimes;

/**
 * Public class to store string keys for the values stored in intents.
 * Created to prevent from different spelling while getting or putting a value.
 */
public class Key {
    public static String SHARED_PREFERENCES = "preferences";

    public static String ROUNDS_NUMBER = "roundsNumber";

    public static String ROUNDS_SAME = "roundsSame";
    public static String RESTS_SAME = "restsSame";

    // for the same rounds
    public static String EXERCISES_NUMBER = "exercisesNumber";
    public static String EXERCISE_TIME = "exerciseTime";
    public static String EXERCISES_REST_TIME = "exercisesRestTime";

    // for the same round rests
    public static String ROUNDS_REST_TIME = "roundsRestTime";

    public static String CURRENT_ROUND = "currentRound";
    public static String CURRENT_REST = "currentRest";

//    // for different rounds
//    public static String EXERCISES_NUMBERS = "exercisesNumbers";
//    public static String EXERCISE_TIMES = "exerciseTimes";
//    public static String EXERCISES_REST_TIMES = "exercisesRestTimes";
//
//    // for different round rests
//    public static String ROUND_REST_TIMES = "roundRestTimes";
}
