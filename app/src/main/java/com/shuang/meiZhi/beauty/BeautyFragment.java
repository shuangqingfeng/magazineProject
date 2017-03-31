package com.shuang.meiZhi.beauty;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;
import com.shuang.meiZhi.adapter.BeautyItemViewBinder;
import com.shuang.meiZhi.base.BaseFragment;
import com.shuang.meiZhi.R;
import com.shuang.meiZhi.constantPool.RefreshConstantField;
import com.shuang.meiZhi.entity.AndroidBean;
import com.shuang.meiZhi.entity.BeautyBean;
import com.shuang.meiZhi.event.OnTouchEventClickListener;
import com.shuang.meiZhi.photoDetails.BeautyPhotoDetailsActivity;
import com.shuang.meiZhi.photoDetails.PhotoDetailsActivity;
import com.shuang.meiZhi.utils.ToastUtils;
import com.shuang.meiZhi.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.drakeet.multitype.MultiTypeAdapter;

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
    protected void initView(Bundle savedInstanceState) {
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
        if (null != mIBeautyPersenter) {
            mIBeautyPersenter.onObtainData(PRELOAD_SIZE, mPage);
        }
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
        BeautyItemViewBinder.setOnTouchEventListener(getOnTouchEventClickListener());
    }


    @Override
    public void onResultSuccess(BeautyBean beautyBean) {
        mBean = beautyBean;
        mBeanLists.addAll(mBean.getResults());
        mBeautyAdapter.setItems(mBeanLists);
        mBeautyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResultFail(Throwable throwable) {
        showMessage(throwable.toString());
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

    @Override
    public void showMessage(String msg) {
        ToastUtils.show(msg);
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

    @NonNull
    private OnTouchEventClickListener<BeautyBean.ResultsBean> getOnTouchEventClickListener() {
        return new OnTouchEventClickListener<BeautyBean.ResultsBean>() {
            @Override
            public void onEventClick(BeautyBean.ResultsBean object, View... views) {
                View view = views[0];
                ImageView imageView = (ImageView) views[1];
                if (imageView == null) {
                    return;
                }
                if (view.getId() == imageView.getId()) {
                    startPictureActivity(object, imageView);
                }
            }


        };
    }

    private void startPictureActivity(BeautyBean.ResultsBean object, View shot) {
        Intent intent = BeautyPhotoDetailsActivity.newIntent(getActivity(), object.getUrl(), object.getDesc());
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                getActivity(), shot, PhotoDetailsActivity.TRANSIT_PIC);
        try {
            ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != beautyDataShow) {
            beautyDataShow.removeOnScrollListener(mBeautyOnButtonListener);
        }
        if (null != mIBeautyPersenter.getCompositeSuscription()) {
            mIBeautyPersenter.getCompositeSuscription().unsubscribe();
        }

    }
}
