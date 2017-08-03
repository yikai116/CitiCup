package com.exercise.p.citicup.model;

import com.exercise.p.citicup.dto.SignInParam;
import com.exercise.p.citicup.dto.response.MyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by p on 2017/8/2.
 */

public interface WelcomeModel {
    /**
     * 验证标识符
     * @param token 标识符
     * @return 返回信息
     */
    @FormUrlEncoded
    @POST("verToken")
    Call<MyResponse> verToken(
            @Field("token") String token
    );
}
