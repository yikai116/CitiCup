package com.exercise.p.citicup.presenter;

import android.util.Log;

import com.exercise.p.citicup.helper.Helper;
import com.exercise.p.citicup.dto.SignInParam;
import com.exercise.p.citicup.dto.SignUpParam;
import com.exercise.p.citicup.dto.User;
import com.exercise.p.citicup.dto.response.MyResponse;
import com.exercise.p.citicup.model.RetrofitInstance;
import com.exercise.p.citicup.model.SignModel;
import com.exercise.p.citicup.view.SignView;

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

    /**
     * 登录
     * @param phone 用户手机号
     * @param psw 用户密码
     */
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
        Call<MyResponse<User>> signInResCall = signModel.signIn(param);
        signInResCall.enqueue(new Callback<MyResponse<User>>() {
            @Override
            public void onResponse(Call<MyResponse<User>> call, Response<MyResponse<User>> response) {
                Log.i("Test","success" + call.request().url().toString());
                MyResponse<User> response1 = response.body();
                if (response1.getStatus().getCode() == Helper.SUCCESS) {
                    Helper.user = response1.getData();
                    signView.toMainActivity();
                }
                else if (response1.getStatus().getCode() == Helper.NO_USER){
                    signView.setSignInPhoneError(response1.getStatus().getMsg());
                    signView.showProgress(false);
                }
                else if (response1.getStatus().getCode() == Helper.PSW_ERROR){
                    signView.setSignInPswError(response1.getStatus().getMsg());
                    signView.showProgress(false);
                }
                else {
                    signView.showMessage(response1.getStatus().getMsg());
                    signView.showProgress(false);
                }
            }
            @Override
            public void onFailure(Call<MyResponse<User>> call, Throwable t) {
                Log.i("Test",call.request().url().toString());
                t.printStackTrace();
                signView.showProgress(false);
                signView.showMessage("网络连接错误");
            }
        });

    }

    /**
     * 注册
     * @param phone 用户手机号
     * @param psw 用户密码
     * @param psw_re 用户密码确认
     * @param verCode 验证码
     */
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
        Call<MyResponse<User>> call = signModel.signUp(param);
        call.enqueue(new Callback<MyResponse<User>>() {
            @Override
            public void onResponse(Call<MyResponse<User>> call, Response<MyResponse<User>> response) {
                Log.i("Test",call.request().url().toString());
                MyResponse<User> response1 = response.body();
                if (response1.getStatus().getCode() == Helper.SUCCESS) {
                    Helper.user = response1.getData();
                    signView.toMainActivity();
                }
                else if (response1.getStatus().getCode() == Helper.USER_REGISTERED){
                    signView.setSignUpPhoneError(response1.getStatus().getMsg());
                    signView.showProgress(false);
                }
                else if (response1.getStatus().getCode() == Helper.VERCODE_ERROR){
                    signView.setSignUpConError(response1.getStatus().getMsg());
                    signView.showProgress(false);
                }
                else {
                    signView.showMessage(response1.getStatus().getMsg());
                    signView.showProgress(false);
                }
            }

            @Override
            public void onFailure(Call<MyResponse<User>> call, Throwable t) {
                Log.i("Test",call.request().url().toString());
                signView.showProgress(false);
                signView.showMessage("网络连接错误");
            }
        });
    }

    /**
     * 得到注册验证码
     * @param phone 用户手机号
     */
    public void getSignUpVerCode(String phone){
        if (!isPhoneValid(phone)) {
            signView.setSignUpPhoneError("用户名格式错误");
            signView.cancelGetVerCode();
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
                signView.cancelGetVerCode();
            }
        });
    }

    /**
     * 判断手机号是否合格
     * @param phone 手机号
     * @return 是否合格
     */
    private boolean isPhoneValid(String phone) {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * 判断面是否小于6位
     * @param password 密码
     * @return 是否小于6位
     */
    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }
}
