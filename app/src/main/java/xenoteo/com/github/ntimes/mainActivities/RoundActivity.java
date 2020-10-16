package xenoteo.com.github.ntimes.mainActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import xenoteo.com.github.ntimes.abstractActivities.ActivitySettingUpBasicDataFromPreferences;
import xenoteo.com.github.ntimes.Key;
import com.github.ntimes.R;

public class RoundActivity extends ActivitySettingUpBasicDataFromPreferences {

    private NumberPicker exercisesNumberPicker;
    private NumberPicker exerciseMinPicker;
    private NumberPicker exerciseSecPicker;
    private NumberPicker restMinPicker;
    private NumberPicker restSecPicker;

    private TextView roundText;
    private TextView roundDisplay;

    private String currentRoundString;  // empty string if rounds are the same
    private int currentRound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        setUpSharedPreferences();
        setUpExercisesNumberPicker();
        setUpTimePickers();
        setUpDataFromPreferences();
        setUpHeader();
    }

    @Override
    protected void onResume() {
        super.onResume();

        setUpCurrentRound();
        exercisesNumberPicker.setValue(preferences
                .getInt(Key.EXERCISES_NUMBER + currentRoundString, 0));
        exerciseMinPicker.setValue(preferences
                .getInt(Key.EXERCISE_TIME + currentRoundString, 0) / 60);
        exerciseSecPicker.setValue(preferences
                .getInt(Key.EXERCISE_TIME + currentRoundString, 0) % 60);
        restMinPicker.setValue(preferences
                .getInt(Key.EXERCISES_REST_TIME + currentRoundString, 0) / 60);
        restSecPicker.setValue(preferences
                .getInt(Key.EXERCISES_REST_TIME + currentRoundString, 0) % 60);

    }

    private void setUpExercisesNumberPicker(){
        exercisesNumberPicker = findViewById(R.id.exercisesNumberPicker);
        exercisesNumberPicker.setMaxValue(30);
        exercisesNumberPicker.setMinValue(1);
    }

    private void setUpTimePickers(){
        exerciseMinPicker = findViewById(R.id.exercisesMinPicker);
        exerciseSecPicker = findViewById(R.id.exercisesSecPicker);
        restMinPicker = findViewById(R.id.roundRestSecPicker);
        restSecPicker = findViewById(R.id.roundRestMinPicker);

        exerciseMinPicker.setMaxValue(60);
        exerciseMinPicker.setMinValue(0);

        exerciseSecPicker.setMaxValue(59);
        exerciseSecPicker.setMinValue(0);

        restMinPicker.setMaxValue(60);
        restMinPicker.setMinValue(0);

        restSecPicker.setMaxValue(59);
        restSecPicker.setMinValue(0);
    }

    private void setUpCurrentRound(){
        currentRound = getIntent().getIntExtra(Key.CURRENT_ROUND, 0);
        currentRoundString = currentRound == 0 ? "" : Integer.toString(currentRound);
    }

    private void setUpRoundTextViews(){
        roundText = findViewById(R.id.roundNumberTextView);
        roundDisplay = findViewById(R.id.roundNumberDisplay);
    }

    public void setUpHeader(){
        setUpRoundTextViews();

        if (roundsSame) {
            setTextViewsVisibility(View.INVISIBLE);
        }
        else {
            setTextViewsVisibility(View.VISIBLE);
            setUpCurrentRound();
            roundDisplay.setText(currentRoundString);
        }
    }

    private void setTextViewsVisibility(int visibility){
        roundText.setVisibility(visibility);
        roundDisplay.setVisibility(visibility);
    }

    public void next(View v){
        int exercisesNumber = exercisesNumberPicker.getValue();
        int exerciseTime = exerciseMinPicker.getValue() * 60 + exerciseSecPicker.getValue();
        int restTime = restMinPicker.getValue() * 60 + restSecPicker.getValue();    // in seconds

        editor.putInt(Key.EXERCISES_NUMBER + currentRoundString, exercisesNumber);
        editor.putInt(Key.EXERCISE_TIME + currentRoundString, exerciseTime);
        editor.putInt(Key.EXERCISES_REST_TIME + currentRoundString, restTime);
        editor.apply();

        if (!roundsSame && currentRound < roundsNumber) {
            Intent intent = new Intent(this, RoundActivity.class);
            intent.putExtra(Key.CURRENT_ROUND, currentRound + 1);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, RestActivity.class);
            int value = restsSame ? 0 : 1;
            intent.putExtra(Key.CURRENT_REST, value);
            startActivity(intent);
        }
    }

    public void back(View v){
        super.onBackPressed();
    }
}