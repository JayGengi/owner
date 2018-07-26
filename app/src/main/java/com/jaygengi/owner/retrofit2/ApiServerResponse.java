package com.jaygengi.owner.retrofit2;

import com.ctrl.hshlife.entity.CateCafeteriaModel;
import com.ctrl.hshlife.entity.CommunityAdreeModel;
import com.ctrl.hshlife.entity.CommunityModel;
import com.ctrl.hshlife.entity.EquipmentModel;
import com.ctrl.hshlife.entity.HouseTopRoleModel;
import com.ctrl.hshlife.entity.MemberBindListModel;
import com.ctrl.hshlife.entity.Rental;
import com.ctrl.hshlife.entity.RentalDetail;
import com.sdwfqin.quicklib.utils.RxUtil;

import java.util.Map;

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
     * 公共无参返回方法[Activity]
     * */
    public void CommonPostinfo(Map<String, Object> map, RetrofitObserverA<ResponseHead> scheduler) {
        RetrofitFactory
                .getInstence()
                .API()
                .CommonPostinfo(map)
                .compose(RxUtil.rxObservableSchedulerHelper())
                .subscribe(scheduler);
    }
}

