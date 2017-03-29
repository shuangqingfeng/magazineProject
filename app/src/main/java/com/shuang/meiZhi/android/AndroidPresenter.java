package com.shuang.meiZhi.android;

import com.shuang.meiZhi.base.IDataSource;
import com.shuang.meiZhi.constantPool.RefreshConstantField;
import com.shuang.meiZhi.entity.AndroidBean;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author feng
 * @Description:
 * @date 2017/3/23
 */
public class AndroidPresenter implements IAndroidContract.IAndroidPresenter {
    private CompositeSubscription mCompositeSubscription;
    private IAndroidContract.IAndroidView iAndroidView;
    private AndroidModule androidModule;


    public AndroidPresenter(IAndroidContract.IAndroidView iAndroidView, AndroidModule androidModule) {
        this.iAndroidView = iAndroidView;
        this.androidModule = androidModule;
        iAndroidView.setPresenter(this);
    }

    @Override
    public void onObtainData(int size, int page) {
        iAndroidView.onRefresh(RefreshConstantField.REFRESHING);
        addSubscription(androidModule.loadDataSource(size, page, new IDataSource.LoadResultSourceCallBack<AndroidBean>() {
            @Override
            public void onResult(AndroidBean object) {
                iAndroidView.onRefresh(RefreshConstantField.NO_REFRESHING);
                iAndroidView.onResultSuccess(object);
            }

            @Override
            public void onResultNoAvailable() {
                iAndroidView.onRefresh(RefreshConstantField.NO_REFRESHING);
            }
        }));
    }

    @Override
    public void addSubscription(Subscription subscription) {
        if (null == mCompositeSubscription) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    @Override
    public CompositeSubscription getCompositeSuscription() {
        return mCompositeSubscription;
    }

}
