package com.exercise.p.citicup.presenter;

import android.util.Log;

import com.exercise.p.citicup.helper.Helper;
import com.exercise.p.citicup.dto.FindPswParam;
import com.exercise.p.citicup.dto.response.MyResponse;
import com.exercise.p.citicup.model.FpswModel;
import com.exercise.p.citicup.model.RetrofitInstance;
import com.exercise.p.citicup.view.FpswView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by p on 2017/8/1.
 */

public class FpswPresenter {

    private FpswView fpswView;
    private FpswModel fpswModel;

    public FpswPresenter(FpswView fpswView) {
        this.fpswView = fpswView;
        fpswModel = RetrofitInstance.getRetrofit().create(FpswModel.class);
    }

    /**
     * 找回密码
     * @param phone 用户手机号
     * @param psw 用户新密码
     * @param psw_re 新密码重复
     * @param verCode 验证码
     */
    public void findPsw(String phone, String psw, String psw_re, String verCode) {
        if (!isPhoneValid(phone)) {
            fpswView.setFpswPhoneError("用户名格式错误");
            return;
        }
        if (!isPasswordValid(psw)) {
            fpswView.setFpswPswError("密码格式错误");
            return;
        }
        if (!psw_re.equals(psw)) {
            fpswView.setFpswPswReError("两次密码不匹配");
            return;
        }
        FindPswParam param = new FindPswParam();
        param.setPhone(phone);
        try {
            param.setPsw(Helper.md5Encode(psw));
        } catch (Exception e) {
            e.printStackTrace();
        }
        param.setVerCode(verCode);
        fpswView.showProgress(true);
        Call<MyResponse> call = fpswModel.findPsw(param);
        call.enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                Log.i(Helper.TAG,"重设密码成功——" + response.body().getStatus().getCode());
                MyResponse response1 = response.body();
                fpswView.showProgress(false);
                if (response1.getStatus().getCode() == Helper.SUCCESS) {
                    fpswView.finish();
                }
                else if (response1.getStatus().getCode() == Helper.NO_USER){
                    fpswView.setFpswPhoneError(response1.getStatus().getMsg());
                }
                else if (response1.getStatus().getCode() == Helper.VERCODE_ERROR){
                    fpswView.setFpswConError(response1.getStatus().getMsg());
                }
                else {
                    fpswView.showMessage(response1.getStatus().getMsg());
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                t.printStackTrace();
                fpswView.showProgress(false);
                fpswView.showMessage("网络连接错误");
            }
        });
    }

    /**
     * 得到找回密码验证码
     * @param phone 用户手机号
     */
    public void getFpswVerCode(String phone){
        if (!isPhoneValid(phone)) {
            fpswView.setFpswPhoneError("用户名格式错误");
            fpswView.cancelGetVerCode();
            return;
        }
        Call<MyResponse<String>> call = fpswModel.getFindPswVerCode(phone);
        call.enqueue(new Callback<MyResponse<String>>() {
            @Override
            public void onResponse(Call<MyResponse<String>> call, Response<MyResponse<String>> response) {
                Log.i(Helper.TAG,"得到找回密码验证码——" + response.body().getStatus().getCode());
                if (response.body().getStatus().getCode() == Helper.SUCCESS) {
                    fpswView.showMessage(response.body().getData());
                } else if (response.body().getStatus().getCode() == Helper.NO_USER){
                    fpswView.setFpswPhoneError(response.body().getStatus().getMsg());
                }
                else {
                    fpswView.showMessage(response.body().getStatus().getMsg());
                }
            }

            @Override
            public void onFailure(Call<MyResponse<String>> call, Throwable t) {
                t.printStackTrace();
                fpswView.showProgress(false);
                fpswView.showMessage("网络连接错误");
                fpswView.cancelGetVerCode();
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
