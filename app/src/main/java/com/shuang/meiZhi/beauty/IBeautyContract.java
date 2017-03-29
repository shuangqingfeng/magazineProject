package com.shuang.meiZhi.beauty;

import com.shuang.meiZhi.base.IBasePresenter;
import com.shuang.meiZhi.base.IBaseRefreshView;
import com.shuang.meiZhi.base.IBaseView;
import com.shuang.meiZhi.entity.BeautyBean;

/**
 * @author feng
 * @Description: 美女页面的契约
 * @date 2017/3/27
 */

public interface IBeautyContract {
    interface IBeautyView extends IBaseView<IBeautyPersenter>, IBaseRefreshView {
        void onResultSuccess(BeautyBean beautyBean);

        void onResultFail(Exception e);
    }

    interface IBeautyPersenter extends IBasePresenter {

    }
}
