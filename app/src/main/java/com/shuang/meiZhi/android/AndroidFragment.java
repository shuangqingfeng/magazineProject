package com.shuang.meiZhi.android;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.orhanobut.logger.Logger;
import com.shuang.meiZhi.BaseFragment;
import com.shuang.meiZhi.R;
import com.shuang.meiZhi.adapter.AndroidItemViewBinder;
import com.shuang.meiZhi.entity.AndroidBean;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author feng
 * @Description: fragment 的基类
 * @date 2017/3/22
 */
public class AndroidFragment extends BaseFragment implements IAndroidContract.IAndroidView {
    @BindView(R.id.srl_swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.rv_androidRecyclerView)
    RecyclerView androidRecyclerView;
    private IAndroidContract.IAndroidPresenter presenter;
    private MultiTypeAdapter androidAdapter;
    private List<AndroidBean.ResultsBean> results;
    private Calendar calendar;

    @Override
    protected int getContentView() {
        return R.layout.fragment_android;
    }

    @Override
    protected void initView() {
        androidRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        androidRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        androidAdapter = new MultiTypeAdapter();
        androidAdapter.register(AndroidBean.ResultsBean.class, new AndroidItemViewBinder());
        androidRecyclerView.setAdapter(androidAdapter);
    }

    @Override
    protected void initData() {

        if (null != presenter) {
            presenter.onObtainData(10, 1);
        }
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.onObtainData(10, 1);
            }
        });
    }

    @Override
    public void setPresenter(IAndroidContract.IAndroidPresenter presenter) {
        this.presenter = presenter;

    }

    @Override
    public void onRefresh(final int refresh) {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.postDelayed(new Runnable() {
                @Override
                public void run() {
                    swipeRefresh.setRefreshing(refresh == 1 ? true : false);
                }
            }, 1000);
        } else {
            swipeRefresh.setRefreshing(refresh == 1 ? true : false);
        }
    }

    @Override
    public void onResultSuccess(AndroidBean androidBean) {
        if (androidAdapter != null) {
            results = androidBean.getResults();
            androidAdapter.setItems(results);
            Logger.d("------------" + androidAdapter.getItemCount());
            androidAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onResultFail(Exception e) {

    }

}
