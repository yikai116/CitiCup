package com.exercise.p.citicup.view;

/**
 * Created by p on 2017/8/1.
 */

public interface FpswView {

    void setFpswConError(String msg);
    void setFpswPhoneError(String msg);
    void setFpswPswError(String msg);
    void setFpswPswReError(String msg);

    void showMessage(String msg);
    void cancelGetVerCode();
    void finish();
    void showProgress(boolean show);
}
