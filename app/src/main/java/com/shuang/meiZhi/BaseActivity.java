package com.shuang.meiZhi;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getContentView());
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mBar = getSupportActionBar();
        initData();
    }

    protected abstract void initData();

    abstract int getContentView();

    protected void setAppBarUpEnabled(boolean showHomeAsUp) {
        mBar.setDisplayHomeAsUpEnabled(showHomeAsUp);
        mBar.setDisplayShowHomeEnabled(showHomeAsUp);
    }

    protected void setToolbatTitle(String toolbatTitle) {
        mToolbar.setTitle(toolbatTitle);
    }



    @Override
    public boolean onSupportNavigateUp() {
        return true;
    }
}
