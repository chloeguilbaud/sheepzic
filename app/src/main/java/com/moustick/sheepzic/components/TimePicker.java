package com.moustick.sheepzic.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.moustick.sheepzic.MainActivity;
import com.moustick.sheepzic.R;

import java.util.ArrayList;

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

        numberPickerHour = findViewById(R.id.numpicker_hours);
        numberPickerMin = findViewById(R.id.numpicker_minutes);
        numberPickerSec = findViewById(R.id.numpicker_seconds);
        initNumberPicker(numberPickerHour, 0, 100);
        initNumberPicker(numberPickerMin, 0, 60);
        initNumberPicker(numberPickerSec, 0, 60);


    }

    private void initNumberPicker(NumberPicker numberPicker, int min, int max) {

        String[] data = new String[max];
        if (max > 10) {
            for (int i = min; i < 10; i++) {
                data[i] = "0" + i;
            }
            for (int i = 10; i < max; i++) {
                data[i] = "" + i;
            }
        } else {
            for (int i = min; i < max; i++) {
                data[i] = "0" + i;
            }
        }
        numberPicker.setDisplayedValues(data);
        numberPicker.setMinValue(min);
        numberPicker.setMaxValue(max - 1);
        numberPicker.setValue(min);
    }

}
