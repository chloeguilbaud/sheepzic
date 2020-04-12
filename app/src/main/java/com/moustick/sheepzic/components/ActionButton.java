package com.moustick.sheepzic.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.moustick.sheepzic.R;

public class ActionButton extends AppCompatButton {

    private Drawable normalIcon;
    private Drawable selectedIcon;

    private boolean selected = false;
    private boolean enabled = true;

    public ActionButton(Context context) {
        super(context);
        init(null);
    }

    public ActionButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ActionButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        // Configuring button style
        int padding = 18;
        this.setPadding(padding, padding, padding, padding);
        this.setAllCaps(false);
        this.setTextSize(14);

        if (attrs != null) {

            // Setting component with given layout attrs
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ActionButton);

            this.setText(typedArray.getString(R.styleable.ActionButton_actionButtonTitle));
            normalIcon = typedArray.getDrawable(R.styleable.ActionButton_actionButtonActivatedIcon);
            selectedIcon = typedArray.getDrawable(R.styleable.ActionButton_actionButtonDeactivatedIcon);

        }

        enable(true);

    }

    public void enable(boolean enable) {
        this.enabled = enable;
        if(selected) {
            if (enable)
                setColor(R.color.colorActionButtonDeactivateEnabled, selectedIcon);
            else
                setColor(R.color.colorActionButtonDeactivateDisabled, selectedIcon);
            setIcon(selectedIcon);
        } else {
            if (enable)
                setColor(R.color.colorActionButtonEnabled, normalIcon);
            else
                setColor(R.color.colorActionButtonDisabled, normalIcon);
            setIcon(normalIcon);
        }
    }

    public void select() {
        this.selected = !this.selected;
        enable(enabled);
    }

    public void select(boolean select) {
        this.selected = select;
        enable(enabled);
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean isEnabled() {
        return enabled;
    }

    private void setColor(int colorId, @NonNull Drawable drawable) {
        this.setTextColor(getResources().getColor(colorId));
        drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(colorId), PorterDuff.Mode.SRC_IN));
    }

    private void setIcon(@NonNull Drawable drawable) {
        this.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
    }

    private void setIcon(int iconID) {
        setIcon(getResources().getDrawable(iconID));
    }

}