package com.shuang.meiZhi.dataApi;

import com.shuang.meiZhi.entity.AndroidBean;
import com.shuang.meiZhi.entity.IOSBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author feng
 * @Description:
 * @date 2017/3/23
 */
public interface MeiZhiApi {
    //    http://gank.io/api/data/数据类型/请求个数/第几页
    @GET("data/Android/{size}/{pag}")
    Observable<AndroidBean> getAndroidData(@Path("size") int size, @Path("pag") int pag);

    @GET("data/iOS/{size}/{pag}")
    Observable<IOSBean> getIosData(@Path("size") int size, @Path("pag") int pag);
}
