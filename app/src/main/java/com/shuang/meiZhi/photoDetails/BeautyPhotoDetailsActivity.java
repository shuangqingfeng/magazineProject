package com.shuang.meiZhi.photoDetails;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.shuang.meiZhi.R;
import com.shuang.meiZhi.adapter.PhotoDetailsAdapter;
import com.shuang.meiZhi.base.BaseActivity;
import com.shuang.meiZhi.utils.GlideBitmapLoadUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author feng
 * @Description: 展示截屏 以及妹纸图片的预览 这个页面是根据 drakkeet 项目来写的
 * @date 2017/3/30
 */
public class BeautyPhotoDetailsActivity extends BaseActivity {
    public static final String EXTRA_IMAGE_URL = "image_url";
    public static final String EXTRA_IMAGE_TITLE = "image_title";
    public static final String TRANSIT_PIC = "picture";
    @BindView(R.id.iv_photoDetails)
    ImageView photoDetails;
    private PhotoDetailsAdapter mPhotoDetailsAdapter;

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        ViewCompat.setTransitionName(photoDetails, PhotoDetailsActivity.TRANSIT_PIC);
        Intent intent = getIntent();
        String extra = intent.getStringExtra(BeautyPhotoDetailsActivity.EXTRA_IMAGE_URL);
        String extra1 = intent.getStringExtra(BeautyPhotoDetailsActivity.EXTRA_IMAGE_TITLE);
        GlideBitmapLoadUtils.loadIntoImageView(photoDetails,extra);
    }

    public static Intent newIntent(Context context, String images, String desc) {
        Intent intent = new Intent(context, BeautyPhotoDetailsActivity.class);
        intent.putExtra(BeautyPhotoDetailsActivity.EXTRA_IMAGE_URL, images);
        intent.putExtra(BeautyPhotoDetailsActivity.EXTRA_IMAGE_TITLE, desc);
        return intent;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_beauty_photo_details;
    }

}
