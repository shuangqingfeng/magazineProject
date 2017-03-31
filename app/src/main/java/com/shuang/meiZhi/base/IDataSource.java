package com.shuang.meiZhi.base;

import rx.Subscription;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * @author feng
 * @Description:
 * @date 2017/3/24
 */
public interface IDataSource<T> {
    interface LoadDataSourceCallBack {
        void loadDataSource();

        void loadDataNOAvailable();
    }

    interface LoadResultSourceCallBack<T> {
        void onResult(T object);

        void onResultNoAvailable(Throwable throwable);
    }

    Subscription loadDataSource(int size, int pag, LoadResultSourceCallBack<T> oadResultSourceCallBack);

}
