package com.shuang.meiZhi.beauty;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.orhanobut.logger.Logger;
import com.shuang.meiZhi.adapter.BeautyItemViewBinder;
import com.shuang.meiZhi.base.BaseFragment;
import com.shuang.meiZhi.R;
import com.shuang.meiZhi.constantPool.RefreshConstantField;
import com.shuang.meiZhi.entity.BeautyBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.drakeet.multitype.MultiTypeAdapter;
import okhttp3.internal.http.RetryAndFollowUpInterceptor;

/**
 * @author feng
 * @Description:
 * @date 2017/3/22
 */
public class BeautyFragment extends BaseFragment implements IBeautyContract.IBeautyView {
    private static final int SPAN_COUNT = 2;
    private static final int PRELOAD_SIZE = 10;
    private int mPage = 1;
    @BindView(R.id.srl_beautyRefreshing)
    SwipeRefreshLayout beautyRefreshing;
    @BindView(R.id.rv_beautyDataShow)
    RecyclerView beautyDataShow;
    private IBeautyContract.IBeautyPersenter mIBeautyPersenter;
    private StaggeredGridLayoutManager mGridLayoutManager;
    private List<BeautyBean.ResultsBean> mBeanLists;
    private BeautyBean mBean;
    private MultiTypeAdapter mBeautyAdapter;
    private boolean mIsFirstTimeTouchBottom = true;
    private BeautyOnButtonListener mBeautyOnButtonListener;

    @Override
    protected void initView() {
        mGridLayoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        beautyDataShow.setLayoutManager(mGridLayoutManager);
        mBeautyAdapter = new MultiTypeAdapter();
        mBeautyAdapter.register(BeautyBean.ResultsBean.class, new BeautyItemViewBinder());
        beautyDataShow.setAdapter(mBeautyAdapter);
        mBeautyOnButtonListener = new BeautyOnButtonListener();
        beautyDataShow.addOnScrollListener(mBeautyOnButtonListener);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_mei_zhi;
    }

    @Override
    protected void initData() {
        mBeanLists = new ArrayList<>();
        mIBeautyPersenter.onObtainData(PRELOAD_SIZE, mPage);
        beautyRefreshing.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Logger.d("是否在刷新：" + beautyRefreshing.isRefreshing());
                if (null != mBeanLists && mBeanLists.size() > 0) {
                    mBeanLists.clear();
                }
                mIBeautyPersenter.onObtainData(PRELOAD_SIZE, mPage);


            }
        });
    }

    @Override
    public void onResultSuccess(BeautyBean beautyBean) {
        mBean = beautyBean;
        mBeanLists.addAll(mBean.getResults());
        mBeautyAdapter.setItems(mBeanLists);
        mBeautyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResultFail(Exception e) {

    }

    @Override
    public void onRefresh(final int refresh) {
        if (beautyRefreshing.isRefreshing()) {
            beautyRefreshing.postDelayed(new Runnable() {
                @Override
                public void run() {
                    beautyRefreshing.setRefreshing(refresh == RefreshConstantField.REFRESHING
                            ? true : false);
                }
            }, RefreshConstantField.REFRESHING_DELAY_MILLIS);
        } else {
            beautyRefreshing.setRefreshing(refresh == RefreshConstantField.REFRESHING
                    ? true : false);
        }
    }

    @Override
    public void setPresenter(IBeautyContract.IBeautyPersenter presenter) {
        mIBeautyPersenter = presenter;
    }

    private class BeautyOnButtonListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            boolean isLastPostition = mGridLayoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1]
                    >= recyclerView.getAdapter().getItemCount() - PRELOAD_SIZE;
            if (!beautyRefreshing.isRefreshing() && isLastPostition) {
                if (!mIsFirstTimeTouchBottom) {
                    mIBeautyPersenter.onObtainData(PRELOAD_SIZE, ++mPage);
                } else {
                    mIsFirstTimeTouchBottom = false;
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        beautyDataShow.removeOnScrollListener(mBeautyOnButtonListener);
        super.onDestroy();

    }
}
