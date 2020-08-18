package com.example.ntimes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private Editor editor;

    private NumberPicker numberPicker;
    private CheckBox roundsSameCheckBox;
    private CheckBox restsSameCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSharedPreferences();
        setRoundsNumberPicker();
        setCheckBoxes();
    }

    @Override
    protected void onResume() {
        super.onResume();

        numberPicker.setValue(sharedPreferences.getInt(Key.ROUNDS_NUMBER, 0));
        restsSameCheckBox.setChecked(sharedPreferences.getBoolean(Key.RESTS_SAME, true));
        roundsSameCheckBox.setChecked(sharedPreferences.getBoolean(Key.ROUNDS_SAME, true));
    }

    private void setSharedPreferences(){
        sharedPreferences = getSharedPreferences(Key.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void setRoundsNumberPicker(){
        numberPicker = findViewById(R.id.roundsNumberPicker);
        numberPicker.setMaxValue(20);
        numberPicker.setMinValue(1);
    }

    private void setCheckBoxes(){
        roundsSameCheckBox = findViewById(R.id.checkBoxRoundsSame);
        restsSameCheckBox = findViewById(R.id.checkBoxRestsSame);
    }

    public void next(View v){
        int roundsNumber = numberPicker.getValue();
        boolean roundsSame = roundsSameCheckBox.isChecked();
        boolean restsSame = restsSameCheckBox.isChecked();

        editor.putInt(Key.ROUNDS_NUMBER, roundsNumber);
        editor.putBoolean(Key.ROUNDS_SAME, roundsSame);
        editor.putBoolean(Key.RESTS_SAME, restsSame);

        editor.apply();

        Intent intent = new Intent(this, RoundActivity.class);
        if (!roundsSame) intent.putExtra(Key.CURRENT_ROUND, 1);

        startActivity(intent);
    }
}