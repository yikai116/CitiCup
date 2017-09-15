package com.exercise.p.citicup.presenter;

import android.util.Log;

import com.exercise.p.citicup.Helper;
import com.exercise.p.citicup.dto.InsuPro;
import com.exercise.p.citicup.dto.response.MyResponse;
import com.exercise.p.citicup.model.InsuProModel;
import com.exercise.p.citicup.model.RetrofitInstance;
import com.exercise.p.citicup.view.InsuFragView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by p on 2017/9/13.
 */

public class InsuProPresenter {
    InsuFragView view;
    InsuProModel model;

    public InsuProPresenter(InsuFragView view) {
        this.view = view;
        model = RetrofitInstance.getRetrofitWithToken().create(InsuProModel.class);

    }

    public void getInsuPro() {
        Call<MyResponse<ArrayList<InsuPro>>> call = model.getInsuPro();
        call.enqueue(new Callback<MyResponse<ArrayList<InsuPro>>>() {
            @Override
            public void onResponse(Call<MyResponse<ArrayList<InsuPro>>> call, Response<MyResponse<ArrayList<InsuPro>>> response) {
                MyResponse<ArrayList<InsuPro>> response1 = response.body();
                if (response1.getStatus().getCode() == Helper.SUCCESS) {
                    view.initView(response1.getData());
                } else {
                    view.showMessage(response1.getStatus().getMsg());
                }
            }

            @Override
            public void onFailure(Call<MyResponse<ArrayList<InsuPro>>> call, Throwable t) {
                Log.i("Test", call.request().url().toString());
                view.showMessage("网络连接错误");
            }
        });

    }
}

