package com.exercise.p.citicup.model;

import com.exercise.p.citicup.dto.FinaPreferInfo;
import com.exercise.p.citicup.dto.InsuPreferInfo;
import com.exercise.p.citicup.dto.response.MyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.POST;

/**
 * Created by p on 2017/9/13.
 */

public interface FinaPreferModel {

    /**
     * 上传理财偏好
     *
     * @param info 偏好信息
     * @return 返回结果信息
     */
    @POST("setFinaPrefer")
    Call<MyResponse> setFinaPrefer(
            @Body FinaPreferInfo info
    );

    /**
     * 得到理财偏好
     *
     * @return 返回结果信息
     */
    @POST("getFinaPrefer")
    Call<MyResponse<FinaPreferInfo>> getFinaPrefer();

}
