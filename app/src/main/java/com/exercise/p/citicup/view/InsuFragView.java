package com.exercise.p.citicup.view;

import com.exercise.p.citicup.dto.InsuPro;

import java.util.ArrayList;

/**
 * Created by p on 2017/9/14.
 */

public interface InsuFragView {
    void initView(final ArrayList<InsuPro> pros, boolean more);

    void showMessage(String msg);
}
