package com.moustick.sheepzic.preferences.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.moustick.sheepzic.R;
import com.moustick.sheepzic.preferences.components.Switch;
import com.moustick.sheepzic.preferences.utils.PreferencesUtils;
import com.moustick.sheepzic.utils.device.lock.DeviceLockAdminManager;

public class SettingsActivity extends AppCompatActivity {

    private Context context;

    private static final int DEVICE_LOCK_RIGHTS = 11;
    private DeviceLockAdminManager deviceLockAdminManager;

    private MaterialToolbar toolbar;

    private Switch deactivateWifiSwitch;
    private Switch deactivateBluetoothSwitch;
    private Switch deviceLockSwitch;
    private Switch deviceOffSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        context = getApplicationContext();

        deviceLockAdminManager = new DeviceLockAdminManager(context);

        toolbar = findViewById(R.id.activity_settings_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        deactivateWifiSwitch = findViewById(R.id.activity_settings_switch_whenTimerEnded_turnOffWifi);
        deactivateBluetoothSwitch = findViewById(R.id.activity_settings_switch_whenTimerEnded_turnOffbluetooth);
        deviceLockSwitch = findViewById(R.id.activity_settings_switch_whenTimerEnded_lockDevice);
        deviceOffSwitch = findViewById(R.id.activity_settings_switch_whenTimerEnded_turnOffDevice);

        // Save the state in the sharedPreferences on switch change
        // TODO - make switch spinner
        deactivateWifiSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                PreferencesUtils.updateOnTimerFinishTurnOffWifi(context, isChecked));
        deactivateBluetoothSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                PreferencesUtils.updateOnTimerFinishTurnOffBluetooth(context, isChecked));
        deviceLockSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                onDeviceLockSwitched(isChecked));
        deviceOffSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                PreferencesUtils.updateOnTimerFinishTurnOffDevice(context, isChecked));

        // Init view according to saved preferences
        deactivateWifiSwitch.setChecked(PreferencesUtils.getOnTimerFinishTurnOffWifi(context));
        deactivateBluetoothSwitch.setChecked(PreferencesUtils.getOnTimerFinishTurnOffBluetooth(context));
        deviceLockSwitch.setChecked(PreferencesUtils.getOnTimerFinishLockDevice(context));
        deviceOffSwitch.setChecked(PreferencesUtils.getOnTimerFinishTurnOffDevice(context));

    }

    private void onDeviceLockSwitched(boolean isChecked) {
        if (isChecked && !deviceLockAdminManager.isDeviceLockRightsActive()) {
            startActivityForResult(deviceLockAdminManager.enableDeviceLockRights(), DEVICE_LOCK_RIGHTS);
        } else {
            deviceLockAdminManager.disableDeviceLockRights();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case DEVICE_LOCK_RIGHTS:
                if (resultCode == Activity.RESULT_OK) {
                    deviceLockSwitch.check();
                } else {
                    deviceLockSwitch.uncheck();
                    Toast.makeText(this, R.string.settings_lockDevice_rights_canceled, Toast.LENGTH_LONG).show();
                }
                PreferencesUtils.updateOnTimerFinishLockDevice(context, deviceLockAdminManager.isDeviceLockRightsActive());
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
