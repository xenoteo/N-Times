package com.example.ntimes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

public class RoundActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    Editor editor;

    NumberPicker exercisesNumberPicker;
    NumberPicker exerciseMinPicker;
    NumberPicker exerciseSecPicker;
    NumberPicker restMinPicker;
    NumberPicker restSecPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        setSharedPreferences();
        setExercisesNumberPicker();
        setTimePickers();
    }


    @Override
    protected void onResume() {
        super.onResume();

        exercisesNumberPicker.setValue(sharedPreferences.getInt(Key.EXERCISES_NUMBER, 0));
        exerciseMinPicker.setValue(sharedPreferences.getInt(Key.EXERCISE_TIME, 0) / 60);
        exerciseSecPicker.setValue(sharedPreferences.getInt(Key.EXERCISE_TIME, 0) % 60);
        restMinPicker.setValue(sharedPreferences.getInt(Key.EXERCISES_REST_TIME, 0) / 60);
        restSecPicker.setValue(sharedPreferences.getInt(Key.EXERCISES_REST_TIME, 0) % 60);
    }

    private void setSharedPreferences(){
        sharedPreferences = getSharedPreferences(Key.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void setExercisesNumberPicker(){
        exercisesNumberPicker = (NumberPicker)findViewById(R.id.exercisesNumberPicker);
        exercisesNumberPicker.setMaxValue(30);
        exercisesNumberPicker.setMinValue(1);
    }

    private void setTimePickers(){
        exerciseMinPicker = (NumberPicker) findViewById(R.id.exercisesMinPicker);
        exerciseSecPicker = (NumberPicker) findViewById(R.id.exercisesSecPicker);
        restMinPicker = (NumberPicker) findViewById(R.id.roundRestSecPicker);
        restSecPicker = (NumberPicker) findViewById(R.id.roundRestMinPicker);

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

        editor.putInt(Key.EXERCISES_NUMBER, exercisesNumber);
        editor.putInt(Key.EXERCISE_TIME, exerciseTime);
        editor.putInt(Key.EXERCISES_REST_TIME, restTime);
        editor.apply();

        startActivity(new Intent(this, RestActivity.class));
    }

    public void back(View v){
        startActivity(new Intent(this, MainActivity.class));
    }
}