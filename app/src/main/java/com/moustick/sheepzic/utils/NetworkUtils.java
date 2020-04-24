package com.moustick.sheepzic.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NetworkUtils {

    public static boolean wifiEnabled(Context context, boolean enable) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            wifiManager.setWifiEnabled(false);
            return true;
        } else {
            return false;
        }
    }
    public static boolean mobileDataEnabled(Context context, boolean enabled) {
        Toast.makeText(context, "MSG", Toast.LENGTH_LONG).show();
        boolean res = false;
        try {
            final ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            final Class mClass = Class.forName(mConnectivityManager.getClass().getName());
            final Field mField = mClass.getDeclaredField("mService");
            mField.setAccessible(true);
            final Object mObject = mField.get(mConnectivityManager);
            final Class mConnectivityManagerClass = Class.forName(mObject.getClass().getName());
            final Method setMobileDataEnabledMethod = mConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            setMobileDataEnabledMethod.setAccessible(true);

            setMobileDataEnabledMethod.invoke(mObject, enabled);
            res = true;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static boolean bluetoothEnabled(boolean enabled) {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (enabled) {
            return adapter.enable();
        } else {
            return adapter.disable();
        }
    }

}
