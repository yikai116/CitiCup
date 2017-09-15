package com.exercise.p.citicup.view;

import com.exercise.p.citicup.dto.FinaPro;

import java.util.ArrayList;

/**
 * Created by p on 2017/9/14.
 */

public interface FinaFragView {
    void initView(final ArrayList<FinaPro> pros, boolean more);

    void showMessage(String msg);
}
