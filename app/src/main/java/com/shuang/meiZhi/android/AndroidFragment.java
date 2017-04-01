package com.shuang.meiZhi.android;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.shuang.meiZhi.MainActivity;
import com.shuang.meiZhi.base.BaseFragment;
import com.shuang.meiZhi.R;
import com.shuang.meiZhi.adapter.AndroidItemViewBinder;
import com.shuang.meiZhi.constantPool.RefreshConstantField;
import com.shuang.meiZhi.entity.AndroidBean;
import com.shuang.meiZhi.event.OnTouchEventClickListener;
import com.shuang.meiZhi.photoDetails.PhotoDetailsActivity;
import com.shuang.meiZhi.utils.PhoneStatusUtils;
import com.shuang.meiZhi.utils.ToastUtils;
import com.shuang.meiZhi.utils.UIUtils;
import com.shuang.meiZhi.webView.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.drakeet.multitype.MultiTypeAdapter;
import rx.Subscription;

/**
 * @author feng
 * @Description: android 的数据展示页面
 * @date 2017/3/22
 */
public class AndroidFragment extends BaseFragment implements IAndroidContract.IAndroidView {
    private static final int PRELOAD_SIZE = 10;
    @BindView(R.id.srl_swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.rv_androidRecyclerView)
    RecyclerView androidRecyclerView;
    private IAndroidContract.IAndroidPresenter presenter;
    private MultiTypeAdapter androidAdapter;
    private boolean mIsFirstTimeTouchBottom = true;
    private onBottomScrollListener mOnBottomScrollListener;
    private int mPage = 1;
    private List<AndroidBean.ResultsBean> mAndroidBeenLists;
    private AndroidBean mAndroidBean;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected int getContentView() {
        return R.layout.fragment_android;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        androidRecyclerView.setLayoutManager(mLinearLayoutManager);
        androidAdapter = new MultiTypeAdapter();
        androidAdapter.register(AndroidBean.ResultsBean.class, new AndroidItemViewBinder());
        androidRecyclerView.setAdapter(androidAdapter);
        mOnBottomScrollListener = new onBottomScrollListener();
        androidRecyclerView.addOnScrollListener(mOnBottomScrollListener);
        AndroidItemViewBinder.setOnTouchEventClickListener(getOnTouchEventClickListener());
    }


    @Override
    protected void initData() {
        mAndroidBeenLists = new ArrayList<>();

        presenter.onObtainData(PRELOAD_SIZE, mPage);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (null != mAndroidBeenLists && mAndroidBeenLists.size() > 0) {
                    mAndroidBeenLists.clear();
                }
                presenter.onObtainData(PRELOAD_SIZE, mPage);
            }
        });

    }

    @Override
    public void setPresenter(IAndroidContract.IAndroidPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showMessage(String msg) {
        ToastUtils.show(msg);
    }

    @Override
    public void onRefresh(final int refresh) {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.postDelayed(new Runnable() {
                @Override
                public void run() {
                    swipeRefresh.setRefreshing(refresh == RefreshConstantField.REFRESHING
                            ? true : false);
                }
            }, RefreshConstantField.REFRESHING_DELAY_MILLIS);
        } else {
            swipeRefresh.setRefreshing(refresh == RefreshConstantField.REFRESHING
                    ? true : false);
        }
    }

    @Override
    public void onResultSuccess(AndroidBean androidBean) {
        mAndroidBean = androidBean;
        if (androidAdapter != null) {
            mAndroidBeenLists.addAll(androidBean.getResults());
            androidAdapter.setItems(mAndroidBeenLists);
            androidAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onResultFail(Throwable e) {
        showMessage(e.toString());
    }

    private void startPictureActivity(AndroidBean.ResultsBean object, View shot) {
        Intent intent = PhotoDetailsActivity.newIntent(getActivity(), object.getImages(), object.getDesc());
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                getActivity(), shot, PhotoDetailsActivity.TRANSIT_PIC);
        try {
            ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            startActivity(intent);
        }
    }


    private class onBottomScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            boolean isBottomed = mLinearLayoutManager.findLastVisibleItemPosition()
                    >= recyclerView.getAdapter().getItemCount() - 1;
            if (!swipeRefresh.isRefreshing() && isBottomed) {
                if (!mIsFirstTimeTouchBottom) {
                    Logger.d("----上啦加载");
                    if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_SETTLING) {
                        presenter.onObtainData(PRELOAD_SIZE, ++mPage);
                    }
                } else {
                    Logger.d("---第一次触摸底部");
                    mIsFirstTimeTouchBottom = false;
                }
            }
        }
    }

    @NonNull
    private OnTouchEventClickListener<AndroidBean.ResultsBean> getOnTouchEventClickListener() {
        return new OnTouchEventClickListener<AndroidBean.ResultsBean>() {
            @Override
            public void onEventClick(AndroidBean.ResultsBean object, View... views) {
                View view = views[0];
                View itemView = views[1];
                View screenShot = views[2];
                if (itemView == null) return;
                if (null != itemView && view.getId() == itemView.getId() && null != object.getUrl()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title", object.getDesc());
                    bundle.putString("detailsUrl", object.getUrl());
                    UIUtils.startActivity(getActivity(), WebViewActivity.class, bundle);
                } else if (null != screenShot && view.getId() == screenShot.getId() && null != object.getImages()) {
                    startPictureActivity(object, screenShot);
                } else {
                    ToastUtils.show("抱歉，数据丢失");
                }
            }
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != androidRecyclerView) {
            androidRecyclerView.removeOnScrollListener(mOnBottomScrollListener);
        }
        if (null != presenter.getCompositeSuscription()) {
            presenter.getCompositeSuscription().unsubscribe();
        }
    }

}
