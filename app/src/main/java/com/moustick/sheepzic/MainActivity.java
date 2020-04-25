package com.moustick.sheepzic;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.moustick.sheepzic.components.ActionButton;
import com.moustick.sheepzic.components.TimePicker;
import com.moustick.sheepzic.components.Timer;
import com.moustick.sheepzic.utils.ColorHelper;
import com.moustick.sheepzic.utils.NetworkUtils;

import java.lang.reflect.InvocationTargetException;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    private Context context;

    private FloatingActionButton playButton;
    private FloatingActionButton resetButton;
    private FloatingActionButton volumeButton;

    private ActionButton airplanemodeButton;
    private ActionButton wifiButton;
    private ActionButton bluetoothButton;
    private ActionButton mobileDataButton;

    private Timer timer;
    private TimePicker timePicker;

    private boolean play;
    private boolean timerOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        /*ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.getAllNetworkInfo()[0].isConnected();*/

        timer = findViewById(R.id.activity_main_timer);
        timePicker = findViewById(R.id.activity_main_timePicker);

        playButton = findViewById(R.id.activity_main_playButton);
        resetButton = findViewById(R.id.activity_main_resetButton);
        volumeButton = findViewById(R.id.activity_main_volumeButton);

        airplanemodeButton = findViewById(R.id.activity_main_actionbutton_airplanemode);
        wifiButton = findViewById(R.id.activity_main_actionbutton_wifi);
        bluetoothButton = findViewById(R.id.activity_main_actionbutton_bluetooth);
        mobileDataButton = findViewById(R.id.activity_main_actionbutton_mobileData);

        timePicker.setOnTimeChangeListener((oldValue, newValue) -> onTimeSelect());

        playButton.setOnClickListener((v) -> onPlayButtonClick());
        resetButton.setOnClickListener((v) -> onResetButtonClick());
        volumeButton.setOnClickListener((v) -> onVolumeButtonClick());

        airplanemodeButton.setOnClickListener((v) -> onAirplanemodeButtonClick());
        wifiButton.setOnClickListener((v -> onWifiButtonClick()));
        bluetoothButton.setOnClickListener((v -> onBluetoothButtonClick()));
        mobileDataButton.setOnClickListener((v -> onMobileDataButtonClick()));

        // View initialisation
        init();

    }

    private void init() {
        enablePlay(false);
        enableReset(false);
        timerOn = false;
        play = false;
    }

    private void onTimeSelect() {
        // Toast.makeText(this, "" + timePicker.getHours() + ":" + timePicker.getMinutes() + ":" + timePicker.getSeconds(), Toast.LENGTH_SHORT).show();
        if (timePicker.hasValue()) {
            enablePlay(true);
            enableReset(true);
        } else {
            enablePlay(false);
            enableReset(false);
        }
    }

    private void onPlayButtonClick() {
        if (timePicker.hasValue()) {
            play = !play;
            if (play) { // Start the count down countDownTimer
                play();
            } else { // Pause the countDownTimer
                pause();
            }
        }
    }

    private void play() {
        // Disable action buttons
        enableActionButtons(false);
        // Enable reset button
        enableReset(true);
        // Display pause button
        setPauseButton();
        // Hide time picker
        timePicker.setVisibility(GONE);
        // Manage timer
        if (!timerOn) { // Start timer if it is off
            startTimer();
        } else { // Pause timer if it was not off yet
            resumeTimer();
        }
    }

    private void pause() {
        // Enable action buttons
        enableActionButtons(true);
        // Display play button
        setEnabledPlayButton();
        // If timer is on, then pause it
        if (timerOn) {
            pauseTimer();
        }
    }

    private void onResetButtonClick() {
        reset();
    }

    private void reset() {
        // Stop timer and reset it
        timer.reset();
        // Reset time picker to zero
        timePicker.setVisibility(View.VISIBLE);
        timePicker.reset();
        // Enable action buttons again
        enableActionButtons(true);
        // Display play button
        setEnabledPlayButton();
        // Enable play button, disable the reset button and set timeOn and play booleans to false
        init();
    }

    private void onVolumeButtonClick() {
        // TODO
    }

    private void startTimer() {
        int hours = timePicker.getHours();
        int minutes = timePicker.getMinutes();
        int seconds = timePicker.getSeconds();
        timer.setTimer(hours, minutes, seconds, this::onTimerFinish);
        timer.start();
        timerOn = true;
    }

    private void pauseTimer() {
        timer.pause();
    }

    private void resumeTimer() {
        timer.resume();
    }

    private void enablePlay(boolean enabled) {
        if (enabled) {
            setEnabledPlayButton();
        } else {
            setDisabledPlayButton();
        }
    }

    private void setDisabledPlayButton() {
        setPlayButtonIcon(R.drawable.ic_play_arrow_normal, R.color.playButtonDisableBackground, R.color.colorActionButtonDeactivateDisabled);
    }

    private void setPauseButton() {
        setPlayButtonIcon(R.drawable.ic_pause_normal, R.color.colorWhite, R.color.colorPrimary);
    }

    private void setEnabledPlayButton() {
        setPlayButtonIcon(R.drawable.ic_play_arrow_normal, R.color.colorPrimary, R.color.colorWhite);
    }

    private void enableReset(boolean enabled) {
        if (enabled) {
            resetButton.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorBlack)));
        } else {
            resetButton.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorLightGrey)));
        }
        resetButton.setEnabled(enabled);
    }

    private void enableActionButtons(boolean enable) {
        airplanemodeButton.enable(enable);
        wifiButton.enable(enable);
        bluetoothButton.enable(enable);
        mobileDataButton.enable(enable);
    }

    private void setButtonIcon(FloatingActionButton button, int iconRef, int iconColorRef, int backgroundColorRef) {
        Drawable drawable = getResources().getDrawable(iconRef);
        button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(iconColorRef)));
        ColorHelper.setColor(context, backgroundColorRef, drawable);
        button.setImageDrawable(drawable);
    }
    private void setPlayButtonIcon(int iconRef, int iconColorRef, int backgroundColorRef) {
        setButtonIcon(playButton, iconRef, iconColorRef, backgroundColorRef);
    }

    private void onAirplanemodeButtonClick() {
        //Settings.Global.putString(getContentResolver(), "airplane_mode_on", "1");
        // Asking for air plane mode deactivation after time out
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
        /*try {
            NetworkUtils.setMobileDataEnabled(context, false);
            Toast.makeText(context, "MobileData OK ;)" , Toast.LENGTH_LONG).show();
            System.out.println("MobileData OK ;)");
        } catch (ClassNotFoundException e) {
            Toast.makeText(context, "MobileData ClassNotFoundException" , Toast.LENGTH_LONG).show();
            System.out.println("MobileData ClassNotFoundException ;)");
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            Toast.makeText(context, "MobileData NoSuchFieldException", Toast.LENGTH_LONG).show();
            System.out.println("MobileData NoSuchFieldException ;)");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Toast.makeText(context, "MobileData IllegalAccessException", Toast.LENGTH_LONG).show();
            System.out.println("MobileData IllegalAccessException ;)");
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            Toast.makeText(context, "MobileData NoSuchMethodException", Toast.LENGTH_LONG).show();
            System.out.println("MobileData NoSuchMethodException ;)");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            Toast.makeText(context, "MobileData InvocationTargetException", Toast.LENGTH_LONG).show();
            System.out.println("MobileData InvocationTargetException ;)");
            e.printStackTrace();
        }*/
        //NetworkUtils.mobileDataEnabled(context, false);
    }

    private void onTimerFinish() {
        if (wifiButton.isSelected())
            NetworkUtils.wifiEnabled(context, false);
        if (mobileDataButton.isSelected())

        if (bluetoothButton.isSelected())
            NetworkUtils.bluetoothEnabled(false);

    }

}
