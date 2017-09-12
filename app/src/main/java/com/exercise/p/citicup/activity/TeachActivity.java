package com.exercise.p.citicup.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.exercise.p.citicup.R;

public class TeachActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach);
        initToolbar();
        initView();

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.read_toolbar);
        //设置toolbar属性
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeachActivity.this.finish();
            }
        });
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initView() {
        TextView teach_2_1_d = (TextView) findViewById(R.id.teach_2_1_d);
        SpannableStringBuilder builder = new SpannableStringBuilder("d. 包括数字（时间）期限的条款。认真看清楚期限是多久，及时地处理好该做的事情，逾期只会带来麻烦。");
        ForegroundColorSpan blueSpan1 = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.themeColor));
        builder.setSpan(blueSpan1, 5, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan blueSpan2 = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.themeColor));
        builder.setSpan(blueSpan2, 28, 30, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan blueSpan3 = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.themeColor));
        builder.setSpan(blueSpan3, 40, builder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        teach_2_1_d.setText(builder);
    }
}
