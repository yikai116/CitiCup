package com.exercise.p.citicup.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.exercise.p.citicup.dto.Location;
import com.exercise.p.citicup.dto.response.MyResponse;
import com.exercise.p.citicup.helper.Helper;
import com.exercise.p.citicup.model.InsuTestModel;
import com.exercise.p.citicup.model.RetrofitInstance;
import com.exercise.p.citicup.view.MainView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by p on 2017/9/13.
 */

public class MainPresenter {
    MainView view;
    InsuTestModel testModel;

    public MainPresenter(MainView view) {
        this.view = view;
        testModel = RetrofitInstance.getRetrofitWithToken().create(InsuTestModel.class);
    }

    public void verTest() {
        view.showDialog("正在初始化...");
        Call<MyResponse> call = testModel.verTest();
        call.enqueue(new Callback<MyResponse>() {

            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                Log.i(Helper.TAG,"初始化验证测试成功——" + response.code());
                MyResponse response1 = response.body();
                if (response1.getStatus().getCode() == Helper.SUCCESS) {
                    view.showNotTest(false);
                } else {
                    view.showNotTest(true);
                }
                view.dismissDialog();
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                t.printStackTrace();
                view.dismissDialog();
                view.showMessage("网络连接错误");
            }
        });
    }

    public void setRegId(Context context){
        SharedPreferences preferences = context.getSharedPreferences("AppInfo", context.MODE_PRIVATE);
        final String regId = preferences.getString("regId",null);
        if (regId == null)
            return;
        Call<MyResponse> regCall = testModel.setRegId(regId);
        regCall.enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                Log.i(Helper.TAG,"发送RegID成功：" + regId);
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void savePlace(final double latitude, final double longitude){
        Location location = new Location();
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        Call<MyResponse> regCall = testModel.savePlace(location);
        regCall.enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                Log.i(Helper.TAG,"定位发送成功——latitude: " + latitude + "  longitude: " + longitude);
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
