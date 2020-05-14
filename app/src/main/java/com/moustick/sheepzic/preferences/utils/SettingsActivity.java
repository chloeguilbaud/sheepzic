package com.moustick.sheepzic.preferences.utils;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.moustick.sheepzic.R;
import com.moustick.sheepzic.preferences.components.Switch;
import com.moustick.sheepzic.preferences.ui.PreferencesUtils;

public class SettingsActivity extends AppCompatActivity {

    private Context context;

    private Switch deactivateWifiSwitch;
    private Switch deactivateBluetoothSwitch;
    private Switch phoneLockSwitch;
    private Switch phoneOffSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        context = getApplicationContext();

        deactivateWifiSwitch = findViewById(R.id.activity_settings_switch_whenTimerEnded_turnOffWifi);
        deactivateBluetoothSwitch = findViewById(R.id.activity_settings_switch_whenTimerEnded_turnOffbluetooth);
        phoneLockSwitch = findViewById(R.id.activity_settings_switch_whenTimerEnded_lockPhone);
        phoneOffSwitch = findViewById(R.id.activity_settings_switch_whenTimerEnded_turnOffPhone);

        // Save the state in the sharedPreferences on switch change
        // TODO - make switch spinner
        deactivateWifiSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                PreferencesUtils.updateOnTimerFinishTurnOffWifi(context, isChecked));
        deactivateBluetoothSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                PreferencesUtils.updateOnTimerFinishTurnOffBluetooth(context, isChecked));
        phoneLockSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                PreferencesUtils.updateOnTimerFinishLockPhone(context, isChecked));
        phoneOffSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                PreferencesUtils.updateOnTimerFinishTurnOffPhone(context, isChecked));

        // Init view according to saved preferences
        deactivateWifiSwitch.setChecked(PreferencesUtils.getOnTimerFinishTurnOffWifi(context));
        deactivateBluetoothSwitch.setChecked(PreferencesUtils.getOnTimerFinishTurnOffBluetooth(context));
        phoneLockSwitch.setChecked(PreferencesUtils.getOnTimerFinishLockPhone(context));
        phoneOffSwitch.setChecked(PreferencesUtils.getOnTimerFinishTurnOffPhone(context));

    }
}
