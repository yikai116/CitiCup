package com.exercise.p.citicup.dto;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by p on 2017/9/13.
 */
public class FinaPreferInfo{

    private ArrayList<String> duration;
    private ArrayList<String> proType;
    private ArrayList<String> level;
    private ArrayList<String> revenue;

    public ArrayList<String> getDuration() {
        return duration;
    }

    public void setDuration(ArrayList<String> duration) {
        this.duration = duration;
    }

    public ArrayList<String> getProType() {
        return proType;
    }

    public void setProType(ArrayList<String> proType) {
        this.proType = proType;
    }

    public ArrayList<String> getLevel() {
        return level;
    }

    public void setLevel(ArrayList<String> level) {
        this.level = level;
    }

    public ArrayList<String> getRevenue() {
        return revenue;
    }

    public void setRevenue(ArrayList<String> revenue) {
        this.revenue = revenue;
    }

}
