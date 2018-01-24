package shun_min_chang.hyena.dialog.utils;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;

import shun_min_chang.hyena.dialog.R;

import static android.os.Build.VERSION.SDK_INT;

/**
 * Created by shunminchang on 2018/1/18.
 */

public class Utils {
    public static int dpToPx(Context context, float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                context.getResources().getDisplayMetrics());
    }

    public static int spToPx(Context context, float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value,
                context.getResources().getDisplayMetrics());
    }

    public static void setDrawableBackgroundColor(@NonNull View view, @NonNull int color) {
        ((GradientDrawable) view.getBackground()).setColor(color);
    }

    public static void setDrawableCornerRadius(@NonNull View view, int value) {
        ((GradientDrawable) view.getBackground()).setCornerRadius(value);
    }

    public static int getDefaultBackgroundColor(Context context) {
        if (SDK_INT < Build.VERSION_CODES.M) {
            return ContextCompat.getColor(context, R.color.colorBackground21);
        } else {
            return ContextCompat.getColor(context, R.color.colorBackground23);
        }
    }
}
