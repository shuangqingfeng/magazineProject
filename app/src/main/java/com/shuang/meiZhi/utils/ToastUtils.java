package com.shuang.meiZhi.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Display;
import android.view.Gravity;
import android.widget.Toast;

/**
 * @author feng
 * @Description:Toast 工具类.简化Toast的使用，并且不用关心线程的问题。
 * Toast的初始化会创建默认构造Handler。Handler默认构造会使用当前线程Looper。如果没有，抛异常。
 * 调用了Looper.prepare和Looper.loop以后Toast才可以生效。但是子线程调用了loop方法就阻塞了。
 * 所以选择抛到主线程执行。
 * @date 2017/3/28
 */
public class ToastUtils {
    private static Toast mToast;

    /**
     * show a short toast
     *
     * @param text
     */
    public static void show(String text) {
        safeShow(text, Toast.LENGTH_SHORT);
    }

    /**
     * show a long toast
     *
     * @param text string text
     */
    public static void showLong(String text) {
        safeShow(text, Toast.LENGTH_LONG);
    }


    /**
     * show a short toast
     *
     * @param resId string id
     */
    public static void show(int resId) {
        show(UIUtils.getContext().getString(resId));
    }

    public static void showLong(int resId) {
        showLong(UIUtils.getContext().getString(resId));
    }


    /**
     * 安全弹出Toast。处理线程的问题。
     *
     * @param text
     * @param lengthShort
     */
    private static void safeShow(final String text, final int lengthShort) {
        if (Looper.myLooper() != Looper.getMainLooper()) {//如果不是在主线程弹出吐司，那么抛到主线程弹
            new Handler(Looper.getMainLooper()).post(
                    new Runnable() {
                        @Override
                        public void run() {
                            showToast(text, lengthShort);
                        }
                    }
            );
        } else {
            showToast(text, lengthShort);
        }
    }

    /**
     * 弹出Toast，处理单例的问题。
     *
     * @param text
     * @param lengthShort
     */
    private static void showToast(String text, int lengthShort) {
        if (mToast == null) {
            mToast = Toast.makeText(UIUtils.getContext(), "", Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        }
        mToast.setDuration(lengthShort);
        mToast.setText(text);
        mToast.show();
    }
}
