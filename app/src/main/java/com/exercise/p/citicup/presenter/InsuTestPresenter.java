package com.exercise.p.citicup.presenter;

import android.util.Log;

import com.exercise.p.citicup.Helper;
import com.exercise.p.citicup.dto.response.MyResponse;
import com.exercise.p.citicup.model.InsuTestModel;
import com.exercise.p.citicup.model.RetrofitInstance;
import com.exercise.p.citicup.view.ShowDialogView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by p on 2017/9/13.
 */

public class InsuTestPresenter {
    ShowDialogView view;
    InsuTestModel model;

    public InsuTestPresenter(ShowDialogView view){
        this.view = view;
        model = RetrofitInstance.getRetrofitWithToken().create(InsuTestModel.class);
    }

    public void submit(String keyword){
        view.showDialog("正在提交...");
        Call<MyResponse> call = model.setKeyword(keyword);
        call.enqueue(new Callback<MyResponse>(){

            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                MyResponse response1 = response.body();
                view.dismissDialog();
                if (response1.getStatus().getCode() == Helper.SUCCESS) {
                    view.showMessage("提交成功");
                    view.myFinish(true);
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                Log.i("Test",call.request().url().toString());
                view.dismissDialog();
                view.showMessage("网络连接错误");
            }
        });
    }
}
