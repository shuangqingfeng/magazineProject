package com.shuang.meiZhi.event;

import android.view.View;

/**
 * @author feng
 * @Description: 设配器数据的点击事件
 * @date 2017/3/30
 */
public interface OnTouchEventClickListener<T> {
    void onEventClick(T object, View... views);
}
