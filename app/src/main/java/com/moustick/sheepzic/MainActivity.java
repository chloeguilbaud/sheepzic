package com.moustick.sheepzic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.moustick.sheepzic.components.ActionButton;

public class MainActivity extends AppCompatActivity {

    private ActionButton airplanemodeButton;
    private ActionButton wifiButton;
    private ActionButton bluetoothButton;
    private ActionButton mobileDataButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        airplanemodeButton = findViewById(R.id.activity_main_actionbutton_airplanemode);
        wifiButton = findViewById(R.id.activity_main_actionbutton_wifi);
        bluetoothButton = findViewById(R.id.activity_main_actionbutton_bluetooth);
        mobileDataButton = findViewById(R.id.activity_main_actionbutton_mobileData);

        airplanemodeButton.setOnClickListener((v) -> onAirplacemodeButtonClick());
        wifiButton.setOnClickListener((v -> onWifiButtonClick()));
        bluetoothButton.setOnClickListener((v -> onBluetoothButtonClick()));
        mobileDataButton.setOnClickListener((v -> onMobileDataButtonClick()));

    }

    private void onAirplacemodeButtonClick() {
        if(!airplanemodeButton.isSelected()) {
            airplanemodeButton.select(true);
            wifiButton.select(true);
            bluetoothButton.select(true);
            mobileDataButton.select(true);
        } else {
            airplanemodeButton.select(false);
        }
    }

    private void onWifiButtonClick() {
        wifiButton.select();
    }

    private void onBluetoothButtonClick() {
        bluetoothButton.select();
    }

    private void onMobileDataButtonClick() {
        mobileDataButton.select();
    }

}
