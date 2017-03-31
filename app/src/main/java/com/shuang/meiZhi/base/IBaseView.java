package com.shuang.meiZhi.base;

import rx.Subscription;

/**
 * @author feng
 * @Description:
 * @date 2017/3/23
 */
public interface IBaseView<T> {
    void setPresenter(T presenter);

    void showMessage(String msg);
}
