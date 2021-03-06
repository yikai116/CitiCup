package com.exercise.p.citicup.presenter;

import android.util.Log;

import com.exercise.p.citicup.helper.Helper;
import com.exercise.p.citicup.dto.InsuPreferInfo;
import com.exercise.p.citicup.dto.response.MyResponse;
import com.exercise.p.citicup.model.InsuPreferModel;
import com.exercise.p.citicup.model.RetrofitInstance;
import com.exercise.p.citicup.view.ShowDialogView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by p on 2017/9/13.
 */

public class InsuPreferPresenter {
    ShowDialogView view;
    InsuPreferModel model;

    public InsuPreferPresenter(ShowDialogView view){
        this.view = view;
        model = RetrofitInstance.getRetrofitWithToken().create(InsuPreferModel.class);
    }

    public void submit(InsuPreferInfo info){
        view.showDialog("正在提交...");
        Call<MyResponse> call = model.setInsuPrefer(info);
        call.enqueue(new Callback<MyResponse>(){

            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                Log.i(Helper.TAG,"提交保险偏好——" + response.body().getStatus().getCode());
                MyResponse response1 = response.body();
                view.dismissDialog();
                if (response1.getStatus().getCode()== Helper.SUCCESS) {
                    view.showMessage("提交成功");
                    view.myFinish(true);
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                t.printStackTrace();
                view.dismissDialog();
                view.showMessage("网络连接错误");
            }
        });
    }
}
