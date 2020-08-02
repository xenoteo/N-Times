package com.example.ntimes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimeActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    private TextView roundTextView;
    private TextView exerciseTextView;
    private TextView remainingDisplay;
    private TextView remainingTextView;
    private Button timeButton;

    private CountDownTimer currentCountDown;
    private long remain;
    private boolean isExercise;

    private MediaPlayer startExercise;
    private MediaPlayer stopExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        setSharedPreferences();
        setViews();
        setMusic();
        startTimer();
    }

    private void setSharedPreferences(){
        sharedPreferences = getSharedPreferences(Key.SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    private void setViews(){
        roundTextView = (TextView)findViewById(R.id.roundDisplay);
        exerciseTextView = (TextView)findViewById(R.id.exerciseDisplay);
        remainingDisplay = (TextView)findViewById(R.id.remainingDisplay);
        remainingTextView = (TextView)findViewById(R.id.remaining);
        timeButton = (Button)findViewById(R.id.timerButton);
    }

    private void setMusic(){
        startExercise = MediaPlayer.create(this, R.raw.start);
        stopExercise = MediaPlayer.create(this, R.raw.stop);
    }

    private void startTimer(){
        boolean roundsSame = sharedPreferences.getBoolean(Key.ROUNDS_SAME, true);
        if (roundsSame) makeTimerWithSameRounds();
        else makeTimerWithDifferentRounds();
    }

    private void makeTimerWithSameRounds(){
        currentCountDown = makeTimer(10000);
        isExercise = true;
        /*
        int roundsNumber = sharedPreferences.getInt(Key.ROUNDS_NUMBER, 0);
        int exerciseNumber = sharedPreferences.getInt(Key.EXERCISES_NUMBER, 0);
        int exerciseTime = sharedPreferences.getInt(Key.EXERCISE_TIME, 0) *  1000;
        int exerciseRestTime = sharedPreferences.getInt(Key.EXERCISE_TIME, 0) *  1000;
        int roundRestTime = sharedPreferences.getInt(Key.EXERCISE_TIME, 0) *  1000;
        for (int i = 1; i <= roundsNumber; i++){
            roundTextView.setText(String.valueOf(i));
            for (int j = 1; j <= exerciseNumber; j++){
                exerciseTextView.setText(String.valueOf(j));
                isExercise = true;
                currentCountDown = makeTimer(exerciseTime).start();
                isExercise = false;
                currentCountDown = makeTimer(exerciseRestTime).start();
            }
            currentCountDown = makeTimer(roundRestTime).start();
        }
         */
    }

    private void makeTimerWithDifferentRounds(){
        // TODO
    }

    private CountDownTimer makeTimer(int millisInFuture){
        return new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long l) {
                if (isExercise){
                    remainingTextView.setText(R.string.exercise_remain);
                }
                else {
                    remainingTextView.setText(R.string.rest_remain);
                }
                remainingDisplay.setText(String.valueOf(l / 1000));
                remain = l;
            }
            @Override
            public void onFinish() {
                if (isExercise) stopExercise.start();
                else startExercise.start();
            }
        };
    }

    public void handleTimeButtonClick(View v){
        String pauseColor = "#FF5722";
        String resumeColor = "#8BC34A";

        String value = timeButton.getText().toString();

        if (value.equals(getString(R.string.start)) || value.equals(getString(R.string.resume))){
            timeButton.setText(R.string.pause);
            timeButton.setBackgroundColor(Color.parseColor(pauseColor));
            currentCountDown.start();
        }
        else if (value.equals(getString(R.string.pause))){
            timeButton.setText(R.string.resume);
            timeButton.setBackgroundColor(Color.parseColor(resumeColor));
            currentCountDown.cancel();
            currentCountDown = makeTimer((int) remain);
        }
    }


}