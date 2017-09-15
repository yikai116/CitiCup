package com.exercise.p.citicup.view;

import com.exercise.p.citicup.dto.UserInfo;

/**
 * Created by p on 2017/9/13.
 */

public interface UserInfoView {
    void showDialog(String title);
    void dismissDialog();
    void showMessage(String msg);
    void initData();
}
