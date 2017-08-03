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
    /**
     * 找回密码接口
     * @param param 传入json数据
     * @return 返回结果信息
     */
    @POST("findPsw")
    Call<MyResponse> findPsw(
            @Body FindPswParam param
    );

    /**
     * 得到修改密码的验证码
     * @param phone 用户修改密码的手机号
     * @return 返回验证码
     */
    @FormUrlEncoded
    @POST("getFindPswVerCode")
    Call<MyResponse<String>> getFindPswVerCode(
            @Field("phone") String phone
    );
}
