package com.exercise.p.citicup.model;

import com.exercise.p.citicup.dto.FinaPreferInfo;
import com.exercise.p.citicup.dto.UserInfo;
import com.exercise.p.citicup.dto.response.MyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by p on 2017/9/13.
 */

public interface UserInfoModel {

    /**
     * 上传理财偏好
     *
     * @param info 偏好信息
     * @return 返回结果信息
     */
    @POST("setUserInfo")
    Call<MyResponse> setUserInfo(
            @Body UserInfo info
    );

    /**
     * 得到理财偏好
     *
     * @return 返回结果信息
     */
    @POST("getUserInfo")
    Call<MyResponse<UserInfo>> getUserInfo();

}
