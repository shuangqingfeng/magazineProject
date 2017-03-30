package com.shuang.meiZhi.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.shuang.meiZhi.R;
import com.shuang.meiZhi.photoDetails.PhotoDetailsActivity;
import com.shuang.meiZhi.utils.GlideBitmapLoadUtils;
import com.shuang.meiZhi.utils.UIUtils;

import java.util.List;

import static com.shuang.meiZhi.photoDetails.PhotoDetailsActivity.TRANSIT_PIC;

/**
 * @author feng
 * @Description: 图片的详情页面
 * @date 2017/3/30
 */
public class PhotoDetailsAdapter extends PagerAdapter {
    private List<String> photos;

    @Override
    public int getCount() {
        return null != photos && photos.size() > 0 ? photos.size() : 0;
    }

    public void setPhotos(List<String> list) {
        photos = list;
        this.notifyDataSetChanged();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(UIUtils.getContext());
        ViewCompat.setTransitionName(imageView, PhotoDetailsActivity.TRANSIT_PIC);
        GlideBitmapLoadUtils.loadIntoImageView(imageView, photos.get(position));
        container.addView(imageView);
        return imageView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
