package com.shuang.meiZhi.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author feng
 * @Description: fragment 的基类 统一管理 fragment
 * @date 2017/3/22
 */
public abstract class BaseFragment extends Fragment {

    private Unbinder mButterUnbinder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(), container, false);
        mButterUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(savedInstanceState);
        initData();
    }


    protected abstract void initView(Bundle savedInstanceState);

    protected abstract int getContentView();

    protected abstract void initData();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mButterUnbinder) {
            mButterUnbinder.unbind();
        }
    }

}
