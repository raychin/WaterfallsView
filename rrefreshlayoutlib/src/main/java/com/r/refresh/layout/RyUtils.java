package com.r.refresh.layout;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * 工具类
 * @author ray
 * @date 2018/01/24
 */
public abstract class RyUtils {

    public static float dipToPx(Context context, float value) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, metrics);
    }
}
