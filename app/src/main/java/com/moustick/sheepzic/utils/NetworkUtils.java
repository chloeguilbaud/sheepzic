package com.moustick.sheepzic.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;

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

    public static boolean bluetoothEnabled(boolean enabled) {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (enabled) {
            return adapter.enable();
        } else {
            return adapter.disable();
        }
    }

}
