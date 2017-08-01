package com.exercise.p.citicup.model;

import com.exercise.p.citicup.dto.FindPswParam;
import com.exercise.p.citicup.dto.SignInParam;
import com.exercise.p.citicup.dto.response.MyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by p on 2017/8/1.
 */

public interface FpswModel {
    @POST("findPsw")
    Call<MyResponse> findPsw(
            @Body FindPswParam param
    );

    @FormUrlEncoded
    @POST("getFindPswVerCode")
    Call<MyResponse> getFindPswVerCode(
            @Field("phone") String phone
    );
}
