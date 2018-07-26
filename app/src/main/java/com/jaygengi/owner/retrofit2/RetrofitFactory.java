package com.jaygengi.owner.retrofit2;


import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.jaygengi.owner.properties.StaticParam;

import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 描述：Retrofit工厂模式
 *
 * @author JayGengi
 * @date 2018/7/17 0017 上午 10:16
 */
public class RetrofitFactory {

    private final ApiServer mApiService;

    private static RetrofitFactory mRetrofitFactory;

    public static RetrofitFactory getInstence() {
        if (mRetrofitFactory == null) {
            synchronized (RetrofitFactory.class) {
                if (mRetrofitFactory == null)
                    mRetrofitFactory = new RetrofitFactory();
            }
        }
        return mRetrofitFactory;
    }

    private RetrofitFactory() {


        //创建Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.baidu.com")
                .addConverterFactory(GsonConverterFactory.create())//添加gson转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rxjava2转换器
                .client(initOkhttpClient())// 设置OkHttpclient
                .build();

        //创建接口实现类
        mApiService = retrofit.create(ApiServer.class);

    }

    /**
     * 每次请求都会走拦截器
     * <p>
     * 只需要修改Constants.TOKEN就可以
     */
    private OkHttpClient initOkhttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 通过拦截器添加除method以外的系统级参数
        builder.addInterceptor(chain -> {
            Request request = chain.request();
            if (request.method().equals("POST")) {
                if (request.body() instanceof FormBody) {
                    FormBody.Builder bodyBuilder = new FormBody.Builder();
                    FormBody formBody = (FormBody) request.body();

                    //把原来的参数添加到新的构造器
                    for (int i = 0; i < formBody.size(); i++) {
                        bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                    }
                    formBody = bodyBuilder.build();
                    LogUtils.d("addRequest"+ new Gson().toJson(formBody.toString()));
                    request = request.newBuilder().post(formBody).build();
                }
            }
            return chain.proceed(request);
        });
        builder.connectTimeout(StaticParam.HTTP_TIME, TimeUnit.SECONDS)
                .readTimeout(StaticParam.HTTP_TIME, TimeUnit.SECONDS)
                .writeTimeout(StaticParam.HTTP_TIME, TimeUnit.SECONDS)
                .build();
        if (BuildConfig.DEBUG) {
            // OkHttp日志拦截器
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        return builder.build();
    }

    public ApiServer API() {
        return mApiService;
    }

    public static RequestBody stringToRequestBody(String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), value);
        return requestBody;
    }
}
