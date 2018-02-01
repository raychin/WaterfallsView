package com.ray.demo.common;

import android.content.ClipData;
import android.content.Context;

/**
 * 粘贴板工具类
 * @author ray
 * @date 2018/01/25
 */
public class ClipboardUtils {
    /**
     * 复制内容到粘贴板
     * @param context
     * @param text
     */
    @SuppressWarnings("deprecation")
    public static void copyToClipboard(Context context, String text){
        if (android.os.Build.VERSION.SDK_INT > 11) {
            android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setPrimaryClip(ClipData.newPlainText("text", text));
        } else {
            android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setText(text);
        }
    }

    /**
     * 获取粘贴板内容
     * @param context
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String getClipboardText(Context context){
        String text = null;
        if (android.os.Build.VERSION.SDK_INT > 11) {
            android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if(clipboardManager.hasPrimaryClip()) {
                ClipData clipData = clipboardManager.getPrimaryClip();
                ClipData.Item item = clipData.getItemAt(0);
                text = item.getText().toString();
            }
        } else {
            android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if(clipboardManager.hasText()) {
                text = clipboardManager.getText().toString();
            }
        }
        return text;
    }
}
