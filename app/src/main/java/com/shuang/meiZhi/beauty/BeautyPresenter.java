package com.shuang.meiZhi.beauty;

import com.shuang.meiZhi.IDataSource;
import com.shuang.meiZhi.entity.BeautyBean;

import javax.sql.DataSource;

/**
 * @author feng
 * @Description:
 * @date 2017/3/27
 */

public class BeautyPresenter implements IBeautyContract.IBeautyPersenter {
    private IBeautyContract.IBeautyView mBeautyView;
    private IDataSource<BeautyBean> mIDataSource;

    public BeautyPresenter(IBeautyContract.IBeautyView iBeautyView, IDataSource<BeautyBean> iDataSource) {
        this.mBeautyView = iBeautyView;
        this.mIDataSource = iDataSource;
    }

    @Override
    public void onObtainData(int size, int page) {
//        mIDataSource.loadDataSource();
    }
}
