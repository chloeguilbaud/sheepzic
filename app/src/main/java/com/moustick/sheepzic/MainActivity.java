package com.moustick.sheepzic;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.moustick.sheepzic.preferences.SettingsUtils;
import com.moustick.sheepzic.preferences.SetupUtils;
import com.moustick.sheepzic.timer.components.TimePicker;
import com.moustick.sheepzic.timer.components.Timer;
import com.moustick.sheepzic.timer.utils.TimeUtils;
import com.moustick.sheepzic.utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.*;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

    private Context context;

    private FloatingActionButton playButton;
    private FloatingActionButton resetButton;
    private FloatingActionButton volumeButton;
    private List<MaterialButton> timerPreferenceButtons;

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

        timerPreferenceButtons = asList(
                findViewById(R.id.activity_main_timerPreferences_button1),
                findViewById(R.id.activity_main_timerPreferences_button2),
                findViewById(R.id.activity_main_timerPreferences_button3),
                findViewById(R.id.activity_main_timerPreferences_button4));

        // Preference initialisation
        try {
            SetupUtils.initSettings(context);
        } catch (RuntimeException e) {
            Log.e("MAIN ACTIVITY", e.getMessage()); //TODO to test
        }

        // View initialisation
        inflateTimerPreferences();
        init();

    }

    private void inflateTimerPreferences() {
        ArrayList<Integer> timerPreferences = SettingsUtils.getTimerPreferences(context);
        for (int i = 0; i < timerPreferenceButtons.size(); i++) {
            long millis = timerPreferences.get(i);
            MaterialButton button = timerPreferenceButtons.get(i);
            button.setText(TimeUtils.format(millis));
            button.setOnClickListener(v -> timePicker.setTime(millis));
        }
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
        displayPausableMode();
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
        enableTimerPreferencesButtons(true);
        timePicker.setVisibility(VISIBLE);
        timer.setVisibility(GONE);
    }

    private void displayPlayableMode() {
        displayEnabledPlayButton();
        enableReset(true);
        enableTimerPreferencesButtons(true);
        timePicker.setVisibility(VISIBLE);
        timer.setVisibility(GONE);
    }

    private void displayPausableMode() {
        if (play)
            displayPauseButton();
        else
            displayEnabledPlayButton();
        enableReset(true);
        enableTimerPreferencesButtons(false);
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
            resetButton.setVisibility(GONE);
        }
        resetButton.setEnabled(enabled);
    }

    private void enableTimerPreferencesButtons(boolean enable) {
        if (enable)
            for (MaterialButton button : timerPreferenceButtons) button.setVisibility(VISIBLE);
        else
            for (MaterialButton button : timerPreferenceButtons) button.setVisibility(INVISIBLE);
    }

    private void setPlayButtonIcon(int iconRef, int iconColorRef, int backgroundColorRef) {
        Drawable drawable = getResources().getDrawable(iconRef);
        playButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(backgroundColorRef)));
        ColorUtils.setColor(context, iconColorRef, drawable);
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
