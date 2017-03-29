package com.shuang.meiZhi.ios;

import com.shuang.meiZhi.base.IDataSource;
import com.shuang.meiZhi.dataApi.MeiZhiRetrofit;
import com.shuang.meiZhi.entity.IOSBean;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author feng
 * @Description: ios 页面
 * @date 2017/3/27
 */
public class IosModule implements IDataSource<IOSBean> {

    private static IosModule module;

    public static IosModule getInstance() {
        module = new IosModule();
        return module;
    }

    @Override
    public Subscription loadDataSource(int size, int pag, final LoadResultSourceCallBack<IOSBean> loadResultSourceCallBack) {
       return MeiZhiRetrofit.getMeiZhiApi().getIosData(size, pag)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<IOSBean>() {
                    @Override
                    public void onCompleted() {
                        loadResultSourceCallBack.onResultNoAvailable();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadResultSourceCallBack.onResultNoAvailable();
                    }

                    @Override
                    public void onNext(IOSBean iosBean) {
                        loadResultSourceCallBack.onResult(iosBean);
                    }
                });

    }
}
