package com.shuang.meiZhi;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.orhanobut.logger.Logger;
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
import com.shuang.meiZhi.view.AlertDialogView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity {
    private static final String ANDROID_FRAGMENT_TAG = "androidFragment";
    private static final String IOS_FRAGMENT_TAG = "iosFragment";
    private static final String MEI_ZHI_FRAGMENT_TAG = "meiZhiFragment";
    private static final String VIDEO_FRAGMENT_TAG = "videoFragment";
    private static final String SAVE_AVATORNAME = "avatar.png";// 保存的图片名
    private static final int CAMERA_REQUEST_CODE = 1; // 拍照
    private static final int RESULT_REQUEST_CODE = 2; // 裁剪
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
    private CircleImageView avatarImg;
    private ActionBarDrawerToggle mToggle;


    @Override
    protected int getContainerId() {
        return R.layout.activity_main;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void initToolbar(Toolbar toolbar) {
        toolbar.setTitle(UIUtils.getString(R.string.app_name));
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(mToggle);
        mToggle.syncState();

//        toolbar.setlog
    }

    @Override
    protected void setStatusBarColor() {
        StatusBarUtils.setColorForDrawerLayout(MainActivity.this, mDrawerLayout,
                UIUtils.getColor(R.color.colorPrimary), Color.TRANSPARENT);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        navigationView.setItemIconTintList(null);
        View navigationHeaderView = navigationView.getHeaderView(0);
        avatarImg = (CircleImageView) navigationHeaderView.findViewById(R.id.avatar_image);
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
        avatarImg.setOnClickListener(new AvatarImgClickListener());
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

    /**
     * 更换头像
     */
    class AvatarImgClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (mDrawerLayout.isDrawerOpen(navigationView)) {
                mDrawerLayout.closeDrawers();
            }
            final AlertDialogView alertDialogView = new AlertDialogView(MainActivity.this, false).builder();
            alertDialogView.show();
            alertDialogView.setDialogChildAtViewClickListener(new AlertDialogView.DialogChildAtViewClickListener() {
                @Override
                public void clickCarmera() {
                    openCarmera();
                    alertDialogView.dismiss();
                }

                @Override
                public void clickPhoto() {

                    alertDialogView.dismiss();
                }

                @Override
                public void clickMore() {
                    ToastUtils.show("敬请期待");
                    alertDialogView.dismiss();

                }
            });
        }
    }

    private void openCarmera() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment
                .getExternalStorageDirectory(), SAVE_AVATORNAME)));
        startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    startPhotoZoom(Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(), SAVE_AVATORNAME)));
                    break;
                case RESULT_REQUEST_CODE:
                    if (data != null) {
                        getImageToView(data);
                    }
                    break;
            }
        }
    }

    /**
     * 裁剪图片方法实现
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    /**
     * 保存裁剪之后的图片数据
     */
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(Environment
                .getExternalStorageDirectory(), SAVE_AVATORNAME))));
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            saveMyBitmap(photo); // 保存裁剪后的图片到SD
            avatarImg.setImageBitmap(photo);
        }
    }

    /**
     * 将头像保存在SDcard
     */
    public void saveMyBitmap(Bitmap bitmap) {
        File f = new File(Environment.getExternalStorageDirectory(), SAVE_AVATORNAME);
        try {
            f.createNewFile();
            FileOutputStream fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
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
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);

        } else if (System.currentTimeMillis() - mExitTme > 2000) {
            ToastUtils.show("在按一次退出程序");
            mExitTme = System.currentTimeMillis();
        } else {
            this.finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }
    /**
     * 菜单键点击的事件处理
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

}
