package com.exercise.p.citicup;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;

/**
 * Created by p on 2017/2/10.
 */

public class CheckableLinearLayout extends LinearLayout implements Checkable {
    private boolean mChecked;
    private CheckBox box;

    public CheckableLinearLayout(Context context) {
        super(context);
    }

    public CheckableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CheckBox getBox() {
        return box;
    }

    public void setBox(CheckBox box) {
        this.box = box;
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void setChecked(boolean checked) {
        mChecked = checked;
        box.setChecked(checked);
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }
}
