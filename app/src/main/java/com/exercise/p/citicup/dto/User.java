package com.exercise.p.citicup.dto;

import com.google.gson.Gson;

/**
 * Created by p on 2017/8/18.
 */
public class User {
    private String phone;
    private String name;
    private String avatar;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        super.toString();
        return new Gson().toJson(this);
    }
}
