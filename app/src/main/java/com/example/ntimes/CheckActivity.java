package com.example.ntimes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CheckActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private int roundsNumber;
    private int exercisesNumber;
    private int exerciseTime;
    private int exercisesRestTime;
    private int roundsRestTime;
    private TextView roundsNumberTextView;
    private TextView exercisesNumberTextView;
    private TextView exerciseTimeTextView;
    private TextView exercisesRestTimeTextView;
    private TextView roundsRestTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        setSharedPreferences();
        fillText();
    }

    private void setSharedPreferences(){
        sharedPreferences = getSharedPreferences(Key.SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    private void fillText(){
        setNumbers();
        setTextViews();

        roundsNumberTextView.setText(String.valueOf(roundsNumber));
        exercisesNumberTextView.setText(String.valueOf(exercisesNumber));
        exerciseTimeTextView.setText(getTimeString(exerciseTime));
        exercisesRestTimeTextView.setText(getTimeString(exercisesRestTime));
        roundsRestTimeTextView.setText(getTimeString(roundsRestTime));
    }

    private String getTimeString(int time){
        String timeString = " ";
        int min = time / 60;
        if (min != 0) timeString += min + " min ";
        timeString += time % 60 + " s";
        return timeString;
    }

    private void setNumbers(){
        roundsNumber = sharedPreferences.getInt(Key.ROUNDS_NUMBER, 0);
        exercisesNumber = sharedPreferences.getInt(Key.EXERCISES_NUMBER, 0);
        exerciseTime = sharedPreferences.getInt(Key.EXERCISE_TIME, 0);
        exercisesRestTime = sharedPreferences.getInt(Key.EXERCISES_REST_TIME, 0);
        roundsRestTime = sharedPreferences.getInt(Key.ROUNDS_REST_TIME, 0);
    }

    private void setTextViews(){
        roundsNumberTextView = (TextView) findViewById(R.id.checkRoundsNumber);
        exercisesNumberTextView = (TextView) findViewById(R.id.checkExercises);
        exerciseTimeTextView = (TextView) findViewById(R.id.checkExerciseTime);
        exercisesRestTimeTextView = (TextView) findViewById(R.id.checkExercisesRest);
        roundsRestTimeTextView = (TextView) findViewById(R.id.checkRoundRest);
    }

    public void edit(View v){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void start(View v){
        // TODO
    }
}