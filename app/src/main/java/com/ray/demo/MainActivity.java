package com.ray.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.ray.demo.common.ClipboardUtils;
import com.ray.demo.common.LoginUtils;
import com.ray.demo.refresh.RefreshDemoActivity;
import com.ray.demo.waterfall.WaterFallsActivity;

/**
 * demo演示
 * @author ray
 * @date 2018/01/25
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String text = ClipboardUtils.getClipboardText(MainActivity.this);
        System.out.println("------------粘贴板内容 = " + (text == null ? "" : text));

        System.out.println("------------首次登录判断 = " + (LoginUtils.isTodayFirstLogin(MainActivity.this) == 1 ? "首次登录" : "非首次登录"));

        (findViewById(R.id.refreshBtn)).setOnClickListener(this);
        (findViewById(R.id.waterfallBtn)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refreshBtn:
                startActivity(new Intent(MainActivity.this, RefreshDemoActivity.class));
                break;
            case R.id.waterfallBtn:
                startActivity(new Intent(MainActivity.this, WaterFallsActivity.class));
                break;
        }
    }
}
