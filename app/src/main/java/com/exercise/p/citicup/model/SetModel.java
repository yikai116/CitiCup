package com.exercise.p.citicup.model;

import com.exercise.p.citicup.dto.FindPswParam;
import com.exercise.p.citicup.dto.response.MyResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by p on 2017/8/18.
 */

public interface SetModel {
    /**
     * 上传照片
     * @param param 传入json数据
     * @return 返回结果信息
     */
    @Multipart
    @POST("setAvatar")
    Call<MyResponse<String>> uploadAvatar(
            @Part MultipartBody.Part avatar
    );
}
