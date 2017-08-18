package com.exercise.p.citicup.dto;

import com.google.gson.Gson;

/**
 * Created by p on 2017/8/18.
 */
public class UserInfo {
    private String phone;
    private String name;
    private String avatar;
    private int age;
    private int income;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    @Override
    public String toString() {
        super.toString();
        return new Gson().toJson(this);
    }
}
