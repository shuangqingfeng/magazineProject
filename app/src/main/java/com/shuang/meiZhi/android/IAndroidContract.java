package com.shuang.meiZhi.android;

import com.shuang.meiZhi.IBasePresenter;
import com.shuang.meiZhi.IBaseRefreshView;
import com.shuang.meiZhi.IBaseView;
import com.shuang.meiZhi.entity.AndroidBean;

/**
 * @author feng
 * @Description:
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
