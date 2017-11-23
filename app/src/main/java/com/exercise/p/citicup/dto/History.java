package com.exercise.p.citicup.dto;

import java.util.Date;

/**
 * Created by p on 2017/11/21.
 */

public class History {
    Date date;
    String title;
    String url;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
