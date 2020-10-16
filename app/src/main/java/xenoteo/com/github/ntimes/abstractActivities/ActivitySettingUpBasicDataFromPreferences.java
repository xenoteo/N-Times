package xenoteo.com.github.ntimes.abstractActivities;

import xenoteo.com.github.ntimes.Key;

public abstract class ActivitySettingUpBasicDataFromPreferences extends ActivityWithPreferences {
    protected boolean roundsSame;
    protected boolean restsSame;
    protected int roundsNumber;

    protected void setUpDataFromPreferences(){
        roundsSame = preferences.getBoolean(Key.ROUNDS_SAME, true);
        roundsNumber = preferences.getInt(Key.ROUNDS_NUMBER, 0);
        restsSame = preferences.getBoolean(Key.RESTS_SAME, true);
    }
}
