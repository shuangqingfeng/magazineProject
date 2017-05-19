package com.shuang.meiZhi;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import com.shuang.meiZhi.utils.FileUtils;
import com.shuang.meiZhi.utils.StatusBarUtils;
import com.shuang.meiZhi.utils.ToastUtils;
import com.shuang.meiZhi.utils.UIUtils;
import com.shuang.meiZhi.video.VideoFragment;
import com.shuang.meiZhi.view.AlertDialogView;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity {
    private static final String ANDROID_FRAGMENT_TAG = "androidFragment";
    private static final String IOS_FRAGMENT_TAG = "iosFragment";
    private static final String MEI_ZHI_FRAGMENT_TAG = "meiZhiFragment";
    private static final String VIDEO_FRAGMENT_TAG = "videoFragment";
    private static final int PHOTO_REQUEST_CAMERA = 1; // 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;//返回结果
    private static final String PHOTO_FILE_NAME = "temp_avatar.jpg";
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
                        openCarmera();
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
                    AndPermission.with(MainActivity.this).requestCode(200)
                            .permission(Manifest.permission.CAMERA)
                            .callback(listener).start();
//                    alertDialogView.dismiss();
                }

                @Override
                public void clickPhoto() {
                    // 激活系统图库，选择一张图片
                    openGallery();
                    alertDialogView.dismiss();
                }

                @Override
                public void clickMore() {
                    ToastUtils.show("更多多功能敬请期待");
                    alertDialogView.dismiss();

                }
            });
        }
    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。

            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if (requestCode == 200) {
                // TODO ...
                ToastUtils.show("权限申请成功");
                openCarmera();
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == 200) {
                // TODO ...
                ToastUtils.show("拒绝使用相机");
            }
        }
    };

    // 成功回调的方法，用注解即可，这里的300就是请求时的requestCode。
    @PermissionYes(300)
    private void getPermissionYes(List<String> grantedPermissions) {

    }

    @PermissionNo(300)
    private void getPermissionNo(List<String> deniedPermissions) {

    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    private void openCarmera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        File imagePath = new File(getFilesDir(), "images");
//        Logger.d("---------imagePath：" + imagePath);
//        File newFile = new File(imagePath, "default_image.jpg");
//        Logger.d("---------newFile：" + newFile.getPath() + "----------" + newFile.getAbsolutePath());
        Uri contentUri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contentUri = FileProvider.getUriForFile(MainActivity.this.getApplicationContext(), "com.shuang.meiZhi.fileprovider"
                    , FileUtils.getExternalFilesDir(this, Environment.DIRECTORY_MOVIES, PHOTO_FILE_NAME));
        } else {
            contentUri = getUriFile();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        startActivityForResult(intent, PHOTO_REQUEST_CAMERA);

//        Intent intent = new Intent(
//                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        // 打开照相机,设置请求码

//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"temp.jpg")));
//        startActivityForResult(intent, PHOTO_REQUEST_CAMERA);

//        Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        //下面这句指定调用相机拍照后的照片存储的路径
//        takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME)));
//        startActivityForResult(takeIntent, PHOTO_REQUEST_CAMERA);
    }

    private Uri getUriFile() {
        return Uri.fromFile(FileUtils.getExternalFilesDir(this, Environment.DIRECTORY_MOVIES, PHOTO_FILE_NAME));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case PHOTO_REQUEST_CAMERA:
                    Uri contentUri = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        contentUri = FileProvider.getUriForFile(MainActivity.this.getApplicationContext(), "com.shuang.meiZhi.fileprovider"
                                , FileUtils.getExternalFilesDir(this, Environment.DIRECTORY_MOVIES, PHOTO_FILE_NAME));
                    } else {
                        contentUri = getUriFile();
                    }
                    crop(contentUri);
                    break;
                case PHOTO_REQUEST_GALLERY:
                    // 从相册返回的数据
                    if (data != null) {
                        // 得到图片的全路径
                        Uri uri = data.getData();

                        crop(uri);
                    }
                    break;
                case PHOTO_REQUEST_CUT:
                    if (data != null) {
                        getImageToView(data);
                    }
                    break;
            }
        }
    }

    /**
     * 剪切图片
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG);// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriFile());
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    /**
     * 保存裁剪之后的图片数据
     */
    private void getImageToView(Intent data) {
        Bitmap bitmap = data.getParcelableExtra("data");
        if (bitmap != null) {
            saveMyBitmap(bitmap); // 保存裁剪后的图片到SD
            avatarImg.setImageBitmap(bitmap);
        }
    }

    /**
     * 将头像保存在SDcard
     */
    public void saveMyBitmap(Bitmap bitmap) {
        File f = FileUtils.getExternalFilesDir(this, Environment.DIRECTORY_MOVIES, PHOTO_FILE_NAME);
        try {
            if (f.exists())
                f.delete();
            f.createNewFile();
            FileOutputStream fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        // 其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(this.getContentResolver(),
//                    f.getAbsolutePath(), PHOTO_FILE_NAME, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//         最后通知图库更新
//        sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ getExternalFilesDir(Environment.DIRECTORY_MOVIES))));
//        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, getUriFile()));
//        try {
//            MediaStore.Images.Media.insertImage(getContentResolver(), f.getAbsolutePath(), PHOTO_FILE_NAME, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
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
