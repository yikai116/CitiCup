package com.exercise.p.citicup.test;

import android.util.Log;

import com.exercise.p.citicup.dto.response.MyResponse;
import com.exercise.p.citicup.model.RetrofitInstance;
import com.exercise.p.citicup.model.SignModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;

/**
 * Created by p on 2017/8/1.
 */
public class SignModelTest {
    private SignModel signModel = RetrofitInstance.getRetrofit().create(SignModel.class);

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void signIn() throws Exception {

    }

    @Test
    public void signUp() throws Exception {

    }

    @Test
    public void findPsw() throws Exception {

    }
    @Test
    public void getSignUpVerCode() throws Exception {
        Call<MyResponse> call = signModel.getSignUpVerCode("15196649548");
        System.out.println("23");
        call.enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                Log.i("Test","success" + call.request().url().toString());
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                System.out.println(call.request().url().toString());
                System.out.println(call.request().body().toString());
            }
        });

        System.out.println("32");
    }

    @Test
    public void getFindPswVerCode() throws Exception {

    }

}