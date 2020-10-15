package com.example.ntimes.mainActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ntimes.R;
import com.example.ntimes.mainActivities.timeActivity.TimeActivity;

public class CheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
    }

    public void edit(View v){   // TODO rename reset, add back

        startActivity(new Intent(this, RoundNumberActivity.class));
    }

    public void start(View v){
        startActivity(new Intent(this, TimeActivity.class));
    }
}