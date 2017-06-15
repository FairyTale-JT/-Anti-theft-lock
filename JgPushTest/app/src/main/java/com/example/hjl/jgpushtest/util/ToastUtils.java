package com.example.hjl.jgpushtest.util;

import android.content.Context;
import android.widget.Toast;

import com.example.hjl.jgpushtest.App;

/**
 * Created by Administrator on 2016/12/31.
 */

public class ToastUtils {
    private static Toast mToast;

    /**
     * 非阻塞式显示Toast，防止出现连续点击Toast时的显示问题
     */
    public static void showToast(Context context, CharSequence text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

    public static void showToast(Context context, CharSequence text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }
    public static void showToast( CharSequence text) {
        showToast(App.getInstance().getApplicationContext(), text, Toast.LENGTH_SHORT);
    }
}
