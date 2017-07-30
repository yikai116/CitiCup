package com.exercise.p.citicup.activity;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.exercise.p.citicup.R;
import com.ufreedom.CountDownTextView;

public class FpswActivity extends AppCompatActivity {

    private EditText fPsw_edit_phone;
    private EditText fPsw_edit_psw;
    private EditText fPsw_edit_psw_re;
    private EditText fPsw_edit_con;
    private Button fPsw_button_commit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fpsw);
        initView();
        initActionbar();
        initCountDown();
    }

    private void initActionbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.fPsw_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        assert toolbar != null;
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FpswActivity.this.finish();
            }
        });
    }

    private void initCountDown(){
        final CountDownTextView countDownTextView = (CountDownTextView) findViewById(R.id.fPsw_button_getCon);
        countDownTextView.addCountDownCallback(new CountDownTextView.CountDownCallback() {
            @Override
            public void onTick(CountDownTextView countDownTextView, long millisUntilFinished) {
                countDownTextView.setClickable(false);
                countDownTextView.setBackgroundColor(ContextCompat.getColor(FpswActivity.this,R.color.button_grey));
            }

            @Override
            public void onFinish(CountDownTextView countDownTextView) {
                countDownTextView.setClickable(true);
                countDownTextView.setText("点击获取");
                countDownTextView.setBackgroundColor(ContextCompat.getColor(FpswActivity.this,R.color.theme_color));
            }
        });
        countDownTextView.setTimeFormat(CountDownTextView.TIME_SHOW_S);
        countDownTextView.setAutoDisplayText(true);
        countDownTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long timeInFuture = SystemClock.elapsedRealtime() + 1000 * 60;
                countDownTextView.setTimeInFuture(timeInFuture);
                countDownTextView.start();
                fPsw_button_commit.setEnabled(true);
                GradientDrawable drawable = (GradientDrawable) fPsw_button_commit.getBackground();
                drawable.setColor(ContextCompat.getColor(FpswActivity.this,R.color.theme_color));
            }
        });
    }

    private void initView(){
        fPsw_edit_phone = (EditText) findViewById(R.id.fPsw_edit_phone);
        fPsw_edit_psw = (EditText) findViewById(R.id.fPsw_edit_psw);
        fPsw_edit_psw_re = (EditText) findViewById(R.id.fPsw_edit_psw_re);
        fPsw_edit_con = (EditText) findViewById(R.id.fPsw_edit_con);

        fPsw_button_commit = (Button) findViewById(R.id.fPsw_button_commit);
        fPsw_button_commit.setEnabled(false);
        GradientDrawable drawable = (GradientDrawable) fPsw_button_commit.getBackground();
        drawable.setColor(ContextCompat.getColor(FpswActivity.this,R.color.button_grey));
        fPsw_button_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive())
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

            }
        });

    }
}
