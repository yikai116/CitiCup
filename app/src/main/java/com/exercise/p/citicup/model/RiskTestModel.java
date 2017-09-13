package com.exercise.p.citicup.model;

import com.exercise.p.citicup.dto.FinaPreferInfo;
import com.exercise.p.citicup.dto.FindPswParam;
import com.exercise.p.citicup.dto.InsuPreferInfo;
import com.exercise.p.citicup.dto.response.MyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by p on 2017/9/13.
 */

public interface RiskTestModel {
    /**
     * 上传测试分数
     *
     * @param sco 分数
     * @return 返回结果信息
     */
    @FormUrlEncoded
    @POST("setAbility")
    Call<MyResponse> setAbility(
            @Field("sco") int sco
    );

    /**
     * 得到测试分数
     *
     * @return 返回结果信息
     */
    @POST("getAbility")
    Call<MyResponse> getAbility();


}
