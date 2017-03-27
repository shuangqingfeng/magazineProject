package com.shuang.meiZhi.beauty;


import com.shuang.meiZhi.BaseFragment;
import com.shuang.meiZhi.R;
import com.shuang.meiZhi.entity.BeautyBean;

/**
 * @author feng
 * @Description:
 * @date 2017/3/22
 */
public class MeiZhiFragment extends BaseFragment implements IBeautyContract.IBeautyView {
    @Override
    protected void initView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_mei_zhi;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResultSuccess(BeautyBean beautyBean) {

    }

    @Override
    public void onResultFail(Exception e) {

    }

    @Override
    public void onRefresh(int refresh) {

    }

    @Override
    public void setPresenter(IBeautyContract.IBeautyPersenter presenter) {

    }
}
