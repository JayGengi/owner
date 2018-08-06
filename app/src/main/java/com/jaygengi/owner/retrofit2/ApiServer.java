package com.jaygengi.owner.retrofit2;


import com.jaygengi.owner.model.GankImgModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
/**
 * 描述：网络请求类（一个接口一个方法）
 * @author JayGengi
 * @date 2018/8/6 0006 下午 5:00
 */
public interface ApiServer {
    /**
     * gank.io開源福利圖片集合接口
     */
    @GET("data/{type}/{rows}/{page}")
    Observable<ResponseHead<GankImgModel>> getGankImg(@Path("type") String data, @Path("rows") int page, @Path("page") int rows);

}

