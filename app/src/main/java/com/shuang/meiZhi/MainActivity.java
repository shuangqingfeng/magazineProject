package com.shuang.meiZhi;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.shuang.meiZhi.android.AndroidFragment;
import com.shuang.meiZhi.android.AndroidModule;
import com.shuang.meiZhi.android.AndroidPresenter;
import com.shuang.meiZhi.base.BaseActivity;
import com.shuang.meiZhi.beauty.BeautyModule;
import com.shuang.meiZhi.beauty.BeautyPresenter;
import com.shuang.meiZhi.beauty.BeautyFragment;
import com.shuang.meiZhi.ios.IosModule;
import com.shuang.meiZhi.ios.IosPresenter;
import com.shuang.meiZhi.ios.IosFragment;
import com.shuang.meiZhi.utils.StatusBarUtils;
import com.shuang.meiZhi.utils.ToastUtils;
import com.shuang.meiZhi.utils.UIUtils;
import com.shuang.meiZhi.video.VideoFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    private static final String ANDROID_FRAGMENT_TAG = "androidFragment";
    private static final String IOS_FRAGMENT_TAG = "iosFragment";
    private static final String MEI_ZHI_FRAGMENT_TAG = "meiZhiFragment";
    private static final String VIDEO_FRAGMENT_TAG = "videoFragment";
    @BindView(R.id.dl_drawLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.fl_mainContainer)
    FrameLayout mineContainer;
    @BindView(R.id.rg_checkItem)
    RadioGroup checkItem;
    @BindView(R.id.rb_itemAndroid)
    RadioButton itemAndroid;
    @BindView(R.id.rb_itemIos)
    RadioButton itemIos;
    @BindView(R.id.rb_itemBeauty)
    RadioButton itemBeauty;
    @BindView(R.id.rb_itemVideo)
    RadioButton itemVideo;
    @BindView(R.id.ngv_drawView)
    NavigationView navigationView;
    private AndroidFragment androidFragment;
    private IosFragment iosFragment;
    private BeautyFragment meiZhiFragment;
    private VideoFragment videoFragment;
    private FragmentManager fragmentManager;
    private AndroidPresenter androidPresenter;
    private IosPresenter mIosPresenter;
    private long mExitTme = 0;

    @Override
    protected int getContainerId() {

        return R.layout.activity_main;
    }

    @Override
    protected void initToolbar(Toolbar toolbar) {
        toolbar.setTitle(UIUtils.getString(R.string.app_name));
//        toolbar.setlog
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        navigationView.setItemIconTintList(null);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
//        StatusBarUtils.setColorForDrawerLayout(MainActivity.this,mDrawerLayout,255);
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected void initData() {
        if (itemAndroid.isChecked()) {
            itemAndroid.setChecked(true);
        }
        switchFragmentShow(0);
        checkItem.setOnCheckedChangeListener(new CheckItemChangeListener());
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_thems:
                        ToastUtils.show("更换皮肤");
                        break;
                    case R.id.menu_nightMode:
                        ToastUtils.show("夜间模式");
                        break;
                }
                return true;
            }
        });
    }

    class CheckItemChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_itemAndroid:
                    switchFragmentShow(0);
                    break;
                case R.id.rb_itemIos:
                    switchFragmentShow(1);
                    break;
                case R.id.rb_itemBeauty:
                    switchFragmentShow(2);
                    break;
                case R.id.rb_itemVideo:
                    switchFragmentShow(3);
                    break;
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    public void switchFragmentShow(int id) {

        if (null == fragmentManager) {
            fragmentManager = getSupportFragmentManager();
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        switch (id) {
            case 0:
                if (null == androidFragment) {
                    androidFragment = new AndroidFragment();
                    fragmentTransaction.add(R.id.fl_mainContainer, androidFragment, ANDROID_FRAGMENT_TAG);
                } else {
                    fragmentTransaction.show(androidFragment);
                }
                if (null == androidPresenter) {
                    androidPresenter = new AndroidPresenter(androidFragment, AndroidModule.getInstance());
                }
                break;
            case 1:
                if (null == iosFragment) {
                    iosFragment = new IosFragment();
                    fragmentTransaction.add(R.id.fl_mainContainer, iosFragment, IOS_FRAGMENT_TAG);
                } else {
                    fragmentTransaction.show(iosFragment);
                }
                if (null == mIosPresenter) {
                    mIosPresenter = new IosPresenter(iosFragment, IosModule.getInstance());
                }
                break;
            case 2:
                if (null == meiZhiFragment) {
                    meiZhiFragment = new BeautyFragment();
                    fragmentTransaction.add(R.id.fl_mainContainer, meiZhiFragment, MEI_ZHI_FRAGMENT_TAG);
                } else {
                    fragmentTransaction.show(meiZhiFragment);
                }
                BeautyPresenter beautyPresenter = new BeautyPresenter(meiZhiFragment, BeautyModule.getInstance());
                break;
            case 3:
                if (null == videoFragment) {
                    videoFragment = new VideoFragment();
                    fragmentTransaction.add(R.id.fl_mainContainer, videoFragment, VIDEO_FRAGMENT_TAG);
                } else {
                    fragmentTransaction.show(videoFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }


    /**
     * 隐藏正在显示的Fragment 防止重叠显示
     *
     * @param fragmentTransaction 事物管理
     */
    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (androidFragment != null) {
            fragmentTransaction.hide(androidFragment);
        }
        if (iosFragment != null) {
            fragmentTransaction.hide(iosFragment);
        }
        if (meiZhiFragment != null) {
            fragmentTransaction.hide(meiZhiFragment);
        }
        if (videoFragment != null) {
            fragmentTransaction.hide(videoFragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mDrawerLayout.isDrawerOpen(navigationView)) {
            mDrawerLayout.closeDrawers();
            return true;
        } else if (System.currentTimeMillis() - mExitTme > 2000) {
            ToastUtils.show("在按一次退出程序");
            mExitTme = System.currentTimeMillis();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
