package xenoteo.com.github.ntimes.mainActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import xenoteo.com.github.ntimes.abstractActivities.ActivitySettingUpBasicDataFromPreferences;
import xenoteo.com.github.ntimes.Key;
import com.github.ntimes.R;

public class RestActivity extends ActivitySettingUpBasicDataFromPreferences {

    private NumberPicker restMinPicker;
    private NumberPicker restSecPicker;

    private TextView restNumberText;
    private TextView restNumberDisplay;

    private int currentRest;
    private String currentRestString;   // empty string if rests are the same

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);

        setUpSharedPreferences();
        setUpTimePickers();
        setUpDataFromPreferences();
        setUpHeader();
    }

    @Override
    protected void onResume() {
        super.onResume();

        setUpDataFromPreferences();
        setUpCurrentRest();
        restMinPicker.setValue(preferences
                .getInt(Key.ROUNDS_REST_TIME + currentRestString, 0) / 60);
        restSecPicker.setValue(preferences
                .getInt(Key.ROUNDS_REST_TIME + currentRestString, 0) % 60);
    }

    private void setUpTimePickers(){
        restSecPicker = findViewById(R.id.roundRestSecPicker);
        restMinPicker = findViewById(R.id.roundRestMinPicker);

        restMinPicker.setMaxValue(60);
        restMinPicker.setMinValue(0);

        restSecPicker.setMaxValue(59);
        restSecPicker.setMinValue(0);
    }

    private void setUpCurrentRest(){
        currentRest = getIntent().getIntExtra(Key.CURRENT_REST, 0);
        currentRestString = currentRest == 0 ? "" : Integer.toString(currentRest);
    }

    private void setUpRestTextViews(){
        restNumberText = findViewById(R.id.restNumberTextView);
        restNumberDisplay = findViewById(R.id.restNumberDisplay);
    }

    private void setUpHeader(){
        setUpRestTextViews();

        if (restsSame || roundsNumber == 2) {
            setTextViewsVisibility(View.INVISIBLE);
        }
        else {
            setTextViewsVisibility(View.VISIBLE);
            setUpCurrentRest();
            restNumberDisplay.setText(currentRestString);
        }
    }

    private void setTextViewsVisibility(int visibility){
        restNumberText.setVisibility(visibility);
        restNumberDisplay.setVisibility(visibility);
    }

    public void next(View v){
        int restTime = restMinPicker.getValue() * 60 + restSecPicker.getValue();    // in seconds

        editor.putInt(Key.ROUNDS_REST_TIME + currentRestString, restTime);
        editor.apply();

        if (restsSame || currentRest == (roundsNumber - 1))
            startActivity(new Intent(this, CheckActivity.class));
        else {
            Intent intent = new Intent(this, RestActivity.class);
            intent.putExtra(Key.CURRENT_REST, currentRest + 1);
            startActivity(intent);
        }
    }

    public void back(View v){
        super.onBackPressed();
    }
}