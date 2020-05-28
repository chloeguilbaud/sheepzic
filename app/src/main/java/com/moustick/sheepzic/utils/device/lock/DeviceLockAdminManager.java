package com.moustick.sheepzic.utils.device.lock;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.moustick.sheepzic.R;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.DEVICE_POLICY_SERVICE;

public class DeviceLockAdminManager {

    private Context context;

    private DevicePolicyManager devicePolicyManager;
    private ActivityManager activityManager;
    private ComponentName compName;

    public DeviceLockAdminManager(Context context) {
        this.context = context;
        this.devicePolicyManager = (DevicePolicyManager) context.getSystemService(DEVICE_POLICY_SERVICE);
        this.activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        this.compName = new ComponentName(context, DeviceLockAdminReceiver.class);
    }

    public Intent enableDeviceLockRights() {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, compName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, R.string.settings_lockDevice_rights);
        return intent;
    }

    public void disableDeviceLockRights() {
        devicePolicyManager.removeActiveAdmin(compName);
    }

    public boolean lockDevice() {
        boolean active = isDeviceLockRightsActive();
        if (active) {
            devicePolicyManager.lockNow();
        }
        return active;
    }

    public boolean isDeviceLockRightsActive() {
        return this.devicePolicyManager.isAdminActive(compName);
    }

    /*public static boolean turnOffDevice() {

    }*/

}
