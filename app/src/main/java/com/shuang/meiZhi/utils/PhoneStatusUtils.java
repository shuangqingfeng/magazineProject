package com.shuang.meiZhi.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author feng
 * @Description: 关于手机信息的工具类
 * @date 2017/3/31
 */
public class PhoneStatusUtils {
    /**
     * 判断网络是否连接
     *
     * @return
     */
    public static boolean isNetworkConnected() {
        try {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) UIUtils.getContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
