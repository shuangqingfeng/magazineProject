package com.shuang.meiZhi.photoDetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

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

    @Override
    protected void initView(Bundle savedInstanceState) {
        mPhotoDetailsAdapter = new PhotoDetailsAdapter();
        photoDetails.setAdapter(mPhotoDetailsAdapter);
        mPhotoDetailsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        ArrayList<String> extra = intent.getStringArrayListExtra(PhotoDetailsActivity.EXTRA_IMAGE_URL);
        String extra1 = intent.getStringExtra(PhotoDetailsActivity.EXTRA_IMAGE_TITLE);
        mPhotoDetailsAdapter.setPhotos(extra);
    }

    public static Intent newIntent(Context context, List<String> images, String desc) {
        Intent intent = new Intent(context, PhotoDetailsActivity.class);
        intent.putStringArrayListExtra(PhotoDetailsActivity.EXTRA_IMAGE_URL, (ArrayList<String>) images);
        intent.putExtra(PhotoDetailsActivity.EXTRA_IMAGE_TITLE, desc);
        return intent;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_photo_details;
    }

}
