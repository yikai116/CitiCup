package com.exercise.p.citicup.dto;


import java.sql.Timestamp;

/**
 * Created by p on 2017/7/31.
 */
public class Code {
    private String phone;
    private String code;
    private Timestamp date;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
