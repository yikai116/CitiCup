package com.exercise.p.citicup.model;

import com.exercise.p.citicup.dto.response.MyResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by p on 2017/11/23.
 */

public interface ProModel {

    @FormUrlEncoded
    @POST("clickInsuPro")
    Call<MyResponse> clickInsuPro(
            @Field("id") int id
    );
}
