package com.exercise.p.citicup.dto;

import java.util.ArrayList;

/**
 * Created by p on 2017/9/13.
 */
public class InsuPreferInfo {

    private ArrayList<String> insuType;
    private ArrayList<String> theme;
    private ArrayList<String> payMethod;

    public ArrayList<String> getInsuType() {
        return insuType;
    }

    public void setInsuType(ArrayList<String> insuType) {
        this.insuType = insuType;
    }

    public ArrayList<String> getTheme() {
        return theme;
    }

    public void setTheme(ArrayList<String> theme) {
        this.theme = theme;
    }

    public ArrayList<String> getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(ArrayList<String> payMethod) {
        this.payMethod = payMethod;
    }


}
