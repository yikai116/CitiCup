package com.exercise.p.citicup.dto;

import java.io.Serializable;

/**
 * Created by p on 2017/9/14.
 */
public class FinaPro implements Serializable {
    private int id;
    private String name;
    private String company;
    private String minAmount;
    private String duration;
    private String issuintDate;
    private float preEarn;
    private boolean redeem;
    private boolean guaranteed;
    private int level;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(String minAmount) {
        this.minAmount = minAmount;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getIssuintDate() {
        return issuintDate;
    }

    public void setIssuintDate(String issuintDate) {
        this.issuintDate = issuintDate;
    }

    public float getPreEarn() {
        return preEarn;
    }

    public void setPreEarn(float preEarn) {
        this.preEarn = preEarn;
    }

    public boolean getRedeem() {
        return redeem;
    }

    public void setRedeem(boolean redeem) {
        this.redeem = redeem;
    }

    public boolean getGuaranteed() {
        return guaranteed;
    }

    public void setGuaranteed(boolean guaranteed) {
        this.guaranteed = guaranteed;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
