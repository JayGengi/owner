package com.jaygengi.owner.retrofit2;

import com.ctrl.baselib.utils.RxUtil;
import com.jaygengi.owner.model.GankImgModel;


/**
 * 描述：网络请求response接口统一管理类
 * @author JayGengi
 * @date 2018/7/19 0019 上午 10:19
 */
public class ApiServerResponse {
    private static ApiServerResponse apiServerResponse;

    public static ApiServerResponse getInstence() {
        if (apiServerResponse == null) {
            synchronized (ApiServerResponse.class) {
                if (apiServerResponse == null)
                    apiServerResponse = new ApiServerResponse();
            }
        }
        return apiServerResponse;
    }
    /**
     * gank.io開源福利圖片集合接口
     * */
    public void getGankImg(String type,int rows,int page, RetrofitObserver<GankImgModel> scheduler) {
        RetrofitFactory
                .getInstence()
                .API()
                .getGankImg(type,rows,page)
                .compose(RxUtil.rxObservableSchedulerHelper())
                .subscribe(scheduler);
    }
}

