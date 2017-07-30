package com.exercise.p.citicup.json;

/**
 * Created by p on 2017/5/11.
 */

public class SignIn_Res {
    /**
     * isOk : true
     * content : psw is incorrect
     */

    private boolean isOk;
    private String content;

    public boolean isIsOk() {
        return isOk;
    }

    public void setIsOk(boolean isOk) {
        this.isOk = isOk;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String msg) {
        this.content = msg;
    }
}
