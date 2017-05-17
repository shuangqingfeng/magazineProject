package com.shuang.meiZhi.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;
import com.shuang.meiZhi.R;
import com.shuang.meiZhi.utils.StatusBarUtils;
import com.shuang.meiZhi.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * @author feng
 * @Description: activity 的基类
 * @date 2017/3/22
 */
public abstract class BaseActivity extends AppCompatActivity implements BGASwipeBackHelper.Delegate {
    @BindView(R.id.abl_appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private ActionBar mBar;
    private Unbinder mButterrKnifeUnbinder;
    protected BGASwipeBackHelper mSwipeBackHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
        setContentView(getContainerId());
        mButterrKnifeUnbinder = ButterKnife.bind(this);
        setStatusBarColor();
        if (null != mToolbar) {
            initToolbar(mToolbar);
            setSupportActionBar(mToolbar);
        }
        mBar = getSupportActionBar();
        mBar.setElevation(0f);
        if (canBack()) {
            if (mBar != null) mBar.setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activityFinish();
                }
            });
        }
        initView(savedInstanceState);
        initData();
    }

    /**
     * 结束当前页面，如需携带返回自，子类重写此方法
     */
    private void activityFinish() {
        finish();
    }

    protected void setStatusBarColor() {
        StatusBarUtils.setColor(this, UIUtils.getColor(R.color.colorPrimary), Color.TRANSPARENT);
    }

    protected void transparent19and20() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 是否需要返回键
     *
     * @return 默认 fasle 不显示  true 显示返回键
     */
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

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackManager.getInstance().init(this) 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {
    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel() {
    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();
    }

    @Override
    public void onBackPressed() {
//        // 正在滑动返回的时候取消返回按钮事件
//        if (mSwipeBackHelper.isSliding()) {
//            return;
//        }
//        mSwipeBackHelper.backward();
        super.onBackPressed();
    }


}
