package com.exercise.p.citicup.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exercise.p.citicup.presenter.SignPresenter;
import com.exercise.p.citicup.R;
import com.exercise.p.citicup.view.SignView;
import com.ufreedom.CountDownTextView;

import java.util.ArrayList;
import java.util.List;

public class SignActivity extends AppCompatActivity implements SignView {

    //登录控件
    private EditText signIn_edit_phone;
    private EditText signIn_edit_psw;
    private Button signIn_signIn;
    private TextView signIn_forgetPsw;
    private TextView signIn_signUp;

    //注册控件
    private EditText signUp_edit_phone;
    private EditText signUp_edit_psw;
    private EditText signUp_edit_psw_re;
    private EditText signUp_edit_con;
    private CountDownTextView signUp_button_getCon;
    private Button signUp_signUp;
    private TextView signUp_signIn;

    //界面控件
    private RelativeLayout root;
    private ProgressBar progressBar;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private View signInView;
    private View signUpView;
    private List<View> lists = new ArrayList<>();

    private SignPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        initView();
        initSignInView(signInView);
        initSignUpView(signUpView);
    }

    /**
     * 初始化整体界面
     */
    private void initView() {
        pager = (ViewPager) findViewById(R.id.sign_pager);
        root = (RelativeLayout) findViewById(R.id.root_sign);
        progressBar = (ProgressBar) findViewById(R.id.sign_processBar);
        signInView = getLayoutInflater().inflate(R.layout.fragment_sign_in, null);
        signUpView = getLayoutInflater().inflate(R.layout.fragment_sign_up, null);
        presenter = new SignPresenter(this);
        lists.add(signInView);
        lists.add(signUpView);
        pagerAdapter = new ViewPagerAdapter(lists);
        pager.setAdapter(pagerAdapter);
        pager.setPageTransformer(true, new MyPageTransformer());
        controlKeyboardLayout(root);
    }

    /**
     * 初始化登录界面
     * @param signInView 登录部分界面
     */
    private void initSignInView(final View signInView) {
        signIn_edit_phone = (EditText) signInView.findViewById(R.id.signIn_edit_phone);
        signIn_edit_psw = (EditText) signInView.findViewById(R.id.signIn_edit_psw);
        signIn_edit_psw.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD + InputType.TYPE_CLASS_TEXT);
        signIn_signIn = (Button) signInView.findViewById(R.id.signIn_button_signIn);
        signIn_forgetPsw = (TextView) signInView.findViewById(R.id.signIn_text_forgetPsw);
        signIn_signUp = (TextView) signInView.findViewById(R.id.signIn_text_signUp);

        //设置字体颜色
        SpannableStringBuilder builder = new SpannableStringBuilder(signIn_signUp.getText().toString());
        ForegroundColorSpan blueSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.themeColor));
        builder.setSpan(blueSpan, 8, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signIn_signUp.setText(builder);

        signIn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(1, true);
            }
        });

        signIn_forgetPsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SignActivity.this, FpswActivity.class);
                startActivity(intent);
            }
        });
        //登录按钮监听器
        signIn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏软键盘
                ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(SignActivity.this.getCurrentFocus()
                                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                //进行登录
                presenter.signIn(signIn_edit_phone.getText().toString()
                        , signIn_edit_psw.getText().toString());

            }
        });
    }

    /**
     * 初始化注册界面
     * @param signUpView 注册部分界面
     */
    private void initSignUpView(View signUpView) {
        signUp_edit_phone = (EditText) signUpView.findViewById(R.id.signUp_edit_phone);
        signUp_edit_psw = (EditText) signUpView.findViewById(R.id.signUp_edit_psw);
        signUp_edit_psw_re = (EditText) signUpView.findViewById(R.id.signUp_edit_psw_re);
        signUp_edit_con = (EditText) signUpView.findViewById(R.id.signUp_edit_con);
        signUp_button_getCon = (CountDownTextView) signUpView.findViewById(R.id.signUp_button_getCon);
        signUp_signUp = (Button) signUpView.findViewById(R.id.signUp_button_signUp);
        signUp_signIn = (TextView) signUpView.findViewById(R.id.signUp_text_signIn);

        //设置字体颜色
        SpannableStringBuilder builder = new SpannableStringBuilder(signUp_signIn.getText().toString());
        ForegroundColorSpan blueSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.themeColor));
        builder.setSpan(blueSpan, 5, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signUp_signIn.setText(builder);

        signUp_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(0, true);
            }
        });

        //未点击获取验证码之前，设置注册按钮不可点击，并且修改颜色
        signUp_signUp.setEnabled(false);
        GradientDrawable drawable = (GradientDrawable) signUp_signUp.getBackground();
        drawable.setColor(ContextCompat.getColor(SignActivity.this, R.color.buttonGrey));
        //注册按钮监听器
        signUp_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏软键盘
                ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(SignActivity.this.getCurrentFocus()
                                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                presenter.signUp(signUp_edit_phone.getText().toString()
                        , signUp_edit_psw.getText().toString()
                        , signUp_edit_psw_re.getText().toString()
                        , signUp_edit_con.getText().toString());
            }
        });

        //获取验证码按钮倒计时回调函数
        signUp_button_getCon.addCountDownCallback(new CountDownTextView.CountDownCallback() {
            //开始计时，设置不可点击
            @Override
            public void onTick(CountDownTextView countDownTextView, long millisUntilFinished) {
                signUp_button_getCon.setClickable(false);
                signUp_button_getCon.setBackgroundColor(ContextCompat.getColor(SignActivity.this, R.color.buttonGrey));
            }

            //计时结束，设置可点击
            @Override
            public void onFinish(CountDownTextView countDownTextView) {
                signUp_button_getCon.setClickable(true);
                signUp_button_getCon.setText("点击获取");
                signUp_button_getCon.setBackgroundColor(ContextCompat.getColor(SignActivity.this, R.color.themeColor));
            }
        });

        //设置倒计时View
        signUp_button_getCon.setTimeFormat(CountDownTextView.TIME_SHOW_S);
        signUp_button_getCon.setAutoDisplayText(true);
        //设置其监听器，点击时设置注册按钮可点击，并且开始计时60s
        signUp_button_getCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Test", "点击");
                long timeInFuture = SystemClock.elapsedRealtime() + 1000 * 60;
                signUp_button_getCon.setTimeInFuture(timeInFuture);
                signUp_button_getCon.start();
                signUp_signUp.setEnabled(true);
                GradientDrawable drawable = (GradientDrawable) signUp_signUp.getBackground();
                drawable.setColor(ContextCompat.getColor(SignActivity.this, R.color.themeColor));
                presenter.getSignUpVerCode(signUp_edit_phone.getText().toString());
            }
        });
    }

    /**
     * 控制键盘布局
     * @param root 根布局控件
     */
    private void controlKeyboardLayout(final RelativeLayout root) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect rect = new Rect();
                        // 获取root在窗体的可视区域
                        root.getWindowVisibleDisplayFrame(rect);
                        // 当前视图最外层的高度减去现在所看到的视图的最底部的y坐标
                        int rootInvisibleHeight = root.getRootView()
                                .getHeight() - rect.bottom;
                        // 若rootInvisibleHeight高度大于100，则说明当前视图上移了，说明软键盘弹出了
                        if (rootInvisibleHeight > 100) {
                            View v = getCurrentFocus();
                            if (v != null) {
                                int[] location = new int[2];
                                // 获取scrollToView在窗体的坐标
                                v.getLocationInWindow(location);
                                int y = location[1] + 500 - rect.bottom;
                                root.scrollBy(0, y);
                            }
                        } else {
                            // 软键盘没有弹出来的时候
                            root.scrollTo(0, 0);
                        }
                    }
                });
    }

    @Override
    public void setSignInPhoneError(String msg) {
        setError(signIn_edit_phone, msg);
    }

    @Override
    public void setSignInPswError(String msg) {
        setError(signIn_edit_psw, msg);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toMainActivity() {
        Intent intent = new Intent();
        intent.setClass(SignActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        pager.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public void setSignUpPhoneError(String msg) {
        setError(signUp_edit_phone, msg);
    }

    @Override
    public void setSignUpPswError(String msg) {
        setError(signUp_edit_psw, msg);
    }

    @Override
    public void setSignUpPswReError(String msg) {
        setError(signUp_edit_psw_re, msg);
    }

    @Override
    public void cancelGetVerCode() {
        signUp_button_getCon.cancel();
        signUp_button_getCon.setClickable(true);
        signUp_button_getCon.setText("点击获取");
        signUp_button_getCon.setBackgroundColor(ContextCompat.getColor(SignActivity.this, R.color.themeColor));

        signUp_signUp.setEnabled(false);
        GradientDrawable drawable = (GradientDrawable) signUp_signUp.getBackground();
        drawable.setColor(ContextCompat.getColor(SignActivity.this,R.color.buttonGrey));
    }

    @Override
    public void setSignUpConError(String msg) {
        setError(signUp_edit_con, msg);
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

    /**
     * ViewPager适配器
     */
    private class ViewPagerAdapter extends PagerAdapter {

        List<View> viewLists;

        public ViewPagerAdapter(List<View> lists) {
            viewLists = lists;
        }

        //获得size
        @Override
        public int getCount() {
            return viewLists.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        //销毁Item
        @Override
        public void destroyItem(View view, int position, Object object) {
            ((ViewPager) view).removeView(viewLists.get(position));
        }

        //实例化Item
        @Override
        public Object instantiateItem(View view, int position) {
            ((ViewPager) view).addView(viewLists.get(position), 0);
            return viewLists.get(position);
        }

    }

    /**
     * ViewPager过渡动画
     */
    private class MyPageTransformer implements ViewPager.PageTransformer {

        private static final float ROT_MAX = 20.0f;
        private float mRot;

        /**
         * @param view     滑动中的那个view
         * @param position 滑动的比例
         */
        public void transformPage(View view, float position) {
            //界面不可见
            if (position < -1) {
                ViewCompat.setRotation(view, 0);
            }
            //界面可见
            else if (position <= 1) {
                mRot = (ROT_MAX * position);
                ViewCompat.setPivotX(view, view.getMeasuredWidth() * 0.5f);
                ViewCompat.setPivotY(view, view.getMeasuredHeight());
                ViewCompat.setRotation(view, mRot);
            }
            //界面不可见
            else {
                ViewCompat.setRotation(view, 0);
            }
        }
    }
}
