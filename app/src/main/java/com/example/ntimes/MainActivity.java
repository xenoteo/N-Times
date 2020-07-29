package com.example.ntimes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.NumberPicker;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private Editor editor;
    private NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSharedPreferences();
        setRoundsNumberPicker();
    }

    private void setSharedPreferences(){
        sharedPreferences = getSharedPreferences(Key.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void setRoundsNumberPicker(){
        numberPicker = (NumberPicker)findViewById(R.id.roundsNumberPicker);
        numberPicker.setMaxValue(20);
        numberPicker.setMinValue(1);
    }

    public void next(View v){
        int roundsNumber = numberPicker.getValue();
        boolean roundsSame = ((CheckBox)findViewById(R.id.checkBoxRoundsSame)).isChecked();
        boolean restsSame = ((CheckBox)findViewById(R.id.checkBoxRestsSame)).isChecked();

        editor.putInt(Key.ROUNDS_NUMBER, roundsNumber);
        editor.putBoolean(Key.ROUNDS_SAME, roundsSame);
        editor.putBoolean(Key.RESTS_SAME, restsSame);

        editor.apply();

        startActivity(new Intent(this, RoundActivity.class));
    }
}