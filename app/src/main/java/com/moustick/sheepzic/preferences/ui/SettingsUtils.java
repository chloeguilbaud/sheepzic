package com.moustick.sheepzic.preferences.ui;

import android.content.Context;

import com.moustick.sheepzic.timer.utils.TimeUtils;

import java.util.ArrayList;

public class SettingsUtils {

    public static final boolean DEFAULT_ON_TIMER_FINISH_TURN_OFF_WIFI = false;
    public static final boolean DEFAULT_ON_TIMER_FINISH_DEACTIVATE_BLUETOOTH = false;
    public static final boolean DEFAULT_ON_TIMER_FINISH_TURN_OFF_PHONE = false;
    public static final boolean DEFAULT_ON_TIMER_FINISH_LOCK_PHONE = false;

    public static void initSettings(Context context) throws RuntimeException {

        // Timer suggestions
        ArrayList<Integer> timePreferences = PreferencesUtils.getTimerPreferences(context);
        if (timePreferences.isEmpty()) {
            timePreferences.add(TimeUtils.toMillis(0, 20, 0));
            timePreferences.add(TimeUtils.toMillis(0, 30, 0));
            timePreferences.add(TimeUtils.toMillis(0, 45, 0));
            timePreferences.add(TimeUtils.toMillis(1, 0, 0));
            PreferencesUtils.updateTimerPreferences(context, timePreferences);
        } else if (timePreferences.size() != 4) {
            throw new RuntimeException("Error in app default timePreferences settings");
        }

    }

}
