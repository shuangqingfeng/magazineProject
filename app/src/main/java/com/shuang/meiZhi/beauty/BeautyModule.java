package com.shuang.meiZhi.beauty;

import com.shuang.meiZhi.base.IDataSource;
import com.shuang.meiZhi.dataApi.MeiZhiRetrofit;
import com.shuang.meiZhi.entity.BeautyBean;
import com.shuang.meiZhi.entity.IOSBean;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author feng
 * @Description: 妹子页面的数据请求类
 * @date 2017/3/27
 */

public class BeautyModule implements IDataSource<BeautyBean> {
    private static BeautyModule beautyModule;

    public static BeautyModule getInstance() {
        if (null == beautyModule) {
            beautyModule = new BeautyModule();
        }
        return beautyModule;
    }

    @Override
    public Subscription loadDataSource(int size, int pag, final LoadResultSourceCallBack<BeautyBean> loadResultSourceCallBack) {
        return MeiZhiRetrofit.getMeiZhiApi().getBeautyData(size, pag)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<BeautyBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        loadResultSourceCallBack.onResultNoAvailable(e);
                    }

                    @Override
                    public void onNext(BeautyBean beautyBean) {
                        loadResultSourceCallBack.onResult(beautyBean);
                    }
                });

    }
}
