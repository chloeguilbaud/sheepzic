package com.moustick.sheepzic.utils.device.lock;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.moustick.sheepzic.R;

/**
 * Created by ssaurel on 04/09/2017.
 */
public class DeviceLockAdminReceiver extends DeviceAdminReceiver {

    @Override
    public void onEnabled(Context context, Intent intent) {
        Toast.makeText(context, R.string.settings_lockDevice_rights_enabled, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        Toast.makeText(context, R.string.settings_lockDevice_rights_disabled, Toast.LENGTH_LONG).show();
    }

}
