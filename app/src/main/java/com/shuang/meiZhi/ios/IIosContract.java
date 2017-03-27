package com.shuang.meiZhi.ios;

import com.shuang.meiZhi.IBasePresenter;
import com.shuang.meiZhi.IBaseRefreshView;
import com.shuang.meiZhi.IBaseView;
import com.shuang.meiZhi.entity.AndroidBean;
import com.shuang.meiZhi.entity.IOSBean;

/**
 * @author feng
 * @Description: ios 契约类
 * @date 2017/3/27
 */
public interface IIosContract {
    interface IIosView extends IBaseView<IIosPresenter> ,IBaseRefreshView{
        void onResultSuccess(IOSBean iosBean);

        void onResultFail(Exception e);
    }

    interface IIosPresenter extends IBasePresenter {

    }
}