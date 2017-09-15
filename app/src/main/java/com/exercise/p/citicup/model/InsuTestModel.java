package com.exercise.p.citicup.model;

import com.exercise.p.citicup.dto.FinaPreferInfo;
import com.exercise.p.citicup.dto.InsuPreferInfo;
import com.exercise.p.citicup.dto.response.MyResponse;

import java.util.ArrayList;

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
            @Field("keyword") ArrayList<String> keyword
    );

    /**
     * 得到关键字
     *
     * @return 返回结果信息
     */
    @POST("getKeyword")
    Call<MyResponse> getKeyword();

}
