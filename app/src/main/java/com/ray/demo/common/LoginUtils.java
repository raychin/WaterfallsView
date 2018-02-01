package com.ray.demo.common;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 登录相关工具类
 * @author ray
 * @date 2018/01/25
 */
public class LoginUtils {
    public static final String NAME_LAST_LOGIN_TIME = "LastLoginTime";
    public static final String KEY_LAST_LOGIN_TIME = "LoginTime";
    public static final String KEY_EXIT_TIME = "ExitTime";

    /**
     * 日期格式
     */
    public static final String DATE_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 判断是否是当日第一次登陆
     * @return 0表示非首次登录，1表示首次登录
     */
    public static int isTodayFirstLogin(Context context) {
        int result = 0;
        // 取
        SharedPreferences preferences = context.getSharedPreferences(NAME_LAST_LOGIN_TIME, Context.MODE_PRIVATE);
        String lastTime = preferences.getString(KEY_LAST_LOGIN_TIME, "1900-01-01");
        SimpleDateFormat df = new SimpleDateFormat(DATE_YYYY_MM_DD);
        // 获取当前的日期
        String todayTime = df.format(new Date());
        // 如果两个时间段不相同，则非首次登录，同时保存首次登录时间
        if (!lastTime.equals(todayTime)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(KEY_LAST_LOGIN_TIME, todayTime);
            editor.commit();
            result ++;
        }
        return result;
    }

    /**
     * 保存每次退出的时间
     * @param exitLoginTime
     */
    public static void saveExitTime(Context context, String exitLoginTime) {
        SharedPreferences.Editor editor = context.getSharedPreferences(NAME_LAST_LOGIN_TIME, Context.MODE_PRIVATE).edit();
        editor.putString("LoginTime", KEY_EXIT_TIME);
        // 这里用apply()而没有用commit()是因为apply()是异步处理提交，不需要返回结果，而我也没有后续操作
        // 而commit()是同步的，效率相对较低
        // apply()提交的数据会覆盖之前的,这个需求正是我们需要的结果
        editor.apply();
    }
}
