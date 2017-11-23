package com.exercise.p.citicup.model;

import com.exercise.p.citicup.dto.Location;
import com.exercise.p.citicup.dto.response.MyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by p on 2017/9/13.
 */

public interface InsuTestModel {

    /**
     * 上传关键字
     *
     * @param keyword 关键字
     * @return 返回结果信息
     */
    @FormUrlEncoded
    @POST("setKeyword")
    Call<MyResponse> setKeyword(
            @Field("keyword") String keyword
    );

    /**
     * 得到关键字
     *
     * @return 返回结果信息
     */
    @POST("verTest")
    Call<MyResponse> verTest();


    @FormUrlEncoded
    @POST("setRegId")
    Call<MyResponse> setRegId(
            @Field("regId") String regId
    );

    @POST("savePlace")
    Call<MyResponse> savePlace(
            @Body Location location
    );

}
