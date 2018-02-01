package com.ray.demo;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 测试布局
 * @author Ray
 * @date 2018/01/29
 */
public class DemoLayout extends ListView {
    public DemoLayout(Context context) {
        super(context);
    }

    public DemoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DemoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DemoLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
