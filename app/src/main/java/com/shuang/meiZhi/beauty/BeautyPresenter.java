package com.shuang.meiZhi.beauty;

import com.shuang.meiZhi.base.IDataSource;
import com.shuang.meiZhi.constantPool.RefreshConstantField;
import com.shuang.meiZhi.entity.BeautyBean;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author feng
 * @Description:
 * @date 2017/3/27
 */

public class BeautyPresenter implements IBeautyContract.IBeautyPersenter {
    private IBeautyContract.IBeautyView mBeautyView;
    private BeautyModule mBeautyModule;
    private CompositeSubscription mCompositeSubscription;

    public BeautyPresenter(IBeautyContract.IBeautyView iBeautyView, BeautyModule beautyModule) {
        this.mBeautyView = iBeautyView;
        this.mBeautyModule = beautyModule;
        mBeautyView.setPresenter(this);
    }

    @Override
    public void onObtainData(int size, int page) {
        mBeautyView.onRefresh(RefreshConstantField.REFRESHING);
        addSubscription(mBeautyModule.loadDataSource(size, page, new IDataSource.LoadResultSourceCallBack<BeautyBean>() {
            @Override
            public void onResult(BeautyBean object) {
                mBeautyView.onRefresh(RefreshConstantField.NO_REFRESHING);
                mBeautyView.onResultSuccess(object);
            }

            @Override
            public void onResultNoAvailable() {
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
