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

public interface InsuPreferModel {


    /**
     * 上传保险偏好
     *
     * @param info 偏好信息
     * @return 返回结果信息
     */
    @POST("setInsuPrefer")
    Call<MyResponse> setInsuPrefer(
            @Body InsuPreferInfo info
    );

    /**
     * 得到保险偏好
     *
     * @return 返回结果信息
     */
    @POST("getInsuPrefer")
    Call<MyResponse> getInsuPrefer();

}
