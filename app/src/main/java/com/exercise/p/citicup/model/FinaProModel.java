package com.exercise.p.citicup.model;

import com.exercise.p.citicup.dto.FinaPro;
import com.exercise.p.citicup.dto.response.MyResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by p on 2017/9/13.
 */

public interface FinaProModel {

    /**
     * 得到理财产品信息
     *
     * @return 返回结果信息
     */
    @POST("getFinaPro")
    Call<MyResponse<ArrayList<FinaPro>>> getFinaPro();

}
