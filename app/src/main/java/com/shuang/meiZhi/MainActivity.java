package com.shuang.meiZhi;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.shuang.meiZhi.android.AndroidFragment;
import com.shuang.meiZhi.android.AndroidModule;
import com.shuang.meiZhi.android.AndroidPresenter;
import com.shuang.meiZhi.beauty.MeiZhiFragment;
import com.shuang.meiZhi.ios.IosModule;
import com.shuang.meiZhi.ios.IosPresenter;
import com.shuang.meiZhi.ios.IosFragment;
import com.shuang.meiZhi.video.VideoFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    private static final String ANDROID_FRAGMENT_TAG = "androidFragment";
    private static final String IOS_FRAGMENT_TAG = "iosFragment";
    private static final String MEI_ZHI_FRAGMENT_TAG = "meiZhiFragment";
    private static final String VIDEO_FRAGMENT_TAG = "videoFragment";
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
    private AndroidFragment androidFragment;
    private IosFragment iosFragment;
    private MeiZhiFragment meiZhiFragment;
    private VideoFragment videoFragment;
    private FragmentManager fragmentManager;
    private AndroidPresenter androidPresenter;
    private IosPresenter mIosPresenter;

    @Override
    int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        if (itemAndroid.isChecked()) {
            itemAndroid.setChecked(true);
        }
        switchFragmentShow(0);
        checkItem.setOnCheckedChangeListener(new CheckItemChangeListener());
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
                    meiZhiFragment = new MeiZhiFragment();
                    fragmentTransaction.add(R.id.fl_mainContainer, meiZhiFragment, MEI_ZHI_FRAGMENT_TAG);
                } else {
                    fragmentTransaction.show(meiZhiFragment);
                }
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
}
