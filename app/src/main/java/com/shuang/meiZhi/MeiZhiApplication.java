package com.shuang.meiZhi;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;


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
        Logger.init("magazine").logLevel(LogLevel.FULL);
        BGASwipeBackManager.getInstance().init(this);
    }

    public static Context getApplication() {
        return mContext;
    }

}
