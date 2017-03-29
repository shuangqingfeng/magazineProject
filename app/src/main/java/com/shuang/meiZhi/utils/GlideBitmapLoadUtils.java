package com.shuang.meiZhi.utils;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * @author feng
 * @Description: 对图片加载框架 Glide 的二次封装 ，避免以后要更换图片加载框架。
 * @date 2017/3/29
 */
public class GlideBitmapLoadUtils {
    public static void loadIntoImageView(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(TextUtils.isEmpty(url) ? null : url)
                .centerCrop()
                .into(view);
    }
}
