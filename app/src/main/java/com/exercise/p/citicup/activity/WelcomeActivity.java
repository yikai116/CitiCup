package com.exercise.p.citicup.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.exercise.p.citicup.Helper;
import com.exercise.p.citicup.R;
import com.exercise.p.citicup.dto.UserInfo;
import com.exercise.p.citicup.dto.response.MyResponse;
import com.exercise.p.citicup.model.FpswModel;
import com.exercise.p.citicup.model.RetrofitInstance;
import com.exercise.p.citicup.model.WelcomeModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomeActivity extends AppCompatActivity {

    public static final int LOGIN = 2;
    public static final int NOTLOGIN = 3;
    SharedPreferences sharedPreferences;
    private int tag = NOTLOGIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //从手机内存中得到标识符
        sharedPreferences = getSharedPreferences("AppInfo", MODE_PRIVATE);
        Helper.IMEI = sharedPreferences.getString("IMEI", Helper.getIMEI(this));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("IMEI", Helper.IMEI);
        editor.apply();
        //去除登录
//        tag = LOGIN;
//        WelAsyncTask task = new WelAsyncTask();
//        task.execute();
        //请求验证标识符
        WelcomeModel welcomeModel = RetrofitInstance.getRetrofit().create(WelcomeModel.class);
        Log.i("Test",Helper.IMEI);
        Call<MyResponse<UserInfo>> call = welcomeModel.verToken(Helper.IMEI);
        call.enqueue(new Callback<MyResponse<UserInfo>>() {
            @Override
            public void onResponse(Call<MyResponse<UserInfo>> call, Response<MyResponse<UserInfo>> response) {
                Log.i("Test",response.body().getData().toString());
                if (response.body().getStatus().getCode() == 1){
                    tag = LOGIN;
                    Helper.userInfo = response.body().getData();
                }
                else {
                    tag = NOTLOGIN;
                    Toast.makeText(WelcomeActivity.this, response.body().getStatus().getMsg(), Toast.LENGTH_SHORT).show();
                }
                WelAsyncTask task = new WelAsyncTask();
                task.execute();
            }

            @Override
            public void onFailure(Call<MyResponse<UserInfo>> call, Throwable t) {
                Toast.makeText(WelcomeActivity.this, "网络连接错误", Toast.LENGTH_SHORT).show();
                tag = NOTLOGIN;
                WelAsyncTask task = new WelAsyncTask();
                task.execute();
            }
        });
    }

    /**
     * 欢迎界面等待任务，持续1s
     */
    private class WelAsyncTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i("Test", "tag: " + tag);
            return tag;
        }

        @Override
        protected void onPostExecute(Integer status) {
            super.onPostExecute(status);
            if (status == LOGIN && Helper.userInfo != null) {
                Intent intent = new Intent();
                intent.setClass(WelcomeActivity.this, MainActivity.class);
                WelcomeActivity.this.startActivity(intent);
            }
            else{
                Intent intent = new Intent();
                intent.setClass(WelcomeActivity.this, SignActivity.class);
                WelcomeActivity.this.startActivity(intent);
            }
            WelcomeActivity.this.finish();
        }
    }

}
