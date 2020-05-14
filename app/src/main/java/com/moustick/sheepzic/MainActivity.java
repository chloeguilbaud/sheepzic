package com.moustick.sheepzic;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.moustick.sheepzic.preferences.ui.PreferencesUtils;
import com.moustick.sheepzic.preferences.ui.SettingsUtils;
import com.moustick.sheepzic.preferences.utils.SettingsActivity;
import com.moustick.sheepzic.timer.components.TimePicker;
import com.moustick.sheepzic.timer.components.Timer;
import com.moustick.sheepzic.timer.utils.TimeUtils;
import com.moustick.sheepzic.utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

    private Context context;

    private FloatingActionButton resetButton;
    private FloatingActionButton playButton;
    private FloatingActionButton stopButton;
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

        // Getting view elements

        ImageButton paramsButton = findViewById(R.id.activity_main_params);

        timer = findViewById(R.id.activity_main_timer);
        timePicker = findViewById(R.id.activity_main_timePicker);

        resetButton = findViewById(R.id.activity_main_resetButton);
        playButton = findViewById(R.id.activity_main_playButton);
        stopButton = findViewById(R.id.activity_main_stopButton);


        // Reacting to user actions

        paramsButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, SettingsActivity.class);
            startActivity(intent);
        });

        timePicker.setOnTimeChangeListener((oldValue, newValue) -> onTimeSelect());

        resetButton.setOnClickListener((v) -> onResetButtonClick());
        playButton.setOnClickListener((v) -> onPlayButtonClick());
        stopButton.setOnClickListener((v) -> onStopButtonClick());

        timerPreferenceButtons = asList(
                findViewById(R.id.activity_main_timerPreferences_button1),
                findViewById(R.id.activity_main_timerPreferences_button2),
                findViewById(R.id.activity_main_timerPreferences_button3),
                findViewById(R.id.activity_main_timerPreferences_button4));


        // Preference initialisation
        try {
            SettingsUtils.initSettings(context);
        } catch (RuntimeException e) {
            Log.e("MAIN ACTIVITY", e.getMessage()); //TODO to test
        }

        // View initialisation
        inflateTimerPreferences();
        initSecondaryButtons();

        init();

    }


    /****************************
     *      Initialisation
     ****************************/

    private void inflateTimerPreferences() {
        ArrayList<Integer> timerPreferences = PreferencesUtils.getTimerPreferences(context);
        for (int i = 0; i < timerPreferenceButtons.size(); i++) {
            long millis = timerPreferences.get(i);
            MaterialButton button = timerPreferenceButtons.get(i);
            button.setText(TimeUtils.format(millis));
            button.setOnClickListener(v -> timePicker.setTime(millis));
        }
    }

    private void initSecondaryButtons() {
        resetButton.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorWhite)));
        stopButton.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorWhite)));
    }

    private void init() {
        displayInitialView();
        timerOn = false;
        play = false;
    }


    /****************************
     *      User actions
     ****************************/

    private void onTimeSelect() {
        if (timePicker.hasValue()) {
            displayPlayableView();
        } else {
            displayInitialView();
        }
    }

    private void onResetButtonClick() {
        reset();
    }

    private void onPlayButtonClick() {
        if (timePicker.hasValue()) {
            if (!play) { // Start the count down countDownTimer
                play();
            } else { // Pause the countDownTimer
                pause();
            }
            play = !play;
        }
    }

    private void onStopButtonClick() {
        if (play) {
            stop();
        }
    }


    /****************************
     *          Actions
     ****************************/

    private void play() {
        // Display pausable mode
        displayPlayingView();
        // Manage timer
        if (!timerOn) { // Start timer if it is off
            startTimer();
        } else { // Pause timer if it was not off yet
            resumeTimer();
        }
    }

    private void pause() {
        // Display play button
        displayPlayingView();
        // If timer is on, then pause it
        if (timerOn) {
            pauseTimer();
        }
    }

    private void reset() {
        // Stop timer and reset it
        resetTimer();
        // Reset time picker to zero
        timePicker.reset();
        // Reset initial screen
        init();
    }

    private void stop() {
        // Pause timer
        stopTimer();
        timerOn = false;
        play = false;
        // Display playable mode
        displayPlayableView();
    }


    /****************************
     *      Timer actions
     ****************************/

    private void startTimer() {
        setTimer();
        timer.start();
        timerOn = true;
    }

    private void setTimer() {
        int hours = timePicker.getHours();
        int minutes = timePicker.getMinutes();
        int seconds = timePicker.getSeconds();
        timer.setTimer(hours, minutes, seconds, this::onTimerFinish);
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

    private void stopTimer() {
        timer.stop();
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


    /****************************
     *      Display views
     ****************************/

    private void displayInitialView() {
        displayPlayButton(false);
        displayResetButton(false);
        displayStopButton(false);
        displayTimerPreferencesButtons(true);
        timePicker.setVisibility(VISIBLE);
        timer.setVisibility(GONE);
    }

    private void displayPlayableView() {
        displayPlayButton(true);
        displayResetButton(true);
        displayStopButton(false);
        displayTimerPreferencesButtons(true);
        timePicker.setVisibility(VISIBLE);
        timer.setVisibility(GONE);
    }

    private void displayPlayingView() {
        if (play)
            displayPauseButton();
        else
            displayPlayButton(true);
        displayResetButton(true);
        displayStopButton(true);
        displayTimerPreferencesButtons(false);
        timePicker.setVisibility(GONE);
        timer.setVisibility(VISIBLE);
    }

    private void displayPlayButton(boolean enable) {
        playButton.setEnabled(enable);
        if (!enable)
            setPlayButtonIcon(R.drawable.ic_play_arrow_normal, R.color.colorGrey, R.color.colorGreyLight);
        else
            setPlayButtonIcon(R.drawable.ic_play_arrow_normal, R.color.colorPrimary, R.color.colorWhite);
    }

    private void displayPauseButton() {
        setPlayButtonIcon(R.drawable.ic_pause_normal, R.color.colorPrimary, R.color.colorWhite);
    }

    private void displayResetButton(boolean enable) {
        displaySecondaryButton(resetButton, enable);
    }

    private void displayStopButton(boolean enable) {
        displaySecondaryButton(stopButton, enable);
    }

    private void displaySecondaryButton(FloatingActionButton button, boolean enable) {
        if (enable) {
            button.setVisibility(VISIBLE);
        } else {
            button.setVisibility(GONE);
        }
        button.setEnabled(enable);
    }

    private void displayTimerPreferencesButtons(boolean enable) {
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

}
