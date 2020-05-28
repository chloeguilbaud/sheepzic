package com.moustick.sheepzic.utils.device.network;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;

public class NetworkUtils {

    public static boolean turnOffWifi(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            wifiManager.setWifiEnabled(false);
            return true;
        } else {
            return false;
        }
    }

    public static boolean turnOffBluetooth() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        return adapter.disable();
    }

}
