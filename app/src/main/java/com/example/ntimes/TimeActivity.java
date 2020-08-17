package com.example.ntimes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TimeActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    private TextView roundTextView;
    private TextView roundDisplay;

    private TextView exerciseTextView;
    private TextView exerciseDisplay;

    private TextView remainingDisplay;
    private TextView remainingTextView;

    private Button timeButton;

    private int exerciseNumber; // for the same rounds

    private CountDownTimer currentCountDown;
    private int currentCountDownId;
    private long remain;

    private final int DELAY_BEFORE_TRAINING = 11; // seconds
    private final List<Integer> times = new ArrayList<>();
    private int timesSize;

    private MediaPlayer startExerciseMusic;
    private MediaPlayer stopExerciseMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        setSharedPreferences();
        setViews();
        setMusic();
        setRunningTimers();
    }

    private void setSharedPreferences(){
        sharedPreferences = getSharedPreferences(Key.SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    private void setViews(){
        roundTextView = findViewById(R.id.textRound);
        roundDisplay = findViewById(R.id.roundDisplay);
        exerciseTextView = findViewById(R.id.textExercise);
        exerciseDisplay = findViewById(R.id.exerciseDisplay);
        remainingDisplay = findViewById(R.id.remainingDisplay);
        remainingTextView = findViewById(R.id.remaining);
        timeButton = findViewById(R.id.timerButton);
    }

    private void setMusic(){
        startExerciseMusic = MediaPlayer.create(this, R.raw.start);
        stopExerciseMusic = MediaPlayer.create(this, R.raw.stop);
    }

    private void setRunningTimers(){
        boolean roundsSame = sharedPreferences.getBoolean(Key.ROUNDS_SAME, true);
        if (roundsSame) runWithSameRounds();
        else runWithDifferentRounds();
    }

    private void runWithSameRounds() {
        exerciseNumber = sharedPreferences.getInt(Key.EXERCISES_NUMBER, 0);
        int roundsNumber = sharedPreferences.getInt(Key.ROUNDS_NUMBER, 0);
        int exerciseTime = sharedPreferences.getInt(Key.EXERCISE_TIME, 0) *  1000;
        int exerciseRestTime = sharedPreferences.getInt(Key.EXERCISES_REST_TIME, 0) *  1000;
        int roundRestTime = sharedPreferences.getInt(Key.ROUNDS_REST_TIME, 0) *  1000;

        times.add(DELAY_BEFORE_TRAINING * 1000);

        for (int i = 1; i <= roundsNumber; i++){
            roundDisplay.setText(String.valueOf(i));
            for (int j = 1; j < exerciseNumber; j++){
                times.add(exerciseTime);
                times.add(exerciseRestTime);
            }
            times.add(exerciseTime);
            times.add(roundRestTime);
        }

        times.remove(times.size() - 1);
        timesSize = times.size();
        startTimer(0);
    }

    private void startTimer(int id){
        if (id >= timesSize) return;
        else if (id == 0) // delay before training starts
            setRoundVisibility(View.INVISIBLE);
        else if (id % (2 * exerciseNumber) == 0) { // if round rest
            setRoundVisibility(View.INVISIBLE);
        }
        else{
            setRoundVisibility(View.VISIBLE);
            roundDisplay.setText(String.valueOf(id / (2 * exerciseNumber) + 1));
            if (id % 2 == 1) {  // if exercise
                setExerciseVisibility(View.VISIBLE);
                exerciseDisplay.setText(String.valueOf((id % (2 * exerciseNumber)) / 2 + 1));
            }
            else
                setExerciseVisibility(View.INVISIBLE);
        }
        makeTimer(times.get(id), id).start();
    }

    private void runWithDifferentRounds(){
        // TODO
    }

    private void setRoundVisibility(int visibility){
        roundTextView.setVisibility(visibility);
        roundDisplay.setVisibility(visibility);
        exerciseTextView.setVisibility(visibility);
        exerciseDisplay.setVisibility(visibility);
    }

    private void setExerciseVisibility(int visibility){
        exerciseTextView.setVisibility(visibility);
        exerciseDisplay.setVisibility(visibility);
    }

    private CountDownTimer makeTimer(int millisInFuture, int id){
        return new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long l) {
                currentCountDown = this;
                currentCountDownId = id;
                if (id == 0)  // delay before training starts
                    remainingTextView.setText(R.string.before_training);
                else if (id % 2 == 1) // if exercise
                    remainingTextView.setText(R.string.exercise_remain);
                else
                    remainingTextView.setText(R.string.rest_remain);

                remainingDisplay.setText(String.valueOf(l / 1000));
                remain = l;
            }
            @Override
            public void onFinish() {
                if (id == timesSize - 1){ // if the last one
                    setRoundVisibility(View.INVISIBLE);
                    remainingTextView.setText(R.string.finish);
                    remainingDisplay.setText("");
                    setFinishedButton();
                }
                if (id % 2 == 1)  // if exercise
                    stopExerciseMusic.start();
                else
                    startExerciseMusic.start();
                startTimer(id + 1);
            }
        };
    }

    private void setFinishedButton(){
        String oneMoreTimeColor = "#FF9800";
        timeButton.setBackgroundColor(Color.parseColor(oneMoreTimeColor));
        timeButton.setText(R.string.again);
    }

    private void pauseCountdown(){
        currentCountDown.cancel();
        currentCountDown = makeTimer((int)remain, currentCountDownId);
    }

    public void handleTimeButtonClick(View v){
        String pauseColor = "#FF5722";
        String resumeColor = "#8BC34A";

        String value = timeButton.getText().toString();

        if (value.equals(getString(R.string.resume))){
            timeButton.setText(R.string.pause);
            timeButton.setBackgroundColor(Color.parseColor(pauseColor));
            currentCountDown.start();
        }
        else if (value.equals(getString(R.string.pause))){
            timeButton.setText(R.string.resume);
            timeButton.setBackgroundColor(Color.parseColor(resumeColor));
            pauseCountdown();
        }
        else if(value.equals(getString(R.string.again))){
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}