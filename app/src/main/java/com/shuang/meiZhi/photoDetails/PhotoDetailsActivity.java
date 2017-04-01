package com.shuang.meiZhi.photoDetails;

import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.shuang.meiZhi.R;
import com.shuang.meiZhi.adapter.PhotoDetailsAdapter;
import com.shuang.meiZhi.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author feng
 * @Description: 展示截屏 以及妹纸图片的预览 这个页面是根据 drakkeet 项目来写的
 * @date 2017/3/30
 */
public class PhotoDetailsActivity extends BaseActivity {
    public static final String EXTRA_IMAGE_URL = "image_url";
    public static final String EXTRA_IMAGE_TITLE = "image_title";
    public static final String TRANSIT_PIC = "picture";
    @BindView(R.id.vp_photoDetails)
    ViewPager photoDetails;
    private PhotoDetailsAdapter mPhotoDetailsAdapter;
    private ArrayList<String> imageUrl;
    private String titleText;

    @Override
    protected void initToolbar(Toolbar toolbar) {
        Intent intent = getIntent();
        imageUrl = intent.getStringArrayListExtra(PhotoDetailsActivity.EXTRA_IMAGE_URL);
        titleText = intent.getStringExtra(PhotoDetailsActivity.EXTRA_IMAGE_TITLE);
        if (null != titleText) {
            toolbar.setTitle(titleText);
        } else {
            finish();
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mPhotoDetailsAdapter = new PhotoDetailsAdapter();
        photoDetails.setAdapter(mPhotoDetailsAdapter);
        mPhotoDetailsAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected void initData() {
        if (null != imageUrl) {
            mPhotoDetailsAdapter.setPhotos(imageUrl);
        } else {
            finish();
        }

    }

    public static Intent newIntent(Context context, List<String> images, String desc) {
        Intent intent = new Intent(context, PhotoDetailsActivity.class);
        intent.putStringArrayListExtra(PhotoDetailsActivity.EXTRA_IMAGE_URL, (ArrayList<String>) images);
        intent.putExtra(PhotoDetailsActivity.EXTRA_IMAGE_TITLE, desc);
        return intent;
    }

    @Override
    protected int getContainerId() {
        return R.layout.activity_photo_details;
    }

}
