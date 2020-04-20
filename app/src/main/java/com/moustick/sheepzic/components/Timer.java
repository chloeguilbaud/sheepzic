package com.moustick.sheepzic.components;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.moustick.sheepzic.R;
import com.moustick.sheepzic.utils.SuiteHelper;

public class Timer extends LinearLayout {

    private TextView hoursView;
    private TextView minutesView;
    private TextView secondsView;

    public static final int minMinute = 0;
    public static final int minSecond = 0;
    public static final int minHour = 0;

    public static final int maxMinute = 60;
    public static final int maxSecond = 60;
    public static final int maxHour = 100;

    private String[] hours;
    private String[] minutes;
    private String[] seconds;

    private int currentHour;
    private int currentMinute;
    private int currentSecond;
    private onFinishEventHandler currentOnFinishEventHandler;

    private CountDownTimer countDownTimer;

    public Timer(Context context) {
        super(context);
        init(null);
    }

    public Timer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public Timer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public Timer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        // View initialisation
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.component_count_down_timer, this);

        hoursView = findViewById(R.id.component_countDownTimer_hours);
        minutesView = findViewById(R.id.component_countDownTimer_minutes);
        secondsView = findViewById(R.id.component_countDownTimer_seconds);

        // Time initialisation
        currentHour = minHour;
        currentMinute = minMinute;
        currentSecond = minSecond;

        hours = SuiteHelper.generate(minHour, maxHour);
        minutes = SuiteHelper.generate(minMinute, maxMinute);
        seconds = SuiteHelper.generate(minSecond, maxSecond);

        updateView();

    }

    private void updateView() {
        hoursView.setText(hours[currentHour]);
        minutesView.setText(minutes[currentMinute]);
        secondsView.setText(seconds[currentSecond]);
    }

    public void setTimer() {
        setTimer(currentHour, currentMinute, currentSecond, currentOnFinishEventHandler);
    }

    public void setTimer(final int hour, final int minute, final int second, final onFinishEventHandler onFinishEventHandler) {

        currentHour = hour;
        currentMinute = minute;
        currentSecond = second;
        currentOnFinishEventHandler = onFinishEventHandler;
        updateView();

        int countDownTimeMillis = currentSecond * 1000 + currentMinute * 60 * 1000 + currentHour * 60 * 60 * 1000;

        // Creating Timer
        countDownTimer = new CountDownTimer(countDownTimeMillis, 1000) {

            public void onTick(long millisUntilFinished) { // Every second
                currentSecond = (int) (millisUntilFinished / 1000) % 60;
                currentMinute = (int) ((millisUntilFinished / (1000 * 60)) % 60);
                currentHour = (int) ((millisUntilFinished / (1000 * 60 * 60)) % 24);
                updateView();
            }

            public void onFinish() {
                onFinishEventHandler.handleFinish();
            }
        };
    }

    public void start() {
        if (countDownTimer != null) {
            countDownTimer.start();
        } else {
            throw new RuntimeException("Timer must be initialised with it's setTimer() method before started.");
        }
    }

    public void pause() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        } else {
            throw new RuntimeException("Do you seriously think you can pause a Timer that you haven't initialised with it's setTimer() method?");
        }
    }

    public void resume() {
        if (countDownTimer != null) {
            setTimer();
            start();
        } else {
            throw new RuntimeException("Do you seriously think you can resume a Timer that you haven't even initialised? Please create it thanks to it's setTimer() method before doing anything else to this poor thing.");
        }
    }

    public void finish() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        } else {
            throw new RuntimeException("Do you seriously think you can stop something that has't started ? Please create the Timer thanks to it's setTimer() method before doing anything else to him.");
        }
    }

    public interface onFinishEventHandler {
        void handleFinish();
    }
}
