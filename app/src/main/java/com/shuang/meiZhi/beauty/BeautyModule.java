package com.shuang.meiZhi.beauty;

import com.shuang.meiZhi.IDataSource;
import com.shuang.meiZhi.entity.BeautyBean;

import javax.sql.DataSource;

/**
 * @author feng
 * @Description: 妹子页面的数据请求类
 * @date 2017/3/27
 */

public class BeautyModule implements IDataSource<BeautyBean> {
    private static BeautyModule beautyModule;

    public static BeautyModule getInstance() {
        if (null == beautyModule) {
            beautyModule = new BeautyModule();
        }
        return beautyModule;
    }

    @Override
    public void loadDataSource(int size, int pag, LoadResultSourceCallBack<BeautyBean> oadResultSourceCallBack) {

    }
}
