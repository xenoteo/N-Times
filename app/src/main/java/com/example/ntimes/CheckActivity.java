package com.example.ntimes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CheckActivity extends AppCompatActivity {
    /*
    private SharedPreferences sharedPreferences;

    private int roundsNumber;

    private boolean roundsSame;
    private boolean restsSame;

    private TextView roundsNumberTextView;
    private TextView exercisesNumberTextView;
    private TextView exerciseTimeTextView;
    private TextView exercisesRestTimeTextView;
    private TextView roundsRestTimeTextView;

     */

    // TODO make summary table

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

//        setSharedPreferences();
//        fillText();
    }

    /*
    private void setSharedPreferences(){
        sharedPreferences = getSharedPreferences(Key.SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    private void fillText(){
        setTextViews();
        setGeneralParameters();

        roundsNumberTextView.setText(String.valueOf(roundsNumber));

        if (roundsSame){
            int exercisesNumber = sharedPreferences.getInt(Key.EXERCISES_NUMBER, 0);
            int exerciseTime = sharedPreferences.getInt(Key.EXERCISE_TIME, 0);
            int exercisesRestTime = sharedPreferences.getInt(Key.EXERCISES_REST_TIME, 0);

            exercisesNumberTextView.setText(String.valueOf(exercisesNumber));
            exerciseTimeTextView.setText(makeTimeString(exerciseTime));
            exercisesRestTimeTextView.setText(makeTimeString(exercisesRestTime));
        }
        else {
            List<Integer> exerciseNumbers = getTimes(Key.EXERCISES_NUMBER);
            List<Integer> exerciseTimes = getTimes(Key.EXERCISE_TIME);
            List<Integer> exerciseRestTimes = getTimes(Key.EXERCISES_REST_TIME);

            exercisesNumberTextView.setText(makeString(exerciseNumbers));
            exerciseTimeTextView.setText(makeTimesString(exerciseTimes));
            exercisesRestTimeTextView.setText(makeTimesString(exerciseRestTimes));
        }


        if (restsSame){
            int roundsRestTime = sharedPreferences.getInt(Key.ROUNDS_REST_TIME, 0);
            roundsRestTimeTextView.setText(makeTimeString(roundsRestTime));
        }
        else {
            List<Integer> roundRestTimes = getTimes(Key.ROUNDS_REST_TIME);
            roundsRestTimeTextView.setText(makeString(roundRestTimes));
        }
    }

    private String makeTimeString(int time){
        String timeString = " ";
        int min = time / 60;
        if (min != 0) timeString += min + " min ";
        timeString += time % 60 + " s";
        return timeString;
    }

    private List<Integer> getTimes(String key){
        List<Integer> times = new LinkedList<>();
        for (int i = 1; i <= roundsNumber; i++){
            times.add(sharedPreferences.getInt(key + i, 0));
        }
        if (key.equals(Key.ROUNDS_REST_TIME)) times.remove(roundsNumber - 1);
        return times;
    }

    private void setTextViews(){
        roundsNumberTextView = findViewById(R.id.checkRoundsNumber);
        exercisesNumberTextView = findViewById(R.id.checkExercises);
        exerciseTimeTextView = findViewById(R.id.checkExerciseTime);
        exercisesRestTimeTextView = findViewById(R.id.checkExercisesRest);
        roundsRestTimeTextView = findViewById(R.id.checkRoundRest);
    }

    private void setGeneralParameters(){
        roundsNumber = sharedPreferences.getInt(Key.ROUNDS_NUMBER, 0);
        restsSame = sharedPreferences.getBoolean(Key.RESTS_SAME, true);
        roundsSame = sharedPreferences.getBoolean(Key.ROUNDS_SAME, true);
    }

    private String makeString(List<Integer> list){
        StringBuilder str = new StringBuilder();
        int size = list.size();

        for (int i = 0; i < size - 1; i++){
            str.append(list.get(i));
            str.append(", ");
        }
        str.append(list.get(size - 1));
        return str.toString();
    }

    private String makeTimesString(List<Integer> list){
        StringBuilder str = new StringBuilder();
        int size = list.size();

        for (int i = 0; i < size - 1; i++){
            str.append(makeTimeString(list.get(i)));
            str.append(", ");
        }
        str.append(list.get(size - 1));
        return str.toString();
    }

     */

    public void edit(View v){
        startActivity(new Intent(this, RoundNumberActivity.class));
    }

    public void start(View v){
        startActivity(new Intent(this, TimeActivity.class));
    }
}