package com.shuang.meiZhi.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author feng
 * @Description:
 * @date 2017/3/24
 */
public interface IBasePresenter {
    void onObtainData(int size, int page);

    void addSubscription(Subscription subscription);

    CompositeSubscription getCompositeSuscription();
}
