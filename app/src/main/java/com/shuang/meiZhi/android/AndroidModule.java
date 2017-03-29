package com.shuang.meiZhi.android;

import com.orhanobut.logger.Logger;
import com.shuang.meiZhi.base.BaseFragment;
import com.shuang.meiZhi.base.IDataSource;
import com.shuang.meiZhi.dataApi.MeiZhiRetrofit;
import com.shuang.meiZhi.entity.AndroidBean;

import rx.Subscriber;
import rx.Subscription;
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
    public Subscription loadDataSource(int size, int pag, final LoadResultSourceCallBack<AndroidBean> loadResultSourceCallBack) {
       return MeiZhiRetrofit.getMeiZhiApi().getAndroidData(size, pag).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<AndroidBean>() {
            @Override
            public void onCompleted() {
                Logger.d("------------>onCompleted");
                loadResultSourceCallBack.onResultNoAvailable();
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("------------>onError");
                loadResultSourceCallBack.onResultNoAvailable();
            }

            @Override
            public void onNext(AndroidBean androidBean) {
                Logger.d("------------>onNext");
                loadResultSourceCallBack.onResult(androidBean);
            }
        });
    }
}
