package com.shuang.meiZhi.android;

import com.shuang.meiZhi.base.IDataSource;
import com.shuang.meiZhi.dataApi.MeiZhiRetrofit;
import com.shuang.meiZhi.entity.AndroidBean;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author feng
 * @Description:
 * @date 2017/3/23
 */
public class AndroidModule implements IDataSource<AndroidBean> {
    private static AndroidModule instance;

    public static AndroidModule getInstance() {
        if (null == instance) {
            instance = new AndroidModule();
        }
        return instance;
    }

    @Override
    public void loadDataSource(int size, int pag, final LoadResultSourceCallBack<AndroidBean> loadResultSourceCallBack) {
        MeiZhiRetrofit.getMeiZhiApi().getAndroidData(size, pag).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<AndroidBean>() {
            @Override
            public void onCompleted() {
                loadResultSourceCallBack.onResultNoAvailable();
            }

            @Override
            public void onError(Throwable e) {
                loadResultSourceCallBack.onResultNoAvailable();
            }

            @Override
            public void onNext(AndroidBean androidBean) {
                loadResultSourceCallBack.onResult(androidBean);
            }
        });
    }
}
