package com.exercise.p.citicup.model;

import com.exercise.p.citicup.dto.SignInParam;
import com.exercise.p.citicup.dto.SignUpParam;
import com.exercise.p.citicup.dto.User;
import com.exercise.p.citicup.dto.response.MyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by p on 2017/5/11.
 */

public interface SignModel {
    /**
     * 登录
     * @param param 用户信息
     * @return 返回结果信息
     */
    @POST("signIn")
    Call<MyResponse<User>> signIn(
            @Body SignInParam param
    );

    /**
     * 注册
     * @param param 用户信息
     * @return 返回结果信息
     */
    @POST("signUp")
    Call<MyResponse<User>> signUp(
            @Body SignUpParam param
    );

    /**
     * 得到注册时的验证码
     * @param phone 用户手机号
     * @return 返回结果信息
     */
    @FormUrlEncoded
    @POST("getSignUpVerCode")
    Call<MyResponse<String>> getSignUpVerCode(
            @Field("phone") String phone
    );

}
