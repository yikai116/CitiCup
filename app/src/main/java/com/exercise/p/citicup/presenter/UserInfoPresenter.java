package com.exercise.p.citicup.presenter;

import com.exercise.p.citicup.helper.Helper;
import com.exercise.p.citicup.dto.UserInfo;
import com.exercise.p.citicup.dto.response.MyResponse;
import com.exercise.p.citicup.model.RetrofitInstance;
import com.exercise.p.citicup.model.UserInfoModel;
import com.exercise.p.citicup.view.UserInfoView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by p on 2017/9/15.
 */

public class UserInfoPresenter {
    UserInfoModel model;
    UserInfoView view;

    public UserInfoPresenter(UserInfoView view) {
        this.view = view;
        model = RetrofitInstance.getRetrofitWithToken().create(UserInfoModel.class);
    }

    public void getDate() {
        Call<MyResponse<UserInfo>> call = model.getUserInfo();
        call.enqueue(new Callback<MyResponse<UserInfo>>() {
            @Override
            public void onResponse(Call<MyResponse<UserInfo>> call, Response<MyResponse<UserInfo>> response) {
                if (response.body().getStatus().getCode() == Helper.SUCCESS) {
                    Helper.info = response.body().getData();
                    view.initData();
                } else
                    view.showMessage("网络连接错误");
            }

            @Override
            public void onFailure(Call<MyResponse<UserInfo>> call, Throwable t) {
                view.showMessage("网络连接错误");
            }
        });
    }

    public void setUserInfo() {
        view.showDialog("正在提交...");
        Call<MyResponse> call = model.setUserInfo(Helper.info);
        call.enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.body().getStatus().getCode() == Helper.SUCCESS)
                    view.showMessage("提交成功");
                else
                    view.showMessage("网络连接错误");
                view.dismissDialog();
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                view.showMessage("网络连接错误");
                view.dismissDialog();
            }
        });
    }
}
