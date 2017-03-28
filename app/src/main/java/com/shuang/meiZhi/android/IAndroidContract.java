package com.shuang.meiZhi.android;

import com.shuang.meiZhi.base.IBasePresenter;
import com.shuang.meiZhi.base.IBaseRefreshView;
import com.shuang.meiZhi.base.IBaseView;
import com.shuang.meiZhi.entity.AndroidBean;

/**
 * @author feng
 * @Description: andrid 契约类
 * @date 2017/3/24
 */
public interface IAndroidContract {
    interface IAndroidView extends IBaseView<IAndroidPresenter>, IBaseRefreshView {
        void onResultSuccess(AndroidBean androidBean);

        void onResultFail(Exception e);
    }

    interface IAndroidPresenter extends IBasePresenter {

    }
}
