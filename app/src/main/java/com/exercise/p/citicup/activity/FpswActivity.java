package com.exercise.p.citicup.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.exercise.p.citicup.R;
import com.exercise.p.citicup.presenter.FpswPresenter;
import com.exercise.p.citicup.view.FpswView;
import com.ufreedom.CountDownTextView;


public class FpswActivity extends AppCompatActivity implements FpswView{

    private EditText fPsw_edit_phone;
    private EditText fPsw_edit_psw;
    private EditText fPsw_edit_psw_re;
    private EditText fPsw_edit_con;
    private Button fPsw_button_commit;
    private CountDownTextView countDownTextView;
    private ProgressBar progressBar;
    private LinearLayout root;

    private FpswPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fpsw);
        presenter = new FpswPresenter(this);
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
        countDownTextView = (CountDownTextView) findViewById(R.id.fPsw_button_getCon);
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
                presenter.getFpswVerCode(fPsw_edit_phone.getText().toString());
            }
        });
    }

    private void initView(){
        fPsw_edit_phone = (EditText) findViewById(R.id.fPsw_edit_phone);
        fPsw_edit_psw = (EditText) findViewById(R.id.fPsw_edit_psw);
        fPsw_edit_psw_re = (EditText) findViewById(R.id.fPsw_edit_psw_re);
        fPsw_edit_con = (EditText) findViewById(R.id.fPsw_edit_con);
        progressBar = (ProgressBar) findViewById(R.id.fPsw_processBar);
        root = (LinearLayout) findViewById(R.id.fPsw_root);

        fPsw_button_commit = (Button) findViewById(R.id.fPsw_button_commit);
        fPsw_button_commit.setEnabled(false);
        GradientDrawable drawable = (GradientDrawable) fPsw_button_commit.getBackground();
        drawable.setColor(ContextCompat.getColor(FpswActivity.this,R.color.button_grey));
        fPsw_button_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏软键盘
                ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(FpswActivity.this.getCurrentFocus()
                                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                presenter.findPsw(fPsw_edit_phone.getText().toString(),
                        fPsw_edit_psw.getText().toString(),
                        fPsw_edit_psw_re.getText().toString(),
                        fPsw_edit_con.getText().toString());
            }
        });

    }

    @Override
    public void setFpswConError(String msg) {
        setError(fPsw_edit_con,msg);
    }

    @Override
    public void setFpswPhoneError(String msg) {
        setError(fPsw_edit_phone,msg);
    }

    @Override
    public void setFpswPswError(String msg) {
        setError(fPsw_edit_psw,msg);
    }

    @Override
    public void setFpswPswReError(String msg) {
        setError(fPsw_edit_psw_re,msg);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cancelGetVerCode() {
        countDownTextView.setClickable(true);
        countDownTextView.setText("点击获取");
        countDownTextView.setBackgroundColor(ContextCompat.getColor(FpswActivity.this,R.color.theme_color));
    }

    @Override
    public void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        root.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
    }

    /**
     * EditText设置错误信息
     * @param view EditText控件
     * @param msg  错误信息
     */
    private void setError(EditText view, String msg) {
        SpannableStringBuilder builder = new SpannableStringBuilder(msg);
        ForegroundColorSpan blueSpan = new ForegroundColorSpan(Color.RED);
        builder.setSpan(blueSpan, 0, msg.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText("");
        view.setHint(builder);
        view.requestFocus();
    }

}
