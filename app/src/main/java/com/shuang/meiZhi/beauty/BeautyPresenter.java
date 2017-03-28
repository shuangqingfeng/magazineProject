package com.shuang.meiZhi.beauty;

import com.shuang.meiZhi.base.IDataSource;
import com.shuang.meiZhi.constantPool.RefreshConstantField;
import com.shuang.meiZhi.entity.BeautyBean;

/**
 * @author feng
 * @Description:
 * @date 2017/3/27
 */

public class BeautyPresenter implements IBeautyContract.IBeautyPersenter {
    private IBeautyContract.IBeautyView mBeautyView;
    private BeautyModule mBeautyModule;

    public BeautyPresenter(IBeautyContract.IBeautyView iBeautyView, BeautyModule beautyModule) {
        this.mBeautyView = iBeautyView;
        this.mBeautyModule = beautyModule;
        mBeautyView.setPresenter(this);
    }

    @Override
    public void onObtainData(int size, int page) {
        mBeautyView.onRefresh(RefreshConstantField.REFRESHING);
        mBeautyModule.loadDataSource(size, page, new IDataSource.LoadResultSourceCallBack<BeautyBean>() {
            @Override
            public void onResult(BeautyBean object) {
                mBeautyView.onRefresh(RefreshConstantField.NO_REFRESHING);
                mBeautyView.onResultSuccess(object);
            }

            @Override
            public void onResultNoAvailable() {

            }
        });
    }
}
