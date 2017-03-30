package com.shuang.meiZhi.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shuang.meiZhi.R;
import com.shuang.meiZhi.entity.AndroidBean;
import com.shuang.meiZhi.entity.BeautyBean;
import com.shuang.meiZhi.entity.IOSBean;
import com.shuang.meiZhi.event.OnTouchEventClickListener;
import com.shuang.meiZhi.utils.GlideBitmapLoadUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;


/**
 * @author feng
 * @Description: ios 数据适配器
 * @date 2017/3/23
 */
public class IosItemViewBinder extends ItemViewBinder<IOSBean.ResultsBean, IosItemViewBinder.AndroidViewHolder> {

    private static OnTouchEventClickListener mClickListener;

    @NonNull
    @Override
    protected AndroidViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_android, null, false);
        AndroidViewHolder androidViewHolder = new AndroidViewHolder(root);
        return androidViewHolder;
    }

    @Override
    protected void onBindViewHolder(@NonNull AndroidViewHolder holder, @NonNull IOSBean.ResultsBean item) {
        holder.mBean = item;
        GlideBitmapLoadUtils.loadIntoImageView(holder.screenShot, item.getImages() != null ? item.getImages().get(0) : null);
        holder.author.setText(item.getWho());
        holder.dataTime.setText(item.getCreatedAt());
        holder.describe.setText(item.getDesc());
    }

    public static void setOnTouchEventClick(OnTouchEventClickListener onTouchEventClick) {
        mClickListener = onTouchEventClick;
    }

    static class AndroidViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View view;
        @BindView(R.id.iv_screenShot)
        ImageView screenShot;
        @BindView(R.id.tv_author)
        TextView author;
        @BindView(R.id.tv_dataTime)
        TextView dataTime;
        @BindView(R.id.tv_describe)
        TextView describe;
        IOSBean.ResultsBean mBean;

        public AndroidViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
            view.setOnClickListener(this);
            screenShot.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onEventClick(mBean, v, view, screenShot);
        }
    }
}
