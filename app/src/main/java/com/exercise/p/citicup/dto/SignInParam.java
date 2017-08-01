package com.exercise.p.citicup.dto;

/**
 * Created by p on 2017/7/31.
 */
public class SignInParam {
    private String phone;
    private String psw;
    private String token;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
