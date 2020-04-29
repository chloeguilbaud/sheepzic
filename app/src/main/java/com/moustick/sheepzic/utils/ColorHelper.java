package com.moustick.sheepzic.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

public class ColorHelper {

    public static void setColor(Context context, int colorId, @NonNull Drawable drawable) {
        drawable.setColorFilter(new PorterDuffColorFilter(context.getResources().getColor(colorId), PorterDuff.Mode.SRC_IN));
    }

}
