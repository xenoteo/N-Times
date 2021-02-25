package xenoteo.com.github.ntimes.mainActivities.timeActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import xenoteo.com.github.ntimes.abstractActivities.ActivityWithPreferences;
import xenoteo.com.github.ntimes.Key;
import com.github.ntimes.R;
import xenoteo.com.github.ntimes.mainActivities.RoundNumberActivity;

import java.util.ArrayList;
import java.util.List;

public class TimeActivity extends ActivityWithPreferences {

    private TextView roundTextView;
    private TextView roundDisplay;

    private TextView exerciseTextView;
    private TextView exerciseDisplay;

    private TextView remainingDisplay;
    private TextView remainingTextView;

    private Button timeButton;
    private Button stopAndBackButton;
    private TimeButtonState buttonState;

    private MediaPlayer startExerciseMusic;
    private MediaPlayer stopExerciseMusic;

    private CountDownTimer currentCountDown;
    private int currentCountDownId;
    private long timeRemaining;

    private int roundsNumber;
    private Timer timer;
    private final int DELAY_BEFORE_TRAINING = 11; // seconds
    private final List<Integer> times = new ArrayList<>();
    private int timesSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        setUpSharedPreferences();
        setUpViews();
        setUpMusic();
        setUpAndStartTimer();
    }

    private void setUpViews(){
        roundTextView = findViewById(R.id.textRound);
        roundDisplay = findViewById(R.id.roundDisplay);
        exerciseTextView = findViewById(R.id.textExercise);
        exerciseDisplay = findViewById(R.id.exerciseDisplay);
        remainingDisplay = findViewById(R.id.remainingDisplay);
        remainingTextView = findViewById(R.id.remaining);
        timeButton = findViewById(R.id.timerButton);
        stopAndBackButton = findViewById(R.id.stopAndBackButton);
        buttonState = TimeButtonState.PAUSE;
    }

    private void setUpMusic(){
        startExerciseMusic = MediaPlayer.create(this, R.raw.start);
        stopExerciseMusic = MediaPlayer.create(this, R.raw.stop);
    }

    private void setUpAndStartTimer(){
        boolean roundsSame = preferences.getBoolean(Key.ROUNDS_SAME, true);
        if (roundsSame)
            timer = new SameRoundsTimer();
        else
            timer = new DifferentRoundTimer();
        timer.fillTimes();
        timer.updateScreenAndStartTimer(0);
    }

    private void setUpRoundsNumber(){
        roundsNumber = preferences.getInt(Key.ROUNDS_NUMBER, 0);
    }

    private List<Integer> getRoundRestTimes(){
        List<Integer> rests = new ArrayList<>();
        boolean restsSame = preferences.getBoolean(Key.RESTS_SAME, true);
        if (restsSame){
            int rest = preferences.getInt(Key.ROUNDS_REST_TIME, 0) * 1000;
            for (int i = 0; i < roundsNumber - 1; i++)
                rests.add(rest);
        }
        else {
            for (int i = 1; i <= roundsNumber - 1; i++){
                rests.add(preferences.getInt(Key.ROUNDS_REST_TIME + i, 0) * 1000);
            }
        }
        return rests;
    }

    private String getTimeString(long s){
        long min = s / 60;
        if (min == 0)
            return s + " s";
        else
            return min + " min " + (s % 60) + " s";
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

    private void pauseCountdown(){
        currentCountDown.cancel();
        currentCountDown = makeTimer((int) timeRemaining, currentCountDownId);
    }

    private void setUpFinishScreen(){
        setRoundVisibility(View.INVISIBLE);
        remainingTextView.setText(R.string.finish);
        remainingDisplay.setText("");
        setUpFinishButton();
        stopAndBackButton.setVisibility(View.INVISIBLE);
    }


    private void setUpFinishButton(){
        buttonState = TimeButtonState.ONE_MORE_TIME;
        timeButton.setBackgroundColor(getResources().getColor(R.color.one_more_time));
        timeButton.setText(R.string.again);
    }

    private CountDownTimer makeTimer(int millisInFuture, int id) {
        return new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long l) {
                currentCountDown = this;
                currentCountDownId = id;
                if (id == 0)            // delay before training starts
                    remainingTextView.setText(R.string.before_training);
                else if (id % 2 == 1)   // if exercise
                    remainingTextView.setText(R.string.exercise_remain);
                else
                    remainingTextView.setText(R.string.rest_remain);

                remainingDisplay.setText(getTimeString(l / 1000));
                timeRemaining = l;
            }
            @Override
            public void onFinish() {
                if (id == timesSize - 1){ // if the last one
                    setUpFinishScreen();
                }
                if (id % 2 == 1)  // if exercise
                    stopExerciseMusic.start();
                else
                    startExerciseMusic.start();
                timer.updateScreenAndStartTimer(id + 1);
            }
        };
    }

    public void handleTimeButtonClick(View v){
        switch (buttonState){
            case RESUME:
                buttonState = TimeButtonState.PAUSE;
                timeButton.setText(R.string.pause);
                timeButton.setBackgroundColor(getResources().getColor(R.color.pause));
                currentCountDown.start();
                break;
            case PAUSE:
                buttonState = TimeButtonState.RESUME;
                timeButton.setText(R.string.resume);
                timeButton.setBackgroundColor(getResources().getColor(R.color.resume));
                pauseCountdown();
                break;
            case ONE_MORE_TIME:
                startActivity(new Intent(this, RoundNumberActivity.class));
                buttonState = TimeButtonState.PAUSE;
        }
    }

    public void back(View v){
        pauseCountdown();
        createStopTrainingAlert().show();
    }

    private AlertDialog createStopTrainingAlert(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage(R.string.stop_training_alert)
                .setCancelable(false)
                .setNegativeButton(R.string.yes, (dialog, id) -> onBackPressed())
                .setPositiveButton(R.string.cancel, (dialog, id) -> currentCountDown.start())
                .create();

        alertDialog.setOnShowListener(arg0 -> {
            Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            negativeButton.setBackgroundColor(getResources().getColor(R.color.white));
            negativeButton.setTextColor(getResources().getColor(R.color.yes));
            negativeButton.setLeft(View.FOCUS_LEFT);

            Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setBackgroundColor(getResources().getColor(R.color.white));
            positiveButton.setTextColor(getResources().getColor(R.color.resume));
        });
        return alertDialog;
    }



    private class SameRoundsTimer implements Timer {
        private int exerciseNumber;

        @Override
        public void fillTimes() {
            setUpRoundsNumber();
            setUpExerciseNumber();
            int exerciseTime = preferences.getInt(Key.EXERCISE_TIME, 0) *  1000;
            int exerciseRestTime = preferences.getInt(Key.EXERCISES_REST_TIME, 0) *  1000;

            List<Integer> roundRestTimes = getRoundRestTimes();

            times.add(DELAY_BEFORE_TRAINING * 1000);

            for (int i = 0; i < roundsNumber; i++){
                for (int j = 1; j < exerciseNumber; j++){
                    times.add(exerciseTime);
                    times.add(exerciseRestTime);
                }
                times.add(exerciseTime);
                if (i < roundsNumber - 1) times.add(roundRestTimes.get(i));
            }

            timesSize = times.size();
        }

        private void setUpExerciseNumber(){
            exerciseNumber = preferences.getInt(Key.EXERCISES_NUMBER, 0);
        }

        @Override
        public void updateScreenAndStartTimer(int id) {
            if (id >= timesSize) return;
            else if (id == 0)                           // delay before training starts
                setRoundVisibility(View.INVISIBLE);
            else if (id % (2 * exerciseNumber) == 0) { // if round rest
                setRoundVisibility(View.INVISIBLE);
            }
            else{
                setRoundVisibility(View.VISIBLE);
                roundDisplay.setText(String.valueOf(id / (2 * exerciseNumber) + 1));
                if (id % 2 == 1) {                      // if exercise
                    setExerciseVisibility(View.VISIBLE);
                    exerciseDisplay.setText(String.valueOf((id % (2 * exerciseNumber)) / 2 + 1));
                }
                else
                    setExerciseVisibility(View.INVISIBLE);
            }
            makeTimer(times.get(id), id).start();
        }
    }

    private class DifferentRoundTimer implements Timer{
        private final List<Integer> exerciseNumbers = new ArrayList<>();
        private final List<Integer> roundRestsIds = new ArrayList<>();
        private int currentRoundNumber;
        private int currentExerciseNumber;

        @Override
        public void fillTimes() {
            setUpRoundsNumber();
            setUpExerciseNumbers();
            List<Integer> exerciseTimes = getTimes(Key.EXERCISE_TIME);
            List<Integer> exerciseRestTimes = getTimes(Key.EXERCISES_REST_TIME);
            List<Integer> roundRestTimes = getRoundRestTimes();

            times.add(DELAY_BEFORE_TRAINING * 1000);

            for (int i = 0; i < roundsNumber; i++){
                int exerciseTime = exerciseTimes.get(i);
                int exerciseRestTime = exerciseRestTimes.get(i);
                for (int k = 0; k < exerciseNumbers.get(i) - 1; k++){
                    times.add(exerciseTime);
                    times.add(exerciseRestTime);
                }
                times.add(exerciseTime);
                if (i < roundRestTimes.size()) {
                    times.add(roundRestTimes.get(i));
                    roundRestsIds.add(times.size() - 1);
                }
            }

            currentExerciseNumber = 1;
            currentRoundNumber = 1;
            timesSize = times.size();
        }

        private void setUpExerciseNumbers(){
            for (int i = 1; i <= roundsNumber; i++)
                exerciseNumbers.add(preferences.getInt(Key.EXERCISES_NUMBER + i, 0));
        }

        private List<Integer> getTimes(String key){
            List<Integer> times = new ArrayList<>();
            for (int i = 1; i <= roundsNumber; i++)
                times.add(preferences.getInt(key + i, 0) * 1000);
            return times;
        }

        @Override
        public void updateScreenAndStartTimer(int id) {
            if (id >= timesSize) return;
            else if (id == 0)                       // delay before training starts
                setRoundVisibility(View.INVISIBLE);
            else if (roundRestsIds.contains(id)) {  // if round rest
                setRoundVisibility(View.INVISIBLE);
                currentRoundNumber++;
                currentExerciseNumber = 1;
            }
            else{
                setRoundVisibility(View.VISIBLE);
                roundDisplay.setText(String.valueOf(currentRoundNumber));
                if (id % 2 == 1) {                    // if exercise
                    setExerciseVisibility(View.VISIBLE);
                    exerciseDisplay.setText(String.valueOf(currentExerciseNumber));
                }
                else {
                    setExerciseVisibility(View.INVISIBLE);
                    currentExerciseNumber++;
                }
            }
            makeTimer(times.get(id), id).start();
        }
    }
}