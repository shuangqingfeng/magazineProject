package com.shuang.meiZhi;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;


/**
 * @author feng
 * @Description:
 * @date 2017/3/22
 */
public class MeiZhiApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Logger.init().logLevel(LogLevel.FULL);
    }

    public static Context getApplication() {
        return mContext;
    }

}
