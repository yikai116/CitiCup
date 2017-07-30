package com.exercise.p.citicup.dao;

import com.exercise.p.citicup.json.SignIn_Res;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by p on 2017/5/11.
 */

public interface SignModel {
    @POST("signIn")
    Call<SignIn_Res> sign_in(
            @Query("phone") String phone,
            @Query("psw") String psw
    );
    @POST("signUp")
    Call<SignIn_Res> sign_up(
            @Query("phone") String phone,
            @Query("psw") String psw,
            @Query("school_id") String school_id,
            @Query("name") String name,
            @Query("sex") String sex
    );
}
