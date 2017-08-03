package com.exercise.p.citicup.presenter;

import android.util.Log;

import com.exercise.p.citicup.Helper;
import com.exercise.p.citicup.dto.Code;
import com.exercise.p.citicup.dto.SignInParam;
import com.exercise.p.citicup.dto.SignUpParam;
import com.exercise.p.citicup.dto.response.MyResponse;
import com.exercise.p.citicup.model.RetrofitInstance;
import com.exercise.p.citicup.model.SignModel;
import com.exercise.p.citicup.view.SignView;
import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by p on 2017/7/17.
 */

public class SignPresenter {
    private SignView signView;
    private SignModel signModel;

    public SignPresenter(SignView view) {
        signView = view;
        signModel = RetrofitInstance.getRetrofit().create(SignModel.class);
    }

    public void signIn(String phone, String psw) {
        if (!isPhoneValid(phone)) {
            signView.setSignInPhoneError("用户名格式错误");
            return;
        }
        if (!isPasswordValid(psw)) {
            signView.setSignInPswError("密码格式错误");
            return;
        }
        SignInParam param = new SignInParam();
        param.setPhone(phone);
        try {
            param.setPsw(Helper.md5Encode(psw));
        } catch (Exception e) {
            e.printStackTrace();
        }
        param.setToken(Helper.IMEI);
        signView.showProgress(true);
        Call<MyResponse> signInResCall = signModel.signIn(param);
        signInResCall.enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                Log.i("Test","success" + call.request().url().toString());
                signView.showProgress(false);
                MyResponse response1 = response.body();
                if (response1.getStatus().getCode() == Helper.SUCCESS) {
                    signView.toMainActivity();
                }
                else if (response1.getStatus().getCode() == Helper.NO_USER){
                    signView.setSignInPhoneError(response1.getStatus().getMsg());
                }
                else if (response1.getStatus().getCode() == Helper.PSW_ERROR){
                    signView.setSignInPswError(response1.getStatus().getMsg());
                }
                else {
                    signView.showMessage(response1.getStatus().getMsg());
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                Log.i("Test",call.request().url().toString());
                signView.showProgress(false);
                signView.showMessage("网络连接错误");
            }
        });

    }

    public void signUp(String phone, String psw, String psw_re, String verCode) {
        if (!isPhoneValid(phone)) {
            signView.setSignUpPhoneError("用户名格式错误");
            return;
        }
        if (!isPasswordValid(psw)) {
            signView.setSignUpPswError("密码格式错误");
            return;
        }
        if (!psw_re.equals(psw)) {
            signView.setSignUpPswReError("两次密码不匹配");
            return;
        }
        SignUpParam param = new SignUpParam();
        param.setPhone(phone);
        try {
            param.setPsw(Helper.md5Encode(psw));
        } catch (Exception e) {
            e.printStackTrace();
        }
        param.setToken(Helper.IMEI);
        param.setVerCode(verCode);
        signView.showProgress(true);
        Call<MyResponse> call = signModel.signUp(param);
        call.enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                Log.i("Test",call.request().url().toString());
                MyResponse response1 = response.body();
                signView.showProgress(false);
                if (response1.getStatus().getCode() == Helper.SUCCESS) {
                    signView.toMainActivity();
                }
                else if (response1.getStatus().getCode() == Helper.USER_REGISTERED){
                    signView.setSignUpPhoneError(response1.getStatus().getMsg());
                }
                else if (response1.getStatus().getCode() == Helper.VERCODE_ERROR){
                    signView.setSignUpConError(response1.getStatus().getMsg());
                }
                else {
                    signView.showMessage(response1.getStatus().getMsg());
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                Log.i("Test",call.request().url().toString());
                signView.showProgress(false);
                signView.showMessage("网络连接错误");
            }
        });
    }

    public void getSignUpVerCode(String phone){
        if (!isPhoneValid(phone)) {
            signView.setSignUpPhoneError("用户名格式错误");
            return;
        }
        Call<MyResponse<String>> call = signModel.getSignUpVerCode(phone);
        call.enqueue(new Callback<MyResponse<String>>() {
            @Override
            public void onResponse(Call<MyResponse<String>> call, Response<MyResponse<String>> response) {
                Log.i("Test","success" + call.request().url().toString());
                Log.i("Test",response.body().getData() != null ? response.body().getData() : "null");
                if (response.body().getStatus().getCode() == Helper.SUCCESS) {
                    signView.showMessage(response.body().getData());
                } else if (response.body().getStatus().getCode() == Helper.USER_REGISTERED){
                    signView.setSignUpPhoneError(response.body().getStatus().getMsg());
                }
                else {
                    signView.showMessage(response.body().getStatus().getMsg());
                }
            }

            @Override
            public void onFailure(Call<MyResponse<String>> call, Throwable t) {
                Log.i("Test",call.request().url().toString());
                signView.showProgress(false);
                signView.showMessage("网络连接错误");
                signView.cancelSignUpGetVerCode();
            }
        });
    }

    private boolean isPhoneValid(String phone) {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }
}
