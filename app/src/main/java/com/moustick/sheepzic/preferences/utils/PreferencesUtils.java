package com.moustick.sheepzic.preferences.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.moustick.sheepzic.utils.SerializeUtils;

import java.util.ArrayList;

public class PreferencesUtils {

    private static final String SHEEPZIC_KEY = "SHEEPZIC_KEY";

    private static final String SHEEPZIC_KEY_TIMER_PREFERENCES = "SHEEPZIC_KEY_TIMER_PREFERENCES";

    private static final String SHEEPZIC_KEY_ON_TIMER_FINISH_TURN_OFF_WIFI = "SHEEPZIC_KEY_ON_TIMER_FINISH_TURN_OFF_WIFI";
    private static final String SHEEPZIC_KEY_ON_TIMER_FINISH_DEACTIVATE_BLUETOOTH = "SHEEPZIC_KEY_ON_TIMER_FINISH_DEACTIVATE_BLUETOOTH";
    private static final String SHEEPZIC_KEY_ON_TIMER_FINISH_TURN_OFF_PHONE = "SHEEPZIC_KEY_ON_TIMER_FINISH_TURN_OFF_PHONE";
    private static final String SHEEPZIC_KEY_ON_TIMER_FINISH_LOCK_PHONE = "SHEEPZIC_KEY_ON_TIMER_FINISH_LOCK_PHONE";

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

    private static void putBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }

    private static Boolean getBoolean(Context context, String key, Boolean defValue) {
        return getSettings(context).getBoolean(key, defValue);
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


    public static boolean getOnTimerFinishTurnOffWifi(Context context) {
        return getBoolean(context, SHEEPZIC_KEY_ON_TIMER_FINISH_TURN_OFF_WIFI, SettingsUtils.DEFAULT_ON_TIMER_FINISH_TURN_OFF_WIFI);
    }

    public static void updateOnTimerFinishTurnOffWifi(Context context, boolean value) {
        putBoolean(context, SHEEPZIC_KEY_ON_TIMER_FINISH_TURN_OFF_WIFI, value);
    }

    public static boolean getOnTimerFinishTurnOffBluetooth(Context context) {
        return getBoolean(context, SHEEPZIC_KEY_ON_TIMER_FINISH_DEACTIVATE_BLUETOOTH, SettingsUtils.DEFAULT_ON_TIMER_FINISH_DEACTIVATE_BLUETOOTH);
    }

    public static void updateOnTimerFinishTurnOffBluetooth(Context context, boolean value) {
        putBoolean(context, SHEEPZIC_KEY_ON_TIMER_FINISH_DEACTIVATE_BLUETOOTH, value);
    }

    public static boolean getOnTimerFinishTurnOffPhone(Context context) {
        return getBoolean(context, SHEEPZIC_KEY_ON_TIMER_FINISH_TURN_OFF_PHONE, SettingsUtils.DEFAULT_ON_TIMER_FINISH_TURN_OFF_PHONE);
    }

    public static void updateOnTimerFinishTurnOffPhone(Context context, boolean value) {
        putBoolean(context, SHEEPZIC_KEY_ON_TIMER_FINISH_TURN_OFF_PHONE, value);
    }

    public static boolean getOnTimerFinishLockPhone(Context context) {
        return getBoolean(context, SHEEPZIC_KEY_ON_TIMER_FINISH_LOCK_PHONE, SettingsUtils.DEFAULT_ON_TIMER_FINISH_LOCK_PHONE);
    }

    public static void updateOnTimerFinishLockPhone(Context context, boolean value) {
        putBoolean(context, SHEEPZIC_KEY_ON_TIMER_FINISH_LOCK_PHONE, value);
    }

}
