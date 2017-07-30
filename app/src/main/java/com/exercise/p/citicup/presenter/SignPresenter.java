package com.exercise.p.citicup.presenter;

import com.exercise.p.citicup.dao.RetrofitInstance;
import com.exercise.p.citicup.dao.SignModel;
import com.exercise.p.citicup.view.SignView;
import com.exercise.p.citicup.json.SignIn_Res;

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

    public SignPresenter(SignView view){
        signView = view;
        signModel = RetrofitInstance.getRetrofit().create(SignModel.class);
    }

    public void signIn(String phone, String psw){
        if (!isPhoneValid(phone)){
            signView.setSignInPhoneError("用户名格式错误");
            return;
        }
        if (!isPasswordValid(psw)){
            signView.setSignInPswError("密码格式错误");
            return;
        }
        signView.showProgress(true);
        Call<SignIn_Res> signInResCall = signModel.sign_in(phone,psw);
        signInResCall.enqueue(new Callback<SignIn_Res>() {
            @Override
            public void onResponse(Call<SignIn_Res> call, Response<SignIn_Res> response) {
                signView.showProgress(false);
                if (response.body().isIsOk())
                    signView.toMainActivity();
                else
                    signView.setSignInPswError("账号或密码错误");
            }

            @Override
            public void onFailure(Call<SignIn_Res> call, Throwable t) {
                signView.showMessage("网络连接错误");
            }
        });

    }

    public void signUp(String phone, String psw, String psw_re, String con_code){
        if (!isPhoneValid(phone)){
            signView.setSignUpPhoneError("用户名格式错误");
            return;
        }
        if (!isPasswordValid(psw)){
            signView.setSignUpPswError("密码格式错误");
            return;
        }
        if (!psw_re.equals(psw)){
            signView.setSignUpPswReError("两次密码不匹配");
            return;
        }
        if (!con_code.equals("1234")){
            signView.setSignUpConError("验证码不正确");
            return;
        }

        signView.showProgress(true);
        Call<SignIn_Res> signInResCall = signModel.sign_up(phone,psw,"2015141463221","大帅比","男");
        signInResCall.enqueue(new Callback<SignIn_Res>() {
            @Override
            public void onResponse(Call<SignIn_Res> call, Response<SignIn_Res> response) {
                signView.showProgress(false);
                if (response.body().isIsOk()) {
                    signView.showMessage("注册成功，已自动登录~");
                    signView.toMainActivity();
                }
                else
                    signView.setSignUpPhoneError("账号已存在");
            }

            @Override
            public void onFailure(Call<SignIn_Res> call, Throwable t) {
                signView.showMessage("网络连接错误");
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
