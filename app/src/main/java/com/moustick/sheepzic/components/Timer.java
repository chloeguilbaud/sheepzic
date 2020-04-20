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
    private onFinishListener currentOnFinishListener;

    private CountDownTimer countDownTimer;

    public Timer(Context context) {
        super(context);
        create(null);
    }

    public Timer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        create(attrs);
    }

    public Timer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        create(attrs);
    }

    public Timer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        create(attrs);
    }

    private void create(AttributeSet attrs) {

        // View initialisation
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.component_count_down_timer, this);

        hoursView = findViewById(R.id.component_countDownTimer_hours);
        minutesView = findViewById(R.id.component_countDownTimer_minutes);
        secondsView = findViewById(R.id.component_countDownTimer_seconds);

        // Time initialisation
        hours = SuiteHelper.generate(minHour, maxHour);
        minutes = SuiteHelper.generate(minMinute, maxMinute);
        seconds = SuiteHelper.generate(minSecond, maxSecond);

        init();

    }

    private void updateView() {
        hoursView.setText(hours[currentHour]);
        minutesView.setText(minutes[currentMinute]);
        secondsView.setText(seconds[currentSecond]);
    }

    public void setTimer() {
        setTimer(currentHour, currentMinute, currentSecond, currentOnFinishListener);
    }

    public void setTimer(final int hour, final int minute, final int second, final onFinishListener onFinishListener) {

        currentHour = hour;
        currentMinute = minute;
        currentSecond = second;
        currentOnFinishListener = onFinishListener;
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
                onFinishListener.onFinish();
            }
        };
    }

    public void start() {
        if (countDownTimer != null) {
            countDownTimer.start();
        }
    }

    public void pause() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    public void resume() {
        if (countDownTimer != null) {
            setTimer();
            start();
        }
    }

    public void finish() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void init() {
        currentHour = minHour;
        currentMinute = minMinute;
        currentSecond = minSecond;
        updateView();
    }

    public void reset() {
        finish();
        init();
    }

    public interface onFinishListener {
        void onFinish();
    }
}
