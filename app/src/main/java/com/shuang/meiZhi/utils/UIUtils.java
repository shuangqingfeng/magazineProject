package com.shuang.meiZhi.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.shuang.meiZhi.MeiZhiApplication;


/**
 * @author feng
 * @Description:
 * @date 2017/3/22
 */
public class UIUtils {
    public static Context getContext() {
        return MeiZhiApplication.getApplication();
    }

    /**
     * 获取资源
     *
     * @return
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取资源目录下的String
     *
     * @param resId
     * @return
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 获取资源目录下的String 数组
     *
     * @param resId
     * @return
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获取Dimens
     *
     * @param resId
     * @return
     */
    public static int getDimens(int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取 drawable 目录下的资源
     *
     * @param resId
     * @return
     */
    public static Drawable getDrawable(int resId) {
        return getContext().getResources().getDrawable(resId);
    }

    /**
     * 获取 res 目录下的颜色
     *
     * @param resId
     * @return
     */
    public static int getColor(int resId) {
        return getContext().getResources().getColor(resId);
    }

    /**
     * 开启新的Activity
     *
     * @param context 上下文
     * @param clazz   开启的 activity
     * @param bundle  传递的数据 可以是 null
     * @param isData  是否传递数据 true 传递，false 不传递
     */
    public static void startActivity(Context context, Class<?> clazz, Bundle bundle, boolean isData) {
        Intent mIntent = new Intent(context, clazz);
        if (isData) {
            mIntent.putExtras(bundle);
            context.startActivity(mIntent);
        } else {
            context.startActivity(mIntent);
        }
    }
    /**
     * 填充布局
     * @param resId
     * @return
     */
    /**
     * 填充布局
     *
     * @param resId
     * @return
     */
    public static View inflate(int resId) {
        return LayoutInflater.from(getContext()).inflate(resId, null);
    }
}
