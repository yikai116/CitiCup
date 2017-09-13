package com.exercise.p.citicup.view;

/**
 * Created by p on 2017/9/13.
 */

public interface ShowDialogView {
    void showDialog(String title);
    void dismissDialog();
    void showMessage(String msg);
    void myFinish(boolean finish);
}
