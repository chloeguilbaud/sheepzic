package com.moustick.sheepzic.preferences.components;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.moustick.sheepzic.R;

public class Switch extends ConstraintLayout {

    private TextView labelView;
    private SwitchMaterial switchView;

    private static final boolean DEFAULT_CHECKED_STATE = false;

    public Switch(Context context) {
        super(context);
        init(null);
    }

    public Switch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public Switch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        // View initialisation
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.component_preferences_switch, this);

        labelView = findViewById(R.id.component_preferences_switch_labelView);
        switchView = findViewById(R.id.component_preferences_switch_switchView);

        if (attrs != null) {

            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.Switch);

            if (typedArray.getBoolean(R.styleable.Switch_switchChecked, DEFAULT_CHECKED_STATE))
                check();

            setLabel(typedArray.getString(R.styleable.Switch_switchLabel));

        }

        // TODO remove ?
        /*switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switchView.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                    switchView.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
                } else {
                    switchView.setThumbTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorNightGreyLight)));
                    switchView.setTrackTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorNightGreyDark)));
                }
            }
        });*/

    }

    public void setLabel(String label) {
        labelView.setText(label);
    }

    public void check() {
        switchView.setChecked(true);
    }

    public void uncheck() {
        switchView.setChecked(false);
    }

    public void isChecked() {
        switchView.isChecked();
    }

}
