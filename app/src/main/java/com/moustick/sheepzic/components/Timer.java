package com.moustick.sheepzic.components;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.moustick.sheepzic.R;
import com.moustick.sheepzic.utils.SuiteHelper;

// TODO - mettre en place du ViewModel et LiveData
public class Timer extends LinearLayout {

    private TextView hoursValueView;
    private TextView minutesValueView;
    private TextView secondsValueView;

    private ProgressBar hoursProgressBarView;
    private ProgressBar minutesProgressBarView;
    private ProgressBar secondsProgressBarView;

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

    private void create(AttributeSet attrs) {

        // View initialisation
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.component_timer, this);

        hoursValueView = findViewById(R.id.component_countDownTimer_hours);
        minutesValueView = findViewById(R.id.component_countDownTimer_minutes);
        secondsValueView = findViewById(R.id.component_countDownTimer_seconds);

        hoursProgressBarView = findViewById(R.id.component_countDownTimer_progressbar_hours);
        minutesProgressBarView = findViewById(R.id.component_countDownTimer_progressbar_minutes);
        secondsProgressBarView = findViewById(R.id.component_countDownTimer_progressbar_seconds);

        // Time initialisation
        hours = SuiteHelper.generate(minHour, maxHour);
        minutes = SuiteHelper.generate(minMinute, maxMinute);
        seconds = SuiteHelper.generate(minSecond, maxSecond);

        init();

    }

    private void updateView() {
        updateTimerValueView();
        updateProgressBarView(currentHour, currentMinute, currentSecond);
    }

    private void updateTimerValueView() {
        hoursValueView.setText(hours[currentHour]);
        minutesValueView.setText(minutes[currentMinute]);
        secondsValueView.setText(seconds[currentSecond]);
    }

    private void updateProgressBarView(int hour, int minute, int second) {
        hoursProgressBarView.setProgress(hour);
        minutesProgressBarView.setProgress(minute);
        secondsProgressBarView.setProgress(second);
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
        initProgressBarView();
        updateTimerValueView();
    }

    private void initProgressBarView() {
        updateProgressBarView(1, 1, 1);
        //hoursProgressBarView.setBackground(getResources().getDrawable(R.drawable.ic_component_timer_progressbarbackgroundhours, getContext().getTheme()));
        //minutesProgressBarView.setBackground(getResources().getDrawable(R.drawable.ic_component_timer_progressbarbackgroundminutes, getContext().getTheme()));
        secondsProgressBarView.setBackground(getResources().getDrawable(R.drawable.ic_component_timer_progressbarbackgroundseconds, getContext().getTheme()));
    }

    private void setProgressBarsBackgroundColor(ProgressBar progressBar, boolean enableBackground) {
        if (enableBackground)
            progressBar.setBackground(getResources().getDrawable(R.drawable.ic_component_timer_progressbarbackgroundseconds, getContext().getTheme()));
        else
            progressBar.setBackground(null);

    }

    public void reset() {
        finish();
        init();
    }

    public interface onFinishListener {
        void onFinish();
    }
}
