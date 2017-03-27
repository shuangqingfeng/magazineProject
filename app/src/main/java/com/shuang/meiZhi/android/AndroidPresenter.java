package com.shuang.meiZhi.android;

import com.shuang.meiZhi.IDataSource;
import com.shuang.meiZhi.entity.AndroidBean;

import java.util.Calendar;

/**
 * @author feng
 * @Description:
 * @date 2017/3/23
 */
public class AndroidPresenter implements IAndroidContract.IAndroidPresenter {
    private IAndroidContract.IAndroidView iAndroidView;
    private AndroidModule androidModule;


    public AndroidPresenter(IAndroidContract.IAndroidView iAndroidView, AndroidModule androidModule) {
        this.iAndroidView = iAndroidView;
        this.androidModule = androidModule;
        iAndroidView.setPresenter(this);
    }

    @Override
    public void onObtainData(int month,int day) {
        iAndroidView.onRefresh(1);
//        取得系统日期:

        androidModule.loadDataSource(month, day, new IDataSource.LoadResultSourceCallBack<AndroidBean>() {

            @Override
            public void onResult(AndroidBean object) {
                iAndroidView.onResultSuccess(object);
            }

            @Override
            public void onResultNoAvailable() {
                iAndroidView.onRefresh(0);
            }
        });
    }
}
