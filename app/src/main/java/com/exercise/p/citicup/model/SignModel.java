package com.exercise.p.citicup.model;

import com.exercise.p.citicup.dto.Code;
import com.exercise.p.citicup.dto.FindPswParam;
import com.exercise.p.citicup.dto.SignInParam;
import com.exercise.p.citicup.dto.SignUpParam;
import com.exercise.p.citicup.dto.response.MyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by p on 2017/5/11.
 */

public interface SignModel {
    @POST("signIn")
    Call<MyResponse> signIn(
            @Body SignInParam param
    );

    @POST("signUp")
    Call<MyResponse> signUp(
            @Body SignUpParam param
    );

    @FormUrlEncoded
    @POST("getSignUpVerCode")
    Call<MyResponse<String>> getSignUpVerCode(
            @Field("phone") String phone
    );

}
