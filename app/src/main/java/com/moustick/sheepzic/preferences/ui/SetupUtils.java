package com.moustick.sheepzic.preferences.ui;

import android.content.Context;

import com.moustick.sheepzic.timer.utils.TimeUtils;

import java.util.ArrayList;

public class SetupUtils {

    public static void initSettings(Context context) throws RuntimeException {
        ArrayList<Integer> timePreferences = SettingsUtils.getTimerPreferences(context);
        if (timePreferences.isEmpty()) {
            timePreferences.add(TimeUtils.toMillis(0, 20, 0));
            timePreferences.add(TimeUtils.toMillis(0, 30, 0));
            timePreferences.add(TimeUtils.toMillis(0, 45, 0));
            timePreferences.add(TimeUtils.toMillis(1, 0, 0));
            SettingsUtils.updateTimerPreferences(context, timePreferences);
        } else if (timePreferences.size() != 4) {
            throw new RuntimeException("Error in app default timePreferences settings");
        }
    }

}
