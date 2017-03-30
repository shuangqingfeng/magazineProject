package com.shuang.meiZhi.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shuang.meiZhi.R;
import com.shuang.meiZhi.entity.BeautyBean;
import com.shuang.meiZhi.event.OnTouchEventClickListener;
import com.shuang.meiZhi.utils.GlideBitmapLoadUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author feng
 * @Description: 妹纸福利页面的数据适配器
 * @date 2017/3/28
 */
public class BeautyItemViewBinder extends ItemViewBinder<BeautyBean.ResultsBean, BeautyItemViewBinder.BeautyViewHolder> {
    private static OnTouchEventClickListener clickListener;

    @NonNull
    @Override
    protected BeautyViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_beauty, parent, false);
        BeautyViewHolder beautyViewHolder = new BeautyViewHolder(view);
        return beautyViewHolder;
    }

    public static void setOnTouchEventListener(OnTouchEventClickListener onTouchEventListener) {
        clickListener = onTouchEventListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull BeautyViewHolder holder, @NonNull BeautyBean.ResultsBean item) {
        holder.mBean = item;
        GlideBitmapLoadUtils.loadIntoImageView(holder.beautyBitmap, item.getUrl());
    }

    static class BeautyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_beautyBitmap)
        ImageView beautyBitmap;
        BeautyBean.ResultsBean mBean;

        public BeautyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            beautyBitmap.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onEventClick(mBean, v, beautyBitmap);
        }
    }
}
