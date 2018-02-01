package com.r.refresh.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 上拉加载视图
 * @author ray
 * @date 2018/01/24
 */
public class FooterView extends LinearLayout {

    private static final int DEFAULT_CIRCLE_SIZE = 42;
    private CircleProgressBar circleProgressBar;
    private TextView tvLoad;

    public FooterView(Context context) {
        super(context);
        setupViews();
    }

    public FooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupViews();
    }

    public FooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupViews();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FooterView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupViews();
    }

    /**
     * 添加View
     */
    private void setupViews() {
        this.setOrientation(HORIZONTAL);
        this.setGravity(Gravity.CENTER);

        circleProgressBar = new CircleProgressBar(getContext());
        LayoutParams lp = new LayoutParams((int) RyUtils.dipToPx(getContext(), DEFAULT_CIRCLE_SIZE),
                (int) RyUtils.dipToPx(getContext(), DEFAULT_CIRCLE_SIZE));
        lp.rightMargin = (int) RyUtils.dipToPx(getContext(), 10);
        addView(circleProgressBar, lp);
        tvLoad = new TextView(getContext());
        addView(tvLoad);
    }

    public void setLoadText(String loadtText) {
        this.tvLoad.setText(loadtText);
    }

    public void setLoadTextColor(int color) {
        tvLoad.setTextColor(color);
    }

    public void setProgressBgColor(int color) {
        circleProgressBar.setBackgroundColor(color);
    }

    public void setProgressColor(int color) {
        circleProgressBar.setColorSchemeColors(color);
    }

    /**
     * 开始动画
     */
    public void start() {
        circleProgressBar.start();
    }

    /**
     * 设置动画起始位置
     */
    public void setStartEndTrim(float startAngle, float endAngle) {
        circleProgressBar.setStartEndTrim(startAngle, endAngle);
    }

    /**
     * 停止动画
     */
    public void stop() {
        circleProgressBar.stop();
    }

    public void setProgressRotation(float rotation) {
        circleProgressBar.setProgressRotation(rotation);
    }
}
