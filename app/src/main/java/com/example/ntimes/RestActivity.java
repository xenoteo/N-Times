package com.example.ntimes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

public class RestActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private NumberPicker restMinPicker;
    private NumberPicker restSecPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);

        setSharedPreferences();
        setTimePickers();
    }

    @Override
    protected void onResume() {
        super.onResume();

        restMinPicker.setValue(sharedPreferences.getInt(Key.ROUNDS_REST_TIME, 0) / 60);
        restSecPicker.setValue(sharedPreferences.getInt(Key.ROUNDS_REST_TIME, 0) % 60);

    }

    private void setSharedPreferences(){
        sharedPreferences = getSharedPreferences(Key.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void setTimePickers(){
        restSecPicker = (NumberPicker) findViewById(R.id.roundRestSecPicker);
        restMinPicker = (NumberPicker) findViewById(R.id.roundRestMinPicker);

        restMinPicker.setMaxValue(60);
        restMinPicker.setMinValue(0);

        restSecPicker.setMaxValue(59);
        restSecPicker.setMinValue(0);
    }

    public void next(View v){
        int restTime = restMinPicker.getValue() * 60 + restSecPicker.getValue();    // in seconds

        editor.putInt(Key.ROUNDS_REST_TIME, restTime);
        editor.apply();

        startActivity(new Intent(this, CheckActivity.class));
    }

    public void back(View v){
        startActivity(new Intent(this, RoundActivity.class));
    }

}