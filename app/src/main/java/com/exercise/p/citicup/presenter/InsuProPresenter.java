package com.exercise.p.citicup.presenter;

import android.util.Log;

import com.exercise.p.citicup.helper.Helper;
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

    public void getInsuPro(final boolean more) {
        Call<MyResponse<ArrayList<InsuPro>>> call = model.getInsuPro();
        call.enqueue(new Callback<MyResponse<ArrayList<InsuPro>>>() {
            @Override
            public void onResponse(Call<MyResponse<ArrayList<InsuPro>>> call, Response<MyResponse<ArrayList<InsuPro>>> response) {
                Log.i(Helper.TAG,"得到保险产品成功——" + response.body().getStatus().getCode());
                MyResponse<ArrayList<InsuPro>> response1 = response.body();
                if (response1.getStatus().getCode() == Helper.SUCCESS) {
                    view.initView(response1.getData(),more);
                } else {
                    view.showMessage(response1.getStatus().getMsg());
                }
            }

            @Override
            public void onFailure(Call<MyResponse<ArrayList<InsuPro>>> call, Throwable t) {
                t.printStackTrace();
                view.showMessage("网络连接错误");
            }
        });

    }
}

