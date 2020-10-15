package com.example.ntimes.abstractActivities;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ntimes.Key;

public abstract class ActivityWithPreferences extends AppCompatActivity {
    protected SharedPreferences preferences;
    protected SharedPreferences.Editor editor;

    protected void setUpSharedPreferences(){
        preferences = getSharedPreferences(Key.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

}
