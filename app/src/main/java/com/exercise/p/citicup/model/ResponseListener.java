package com.exercise.p.citicup.model;

/**
 * Created by p on 2017/6/4.
 */

public interface ResponseListener extends NetFailListener {
    void success();
    void fail(String msg);
}