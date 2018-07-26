package com.jaygengi.owner.retrofit2;


import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiServer {
    /**
     * 公共无参返回方法
     */
    @FormUrlEncoded
    @POST("")
    Observable<ResponseHead> CommonPostinfo(@FieldMap Map<String, Object> options);

}

