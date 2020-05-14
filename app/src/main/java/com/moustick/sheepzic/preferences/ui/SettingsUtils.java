package com.moustick.sheepzic.preferences.ui;

import android.content.Context;
import android.content.SharedPreferences;

import com.moustick.sheepzic.utils.SerializeUtils;

import java.util.ArrayList;

public class SettingsUtils {

    private static final String SHEEPZIC_KEY = "SHEEPZIC_KEY";

    private static final String SHEEPZIC_KEY_TIMER_PREFERENCES = "SHEEPZIC_KEY_TIMER_PREFERENCES";

    private static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences(SHEEPZIC_KEY, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getSettings(context).edit();
    }

    private static SharedPreferences getSettings(Context context) {
        return getSharedPreference(context);
    }

    private static long getLong(Context context, String key, long defValue) {
        return getSettings(context).getLong(key, defValue);
    }

    private static void putLong(Context context, String key, long value) {
        getEditor(context).putLong(key, value).commit();
    }

    private static String getString(Context context, String key, String defValue) {
        return getSettings(context).getString(key, defValue);
    }

    private static void putString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();
    }

    private static ArrayList<String> getList(Context context, String key, ArrayList<String> defValue) {
        return SerializeUtils.deserializeList(getSettings(context).getString(key, SerializeUtils.serialize(defValue)));
    }

    private static void putIntegerList(Context context, String key, ArrayList<Integer> value) {
        getEditor(context).putString(key, SerializeUtils.serialize(value)).commit();
    }

    private static void remove(Context context, String key) {
        getEditor(context).remove(key).commit();
    }

    // PUBLICS METHODS

    public static void updateTimerPreferences(Context context, ArrayList<Integer> timerPreferences) {
        putIntegerList(context, SHEEPZIC_KEY_TIMER_PREFERENCES, timerPreferences);
    }

    public static ArrayList<Integer> getTimerPreferences(Context context) {
        ArrayList<String> timePref = getList(context, SHEEPZIC_KEY_TIMER_PREFERENCES, new ArrayList<>());
        ArrayList<Integer> tmp = new ArrayList<>();
        for (String str : timePref)
            tmp.add(Integer.parseInt(str));
        return tmp;
    }


}
