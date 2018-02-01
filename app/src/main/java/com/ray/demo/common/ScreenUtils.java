package com.ray.demo.common;

import android.content.Context;
import android.view.WindowManager;

/**
 * 屏幕工具类
 * @author ray
 * @date 2018/02/01
 */

public class ScreenUtils {

    /**
     * 获取屏幕宽度
     * @return
     */
    public static int getScreeWidth (Context context) {
        return ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
    }
}
