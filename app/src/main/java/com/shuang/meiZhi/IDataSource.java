package com.shuang.meiZhi;

/**
 * @author feng
 * @Description:
 * @date 2017/3/24
 */
public interface IDataSource {
    interface LoadDataSourceCallBack {
        void loadDataSource();

        void loadDataNOAvailable();
    }

    interface LoadResultSourceCallBack<T> {
        void onResult(T object);

        void onResultNoAvailable();
    }

    void loadDataSource(int size, int pag, LoadResultSourceCallBack loadResultSourceCallBack);

}
