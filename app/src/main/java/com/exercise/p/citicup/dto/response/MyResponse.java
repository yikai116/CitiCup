package com.exercise.p.citicup.dto.response;

/**
 * Created by p on 2017/7/31.
 */
public class MyResponse<Obj> {
    private Status status;
    private Obj data;

    public MyResponse(Status status, Obj data) {
        this.status = status;
        this.data = data;
    }

    public MyResponse(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Obj getData() {
        return data;
    }

    public void setData(Obj data) {
        this.data = data;
    }
}
