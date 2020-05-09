package com.moustick.sheepzic;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.moustick.sheepzic.components.TimePicker;
import com.moustick.sheepzic.components.Timer;
import com.moustick.sheepzic.utils.ColorHelper;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    private Context context;

    private FloatingActionButton playButton;
    private FloatingActionButton resetButton;
    private FloatingActionButton volumeButton;

    private Timer timer;
    private TimePicker timePicker;

    private boolean play;
    private boolean timerOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        timer = findViewById(R.id.activity_main_timer);
        timePicker = findViewById(R.id.activity_main_timePicker);

        playButton = findViewById(R.id.activity_main_playButton);
        resetButton = findViewById(R.id.activity_main_resetButton);

        timePicker.setOnTimeChangeListener((oldValue, newValue) -> onTimeSelect());

        playButton.setOnClickListener((v) -> onPlayButtonClick());
        resetButton.setOnClickListener((v) -> onResetButtonClick());

        // View initialisation
        init();

    }

    private void init() {
        displayInitMode();
        timerOn = false;
        play = false;
    }

    private void onTimeSelect() {
        if (timePicker.hasValue()) {
            displayPlayableMode();
        } else {
            displayInitMode();
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
        // Display pausable mode
        displayPausableMode();
        // Manage timer
        if (!timerOn) { // Start timer if it is off
            startTimer();
        } else { // Pause timer if it was not off yet
            resumeTimer();
        }
    }

    private void pause() {
        // Display play button
        displayPlayableMode();
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
        resetTimer();
        timerOn = false;
        // Reset time picker to zero
        timePicker.reset();
        // Reset initial screen
        init();
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

    private void resetTimer() {
        timer.reset();
    }

    private void displayInitMode() {
        displayDisabledPlayButton();
        enableReset(false);
        timePicker.setVisibility(VISIBLE);
        timer.setVisibility(GONE);
    }

    private void displayPlayableMode() {
        displayEnabledPlayButton();
        enableReset(true);
        timePicker.setVisibility(VISIBLE);
        timer.setVisibility(GONE);
    }

    private void displayPausableMode() {
        displayPauseButton();
        enableReset(true);
        timePicker.setVisibility(GONE);
        timer.setVisibility(VISIBLE);
    }

    private void displayDisabledPlayButton() {
        playButton.setEnabled(false);
        setPlayButtonIcon(R.drawable.ic_play_arrow_normal, R.color.colorGrey, R.color.colorLightGrey);
    }

    private void displayEnabledPlayButton() {
        playButton.setEnabled(true);
        setPlayButtonIcon(R.drawable.ic_play_arrow_normal, R.color.colorPrimary, R.color.colorWhite);
    }

    private void displayPauseButton() {
        setPlayButtonIcon(R.drawable.ic_pause_normal, R.color.colorPrimary, R.color.colorWhite);
    }

    private void enableReset(boolean enabled) {
        if (enabled) {
            resetButton.setVisibility(VISIBLE);
            resetButton.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorWhite)));
        } else {
            resetButton.setVisibility(View.GONE);
        }
        resetButton.setEnabled(enabled);
    }

    private void setPlayButtonIcon(int iconRef, int iconColorRef, int backgroundColorRef) {
        Drawable drawable = getResources().getDrawable(iconRef);
        playButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(backgroundColorRef)));
        ColorHelper.setColor(context, iconColorRef, drawable);
        playButton.setImageDrawable(drawable);
    }

    private void onTimerFinish() {
        Toast.makeText(context, "END", Toast.LENGTH_SHORT).show();
        /*if (wifiButton.isSelected())
            NetworkUtils.wifiEnabled(context, false);
        if (bluetoothButton.isSelected())
            NetworkUtils.bluetoothEnabled(false);
        if (mobileDataButton.isSelected()) {} // TODO
        if (airplanemodeButton.isSelected()) {} // TODO*/
    }

}
