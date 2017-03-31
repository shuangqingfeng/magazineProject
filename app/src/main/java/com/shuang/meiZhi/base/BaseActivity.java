package com.shuang.meiZhi.base;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.shuang.meiZhi.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author feng
 * @Description: activity 的基类
 * @date 2017/3/22
 */
public abstract class BaseActivity extends AppCompatActivity {
    @BindView(R.id.abl_appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private ActionBar mBar;
    private Unbinder mButterrKnifeUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getContainerId());
        mButterrKnifeUnbinder = ButterKnife.bind(this);
        if (null != mToolbar) {
            initToolbar(mToolbar);
            setSupportActionBar(mToolbar);

        }
        if (canBack()) {
            mBar = getSupportActionBar();
            if (mBar != null) mBar.setDisplayHomeAsUpEnabled(true);
            if (Build.VERSION.SDK_INT >= 21) {
                mBar.setElevation(10.6f);
            }
        }

        initView(savedInstanceState);
        initData();
    }

    public boolean canBack() {
        return false;
    }

    protected abstract void initToolbar(Toolbar toolbar);

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();

    protected abstract int getContainerId();

    protected void setAppBarUpEnabled(boolean showHomeAsUp) {
        mBar.setDisplayHomeAsUpEnabled(showHomeAsUp);
        mBar.setDisplayShowHomeEnabled(showHomeAsUp);
    }


    @Override
    public boolean onSupportNavigateUp() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mButterrKnifeUnbinder) {
            mButterrKnifeUnbinder.unbind();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
