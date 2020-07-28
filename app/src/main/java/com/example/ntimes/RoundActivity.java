package com.example.ntimes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

public class RoundActivity extends AppCompatActivity {
    NumberPicker exercisesNumberPicker;
    NumberPicker exerciseMinPicker;
    NumberPicker exerciseSecPicker;
    NumberPicker restMinPicker;
    NumberPicker restSecPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        setExercisesNumberPicker();
        setTimePickers();
    }

    private void setExercisesNumberPicker(){
        exercisesNumberPicker = (NumberPicker)findViewById(R.id.exercisesNumberPicker);
        exercisesNumberPicker.setMaxValue(30);
        exercisesNumberPicker.setMinValue(1);
    }

    private void setTimePickers(){
        exerciseMinPicker = (NumberPicker) findViewById(R.id.exercisesMinPicker);
        exerciseSecPicker = (NumberPicker) findViewById(R.id.exercisesSecPicker);
        restMinPicker = (NumberPicker) findViewById(R.id.restMinPicker);
        restSecPicker = (NumberPicker) findViewById(R.id.restSecPicker);

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
        int exerciseTime = exerciseMinPicker.getValue() * 60
                + exerciseSecPicker.getValue();     // in seconds
        int restTime = restMinPicker.getValue() * 60 + restSecPicker.getValue();    // in seconds
        Intent intent = new Intent(this, RestActivity.class);
        intent.putExtra(Key.EXERCISES_NUMBER, exercisesNumber);
        intent.putExtra(Key.EXERCISE_TIME, exerciseTime);
        intent.putExtra(Key.REST_TIME, restTime);
        Toast.makeText(getApplicationContext(), exercisesNumber + " exercises", Toast.LENGTH_LONG).show();    // test
        startActivity(intent);
    }

    public void back(View v){
        startActivity(new Intent(this, MainActivity.class));
    }
}