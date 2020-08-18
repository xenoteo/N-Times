package com.example.ntimes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

public class RoundActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private Editor editor;

    private NumberPicker exercisesNumberPicker;
    private NumberPicker exerciseMinPicker;
    private NumberPicker exerciseSecPicker;
    private NumberPicker restMinPicker;
    private NumberPicker restSecPicker;

    private TextView roundText;
    private TextView roundDisplay;

    private boolean roundsSame;
    private boolean restsSame;
    private int currentRound;
    private int roundsNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        setSharedPreferences();
        setExercisesNumberPicker();
        setTimePickers();
        setData();
        setHeader();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (roundsSame){
            exercisesNumberPicker.setValue(sharedPreferences.getInt(Key.EXERCISES_NUMBER, 0));
            exerciseMinPicker.setValue(sharedPreferences.getInt(Key.EXERCISE_TIME, 0) / 60);
            exerciseSecPicker.setValue(sharedPreferences.getInt(Key.EXERCISE_TIME, 0) % 60);
            restMinPicker.setValue(sharedPreferences.getInt(Key.EXERCISES_REST_TIME, 0) / 60);
            restSecPicker.setValue(sharedPreferences.getInt(Key.EXERCISES_REST_TIME, 0) % 60);
        }
        else {
            setCurrentRound();
            exercisesNumberPicker.setValue(sharedPreferences
                    .getInt(Key.EXERCISES_NUMBER + currentRound, 0));
            exerciseMinPicker.setValue(sharedPreferences
                    .getInt(Key.EXERCISE_TIME + currentRound, 0) / 60);
            exerciseSecPicker.setValue(sharedPreferences
                    .getInt(Key.EXERCISE_TIME + currentRound, 0) % 60);
            restMinPicker.setValue(sharedPreferences
                    .getInt(Key.EXERCISES_REST_TIME + currentRound, 0) / 60);
            restSecPicker.setValue(sharedPreferences
                    .getInt(Key.EXERCISES_REST_TIME + currentRound, 0) % 60);
        }

    }

    private void setSharedPreferences(){
        sharedPreferences = getSharedPreferences(Key.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void setExercisesNumberPicker(){
        exercisesNumberPicker = findViewById(R.id.exercisesNumberPicker);
        exercisesNumberPicker.setMaxValue(30);
        exercisesNumberPicker.setMinValue(1);
    }

    private void setTimePickers(){
        exerciseMinPicker = findViewById(R.id.exercisesMinPicker);
        exerciseSecPicker = findViewById(R.id.exercisesSecPicker);
        restMinPicker = findViewById(R.id.roundRestSecPicker);
        restSecPicker = findViewById(R.id.roundRestMinPicker);

        exerciseMinPicker.setMaxValue(60);
        exerciseMinPicker.setMinValue(0);

        exerciseSecPicker.setMaxValue(59);
        exerciseSecPicker.setMinValue(0);

        restMinPicker.setMaxValue(60);
        restMinPicker.setMinValue(0);

        restSecPicker.setMaxValue(59);
        restSecPicker.setMinValue(0);
    }

    public void next(View v){
        int exercisesNumber = exercisesNumberPicker.getValue();
        int exerciseTime = exerciseMinPicker.getValue() * 60 + exerciseSecPicker.getValue();
        int restTime = restMinPicker.getValue() * 60 + restSecPicker.getValue();    // in seconds

        if (roundsSame){
            editor.putInt(Key.EXERCISES_NUMBER, exercisesNumber);
            editor.putInt(Key.EXERCISE_TIME, exerciseTime);
            editor.putInt(Key.EXERCISES_REST_TIME, restTime);
        }
        else {
            editor.putInt(Key.EXERCISES_NUMBER + currentRound, exercisesNumber);
            editor.putInt(Key.EXERCISE_TIME + currentRound, exerciseTime);
            editor.putInt(Key.EXERCISES_REST_TIME + currentRound, restTime);
        }
        editor.apply();

        if (!roundsSame && currentRound < roundsNumber) {
            Intent intent = new Intent(this, RoundActivity.class);
            intent.putExtra(Key.CURRENT_ROUND, currentRound + 1);
            startActivity(intent);
        }
        else if (!restsSame) {
            Intent intent = new Intent(this, RestActivity.class);
            intent.putExtra(Key.CURRENT_REST, 1);
            startActivity(intent);
        }
        else
            startActivity(new Intent(this, RestActivity.class));
    }

    public void back(View v){
        if (roundsSame || currentRound == 1)
            startActivity(new Intent(this, MainActivity.class));
        else {
            Intent intent = new Intent(this, RoundActivity.class);
            intent.putExtra(Key.CURRENT_ROUND, currentRound - 1);
            startActivity(intent);
        }
    }

    public void setHeader(){
        setRoundTextViews();

        if (roundsSame) {
            setTextViewsVisibility(View.INVISIBLE);
        }
        else {
            setTextViewsVisibility(View.VISIBLE);
            setCurrentRound();
            roundDisplay.setText(String.valueOf(currentRound));
        }
    }

    private void setRoundTextViews(){
        roundText = findViewById(R.id.roundNumberTextView);
        roundDisplay = findViewById(R.id.roundNumberDisplay);
    }

    private void setTextViewsVisibility(int visibility){
        roundText.setVisibility(visibility);
        roundDisplay.setVisibility(visibility);
    }

    private void setData(){
        roundsSame = sharedPreferences.getBoolean(Key.ROUNDS_SAME, true);
        roundsNumber = sharedPreferences.getInt(Key.ROUNDS_NUMBER, 0);
        restsSame = sharedPreferences.getBoolean(Key.RESTS_SAME, true);
    }

    private void setCurrentRound(){
        currentRound = getIntent().getIntExtra(Key.CURRENT_ROUND, 0);
    }
}
