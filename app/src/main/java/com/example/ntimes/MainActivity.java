package com.example.ntimes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void next(View v){
        String roundsNumberString = ((EditText)findViewById(R.id.roundsNumber)).getText().toString();
        if (roundsNumberString.isEmpty()){
            Toast.makeText(
                    getApplicationContext(),
                    "Enter number of rounds",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        int roundsNumber = Integer.parseInt(roundsNumberString);
        boolean roundsSame = ((CheckBox)findViewById(R.id.checkBoxRoundsSame)).isChecked();
        Intent intent = new Intent(this, RoundActivity.class);
        intent.putExtra("roundsNumber", roundsNumber);
        intent.putExtra("roundsSame", roundsSame);
        Toast.makeText(getApplicationContext(), roundsNumber + " rounds", Toast.LENGTH_LONG).show();    // test
        startActivity(intent);
    }
}