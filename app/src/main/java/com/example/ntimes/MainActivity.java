package com.example.ntimes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRoundsNumberPicker();
    }

    private void setRoundsNumberPicker(){
        numberPicker = (NumberPicker)findViewById(R.id.roundsNumberPicker);
        numberPicker.setMaxValue(20);
        numberPicker.setMinValue(1);
    }

    public void next(View v){
        int roundsNumber = numberPicker.getValue();
        boolean roundsSame = ((CheckBox)findViewById(R.id.checkBoxRoundsSame)).isChecked();
        Intent intent = new Intent(this, RoundActivity.class);
        intent.putExtra(Key.ROUNDS_NUMBER, roundsNumber);
        intent.putExtra(Key.ROUNDS_SAME, roundsSame);
        Toast.makeText(getApplicationContext(), roundsNumber + " rounds", Toast.LENGTH_LONG).show();    // test
        startActivity(intent);
    }
}