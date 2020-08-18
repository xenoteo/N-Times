package com.example.ntimes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

public class RestActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private NumberPicker restMinPicker;
    private NumberPicker restSecPicker;

    private TextView restText;
    private TextView restDisplay;

    private boolean roundsSame;
    private boolean restsSame;
    private int currentRest;
    private int roundsNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);

        setSharedPreferences();
        setTimePickers();
        setData();
        setHeader();
    }

    @Override
    protected void onResume() {
        super.onResume();

        setData();
        if (restsSame){
            restMinPicker.setValue(sharedPreferences.getInt(Key.ROUNDS_REST_TIME, 0) / 60);
            restSecPicker.setValue(sharedPreferences.getInt(Key.ROUNDS_REST_TIME, 0) % 60);
        }
        else {
            setCurrentRest();
            restMinPicker.setValue(sharedPreferences
                    .getInt(Key.ROUNDS_REST_TIME + currentRest, 0) / 60);
            restSecPicker.setValue(sharedPreferences
                    .getInt(Key.ROUNDS_REST_TIME + currentRest, 0) % 60);
        }
    }

    private void setSharedPreferences(){
        sharedPreferences = getSharedPreferences(Key.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void setTimePickers(){
        restSecPicker = findViewById(R.id.roundRestSecPicker);
        restMinPicker = findViewById(R.id.roundRestMinPicker);

        restMinPicker.setMaxValue(60);
        restMinPicker.setMinValue(0);

        restSecPicker.setMaxValue(59);
        restSecPicker.setMinValue(0);
    }

    public void next(View v){
        int restTime = restMinPicker.getValue() * 60 + restSecPicker.getValue();    // in seconds

        if (restsSame)
            editor.putInt(Key.ROUNDS_REST_TIME, restTime);
        else
            editor.putInt(Key.ROUNDS_REST_TIME + currentRest, restTime);
        editor.apply();

        if (restsSame || currentRest == (roundsNumber - 1))
            startActivity(new Intent(this, CheckActivity.class));
        else {
            Intent intent = new Intent(this, RestActivity.class);
            intent.putExtra(Key.CURRENT_REST, currentRest + 1);
            startActivity(intent);
        }
    }

    public void back(View v){
        if (restsSame || currentRest == 1){
            if (roundsSame)
                startActivity(new Intent(this, RoundActivity.class));
            else {
                Intent intent = new Intent(this, RoundActivity.class);
                intent.putExtra(Key.CURRENT_ROUND, roundsNumber);
                startActivity(intent);
            }
        }
        else {
            Intent intent = new Intent(this, RestActivity.class);
            intent.putExtra(Key.CURRENT_REST, currentRest - 1);
            startActivity(intent);
        }
    }

    private void setData(){
        roundsSame = sharedPreferences.getBoolean(Key.ROUNDS_SAME, true);
        roundsNumber = sharedPreferences.getInt(Key.ROUNDS_NUMBER, 0);
        restsSame = sharedPreferences.getBoolean(Key.RESTS_SAME, true);
    }

    private void setCurrentRest(){
        currentRest = getIntent().getIntExtra(Key.CURRENT_REST, 0);
    }

    private void setHeader(){
        setRestTextViews();

        if (restsSame || roundsNumber == 2) {
            setTextViewsVisibility(View.INVISIBLE);
        }
        else {
            setTextViewsVisibility(View.VISIBLE);
            setCurrentRest();
            restDisplay.setText(String.valueOf(currentRest));
        }
    }

    private void setRestTextViews(){
        restText = findViewById(R.id.restNumberTextView);
        restDisplay = findViewById(R.id.restNumberDisplay);
    }

    private void setTextViewsVisibility(int visibility){
        restText.setVisibility(visibility);
        restDisplay.setVisibility(visibility);
    }



}