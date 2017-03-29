package com.shuang.meiZhi.ios;

import com.shuang.meiZhi.base.IDataSource;
import com.shuang.meiZhi.constantPool.RefreshConstantField;
import com.shuang.meiZhi.entity.IOSBean;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author feng
 * @Description: ios 页面数据和视图的交互
 * @date 2017/3/27
 */
public class IosPresenter implements IIosContract.IIosPresenter {
    private IIosContract.IIosView mView;
    private IosModule mIosModule;
    private CompositeSubscription mCompositeSubscription;

    public IosPresenter(IIosContract.IIosView iosView, IosModule iosModule) {
        this.mView = iosView;
        this.mIosModule = iosModule;
        mView.setPresenter(this);
    }

    @Override
    public void onObtainData(int month, int day) {
        mView.onRefresh(RefreshConstantField.REFRESHING);
        addSubscription(mIosModule.loadDataSource(month, day, new IDataSource.LoadResultSourceCallBack<IOSBean>() {
            @Override
            public void onResult(IOSBean object) {
                mView.onResultSuccess(object);
            }

            @Override
            public void onResultNoAvailable() {
                mView.onRefresh(RefreshConstantField.NO_REFRESHING);
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
