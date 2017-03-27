package com.shuang.meiZhi.ios;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shuang.meiZhi.BaseFragment;
import com.shuang.meiZhi.R;
import com.shuang.meiZhi.adapter.IosItemViewBinder;
import com.shuang.meiZhi.constantPool.RefreshConstantField;
import com.shuang.meiZhi.entity.IOSBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author feng
 * @Description:
 * @date 2017/3/22
 */
public class IosFragment extends BaseFragment implements IIosContract.IIosView {
    private static final int preload_size = 10;
    @BindView(R.id.srl_iosRefreshing)
    SwipeRefreshLayout iosRefreshing;
    @BindView(R.id.rv_iosDataShow)
    RecyclerView iosDataShow;
    private IIosContract.IIosPresenter mIosPresenter;
    private LinearLayoutManager mLinearLayoutManager;
    private int mPage = 1;
    private List<IOSBean.ResultsBean> mIosBeanLists;
    private IOSBean mIosBean;
    private MultiTypeAdapter iosAdapter;
    private OnScrollBottomListener mOnScrollBottomListener;
    private boolean mIsFirstTimeTouchBottom = true;

    @Override
    protected void initView() {
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        iosDataShow.setLayoutManager(mLinearLayoutManager);
        iosAdapter = new MultiTypeAdapter();
        iosAdapter.register(IOSBean.ResultsBean.class, new IosItemViewBinder());
        iosDataShow.setAdapter(iosAdapter);
        mOnScrollBottomListener = new OnScrollBottomListener();
        iosDataShow.addOnScrollListener(mOnScrollBottomListener);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_ios;
    }

    @Override
    protected void initData() {
        mIosBeanLists = new ArrayList<>();
        if (null != mIosPresenter) {
            mIosPresenter.onObtainData(preload_size, mPage);
        }
        iosRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (null != mIosBeanLists && mIosBeanLists.size() > 0)
                    mIosBeanLists.clear();
                mIosPresenter.onObtainData(preload_size, mPage);
            }
        });
    }

    @Override
    public void onResultSuccess(IOSBean iosBean) {
        this.mIosBean = iosBean;
        if (null != iosAdapter) {
            mIosBeanLists.addAll(mIosBean.getResults());
            iosAdapter.setItems(mIosBeanLists);
            iosAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void onResultFail(Exception e) {

    }

    @Override
    public void onRefresh(final int refresh) {
        if (iosRefreshing.isRefreshing()) {
            iosRefreshing.postDelayed(new Runnable() {
                @Override
                public void run() {
                    iosRefreshing.setRefreshing(refresh == RefreshConstantField.REFRESHING
                            ? true : false);
                }
            }, RefreshConstantField.REFRESHING_DELAY_MILLIS);
        } else {
            iosRefreshing.setRefreshing(refresh == RefreshConstantField.REFRESHING
                    ? true : false);
        }
//        if (refresh == RefreshConstantField.REFRESHING) {
//            iosRefreshing.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    iosRefreshing.setRefreshing(true);
//                }
//            }, RefreshConstantField.REFRESHING_DELAY_MILLIS);
//        } else {
//            iosRefreshing.setRefreshing(false);
//        }
    }

    @Override
    public void setPresenter(IIosContract.IIosPresenter presenter) {
        mIosPresenter = presenter;
    }

    private class OnScrollBottomListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            boolean isBottomed = mLinearLayoutManager.findLastVisibleItemPosition() ==
                    recyclerView.getAdapter().getItemCount() - 1;
            if (!iosRefreshing.isRefreshing() && isBottomed) {
                if (mIsFirstTimeTouchBottom = true) {
                    mIosPresenter.onObtainData(preload_size, ++mPage);
                } else {
                    mIsFirstTimeTouchBottom = false;
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        iosDataShow.removeOnScrollListener(mOnScrollBottomListener);
        super.onDestroy();
    }
}
