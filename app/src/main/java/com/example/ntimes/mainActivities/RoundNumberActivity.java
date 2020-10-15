package com.example.ntimes.mainActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.NumberPicker;

import com.example.ntimes.abstractActivities.ActivityWithPreferences;
import com.example.ntimes.Key;
import com.example.ntimes.R;

public class RoundNumberActivity extends ActivityWithPreferences {

    private NumberPicker roundNumberPicker;
    private CheckBox roundsSameCheckBox;
    private CheckBox restsSameCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_number);

        setUpSharedPreferences();
        setUpRoundNumberPicker();
        setUpCheckBoxes();
    }

    @Override
    protected void onResume() {
        super.onResume();

        roundNumberPicker.setValue(preferences.getInt(Key.ROUNDS_NUMBER, 0));
        restsSameCheckBox.setChecked(preferences.getBoolean(Key.RESTS_SAME, true));
        roundsSameCheckBox.setChecked(preferences.getBoolean(Key.ROUNDS_SAME, true));
    }

    private void setUpRoundNumberPicker(){
        roundNumberPicker = findViewById(R.id.roundsNumberPicker);
        roundNumberPicker.setMaxValue(20);
        roundNumberPicker.setMinValue(1);
    }

    private void setUpCheckBoxes(){
        roundsSameCheckBox = findViewById(R.id.checkBoxRoundsSame);
        restsSameCheckBox = findViewById(R.id.checkBoxRestsSame);
    }

    public void next(View v){
        updatePreferences();

        Intent intent = new Intent(this, RoundActivity.class);
        int value = roundsSameCheckBox.isChecked() ? 0 : 1;
        intent.putExtra(Key.CURRENT_ROUND, value);

        startActivity(intent);
    }

    private void updatePreferences(){
        editor.putInt(Key.ROUNDS_NUMBER, roundNumberPicker.getValue());
        editor.putBoolean(Key.ROUNDS_SAME, roundsSameCheckBox.isChecked());
        editor.putBoolean(Key.RESTS_SAME, restsSameCheckBox.isChecked());

        editor.apply();
    }
}