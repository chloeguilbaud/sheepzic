package com.moustick.sheepzic;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.moustick.sheepzic.components.ActionButton;
import com.moustick.sheepzic.utils.ColorHelper;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton playButton;
    private boolean play = false;

    private FloatingActionButton resetButton;
    private FloatingActionButton volumeButton;

    private ActionButton airplanemodeButton;
    private ActionButton wifiButton;
    private ActionButton bluetoothButton;
    private ActionButton mobileDataButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playButton = findViewById(R.id.activity_main_playButton);
        resetButton = findViewById(R.id.activity_main_resetButton);
        volumeButton = findViewById(R.id.activity_main_volumeButton);

        airplanemodeButton = findViewById(R.id.activity_main_actionbutton_airplanemode);
        wifiButton = findViewById(R.id.activity_main_actionbutton_wifi);
        bluetoothButton = findViewById(R.id.activity_main_actionbutton_bluetooth);
        mobileDataButton = findViewById(R.id.activity_main_actionbutton_mobileData);

        playButton.setOnClickListener((v) -> onPlayButtonClick()); // TODO - disable if noting selected
        resetButton.setOnClickListener((v) -> onResetButtonClick());
        volumeButton.setOnClickListener((v) -> onVolumeButtonClick());

        airplanemodeButton.setOnClickListener((v) -> onAirplanemodeButtonClick());
        wifiButton.setOnClickListener((v -> onWifiButtonClick()));
        bluetoothButton.setOnClickListener((v -> onBluetoothButtonClick()));
        mobileDataButton.setOnClickListener((v -> onMobileDataButtonClick()));

        // View initialisation
        enableReset(false);

    }

    private void onPlayButtonClick() {
        play = !play;
        if (play) {
            setPlayButtonIcon(R.drawable.ic_pause_normal, R.color.colorWhite, R.color.colorPrimary);
            enableActionButtons(false);
            enableReset(true);
        } else {
            setPlayButtonIcon(R.drawable.ic_play_arrow_normal, R.color.colorPrimary, R.color.colorWhite);
            enableActionButtons(true);
        }
    }

    private void enableReset(final boolean enabled) {
        if (enabled) {
            resetButton.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorBlack)));
        } else {
            resetButton.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorLightGrey)));
        }
        resetButton.setEnabled(enabled);
    }

    private void onResetButtonClick() {
        // TODO - canReset :
        //          - on TimePicking
        //          - on playing
        /*if (resetButton.isEnabled()) {
            // TODO reset timePiker + clock
            enableReset(false);
        }*/
    }

    private void onVolumeButtonClick() {
        // TODO
    }

    private void enableActionButtons(boolean enable) {
        airplanemodeButton.enable(enable);
        wifiButton.enable(enable);
        bluetoothButton.enable(enable);
        mobileDataButton.enable(enable);
    }

    private void setPlayButtonIcon(int iconRef, int iconColorRef, int backgroundColorRef) {
        Drawable drawable = getResources().getDrawable(iconRef);
        playButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(iconColorRef)));
        ColorHelper.setColor(getApplicationContext(), backgroundColorRef, drawable);
        playButton.setImageDrawable(drawable);
    }

    private void onAirplanemodeButtonClick() {
        if (!airplanemodeButton.isSelected()) {
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
