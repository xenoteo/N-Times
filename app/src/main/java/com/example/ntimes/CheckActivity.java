package com.example.ntimes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
    }

    public void edit(View v){
        startActivity(new Intent(this, RoundNumberActivity.class));
    }

    public void start(View v){
        startActivity(new Intent(this, TimeActivity.class));
    }
}