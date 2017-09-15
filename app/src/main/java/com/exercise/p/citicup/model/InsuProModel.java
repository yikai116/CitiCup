package com.exercise.p.citicup.model;

import com.exercise.p.citicup.dto.FinaPreferInfo;
import com.exercise.p.citicup.dto.InsuPro;
import com.exercise.p.citicup.dto.response.MyResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by p on 2017/9/13.
 */

public interface InsuProModel {

    /**
     * 得到保险产品信息
     *
     * @return 返回结果信息
     */
    @POST("getInsuPro")
    Call<MyResponse<ArrayList<InsuPro>>> getInsuPro();

}
