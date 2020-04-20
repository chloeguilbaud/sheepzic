package com.moustick.sheepzic.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;

import com.moustick.sheepzic.R;
import com.moustick.sheepzic.utils.SuiteHelper;

import static com.moustick.sheepzic.components.Timer.maxHour;
import static com.moustick.sheepzic.components.Timer.maxMinute;
import static com.moustick.sheepzic.components.Timer.maxSecond;
import static com.moustick.sheepzic.components.Timer.minHour;
import static com.moustick.sheepzic.components.Timer.minMinute;
import static com.moustick.sheepzic.components.Timer.minSecond;

public class TimePicker extends LinearLayout {

    private NumberPicker numberPickerHour;
    private NumberPicker numberPickerMin;
    private NumberPicker numberPickerSec;

    public TimePicker(Context context) {
        super(context);
    }

    public TimePicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TimePicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public TimePicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(final @Nullable AttributeSet attrs) {

        // View initialisation
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.component_time_picker, this);

        numberPickerHour = findViewById(R.id.component_timePicker_numberPicker_hours);
        numberPickerMin = findViewById(R.id.component_timePicker_numberPicker_minutes);
        numberPickerSec = findViewById(R.id.component_timePicker_numberPicker_seconds);
        initNumberPicker(numberPickerHour, minHour, maxHour);
        initNumberPicker(numberPickerMin, minMinute, maxMinute);
        initNumberPicker(numberPickerSec, minSecond, maxSecond);


    }

    private void initNumberPicker(NumberPicker numberPicker, int min, int max) {
        numberPicker.setDisplayedValues(SuiteHelper.generate(min, max));
        numberPicker.setMinValue(min);
        numberPicker.setMaxValue(max - 1);
        numberPicker.setValue(min);
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
        numberPickerSec.setValue(second);
    }

    public void setMinutes(int minute) {
        numberPickerMin.setValue(minute);
    }

    public void setHours(int hour) {
        numberPickerHour.setValue(hour);
    }

}
