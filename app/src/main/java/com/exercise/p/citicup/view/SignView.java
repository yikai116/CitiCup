package com.exercise.p.citicup.view;

/**
 * Created by p on 2017/7/17.
 */

public interface SignView {

    void setSignInPhoneError(String msg);
    void setSignInPswError(String msg);

    void setSignUpConError(String msg);
    void setSignUpPhoneError(String msg);
    void setSignUpPswError(String msg);
    void setSignUpPswReError(String msg);

    void showMessage(String msg);
    void toMainActivity();
    void showProgress(boolean show);
}
