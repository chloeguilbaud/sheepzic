package com.moustick.sheepzic.timer.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;

import com.moustick.sheepzic.R;
import com.moustick.sheepzic.timer.utils.TimeUtils;
import com.moustick.sheepzic.utils.data.SuiteUtils;

import static com.moustick.sheepzic.timer.components.Timer.maxHour;
import static com.moustick.sheepzic.timer.components.Timer.maxMinute;
import static com.moustick.sheepzic.timer.components.Timer.maxSecond;
import static com.moustick.sheepzic.timer.components.Timer.minHour;
import static com.moustick.sheepzic.timer.components.Timer.minMinute;
import static com.moustick.sheepzic.timer.components.Timer.minSecond;

public class TimePicker extends LinearLayout {

    private NumberPicker numberPickerHour;
    private NumberPicker numberPickerMin;
    private NumberPicker numberPickerSec;

    private OnTimeChangeListener onTimeChangeListener;

    public TimePicker(Context context) {
        super(context);
    }

    public TimePicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        create(attrs);
    }

    public TimePicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        create(attrs);
    }

    private void create(final @Nullable AttributeSet attrs) {

        // View initialisation
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.component_timer_timepicker, this);

        numberPickerHour = findViewById(R.id.component_timePicker_numberPicker_hours);
        numberPickerMin = findViewById(R.id.component_timePicker_numberPicker_minutes);
        numberPickerSec = findViewById(R.id.component_timePicker_numberPicker_seconds);

        init();

    }

    private void init() {
        initNumberPicker(numberPickerHour, minHour, maxHour);
        initNumberPicker(numberPickerMin, minMinute, maxMinute);
        initNumberPicker(numberPickerSec, minSecond, maxSecond);
    }

    private void initNumberPicker(NumberPicker numberPicker, int min, int max) {
        numberPicker.setDisplayedValues(SuiteUtils.generate(min, max));
        numberPicker.setMinValue(min);
        numberPicker.setMaxValue(max - 1);
        numberPicker.setValue(min);
    }

    public void reset() {
        init();
    }

    public boolean hasValue() {
        return getHours() != 0 || getMinutes() != 0 || getSeconds() != 0;
    }

    public void setOnTimeChangeListener(OnTimeChangeListener onTimeChangeListener) {
        this.onTimeChangeListener = onTimeChangeListener;
        numberPickerHour.setOnValueChangedListener((picker, oldVal, newVal) -> onTimeChangeListener.onTimeChange(oldVal, newVal));
        numberPickerMin.setOnValueChangedListener((picker, oldVal, newVal) -> onTimeChangeListener.onTimeChange(oldVal, newVal));
        numberPickerSec.setOnValueChangedListener((picker, oldVal, newVal) -> onTimeChangeListener.onTimeChange(oldVal, newVal));
    }

    public int getSeconds() {
        return numberPickerSec.getValue();
    }

    public int getMinutes() {
        return numberPickerMin.getValue();
    }

    public int getHours() {
        return numberPickerHour.getValue();
    }

    public void setSeconds(int second) {
        onTimeChangeListener.onTimeChange(getSeconds(), second);
        numberPickerSec.setValue(second);
    }

    public void setMinutes(int minute) {
        onTimeChangeListener.onTimeChange(getMinutes(), minute);
        numberPickerMin.setValue(minute);
    }

    public void setHours(int hour) {
        onTimeChangeListener.onTimeChange(getHours(), hour);
        numberPickerHour.setValue(hour);
    }

    public void setTime(long millis) {
        setHours(TimeUtils.toHours(millis));
        setMinutes(TimeUtils.toMinutes(millis));
        setSeconds(TimeUtils.toSeconds(millis));
    }

    public interface OnTimeChangeListener {
        void onTimeChange(int oldValue, int newValue);
    }

}
