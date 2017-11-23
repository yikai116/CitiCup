package com.exercise.p.citicup.presenter;

import android.util.Log;

import com.exercise.p.citicup.helper.Helper;
import com.exercise.p.citicup.dto.FinaPro;
import com.exercise.p.citicup.dto.response.MyResponse;
import com.exercise.p.citicup.model.FinaProModel;
import com.exercise.p.citicup.model.RetrofitInstance;
import com.exercise.p.citicup.view.FinaFragView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by p on 2017/9/13.
 */

public class FinaProPresenter {
    FinaFragView view;
    FinaProModel model;

    public FinaProPresenter(FinaFragView view) {
        this.view = view;
        model = RetrofitInstance.getRetrofitWithToken().create(FinaProModel.class);
    }

    public void getFinaPro(final boolean more) {
        Call<MyResponse<ArrayList<FinaPro>>> call = model.getFinaPro(more);
        call.enqueue(new Callback<MyResponse<ArrayList<FinaPro>>>() {
            @Override
            public void onResponse(Call<MyResponse<ArrayList<FinaPro>>> call, Response<MyResponse<ArrayList<FinaPro>>> response) {
                Log.i(Helper.TAG,"得到理财产品信息——" + response.body().getStatus().getCode());
                MyResponse<ArrayList<FinaPro>> response1 = response.body();
                if (response1.getStatus().getCode() == Helper.SUCCESS) {
                    view.initView(response1.getData(),more);
                } else {
                    view.showMessage(response1.getStatus().getMsg());
                }
            }

            @Override
            public void onFailure(Call<MyResponse<ArrayList<FinaPro>>> call, Throwable t) {
                t.printStackTrace();
                view.showMessage("网络连接错误");
            }
        });

    }

}

