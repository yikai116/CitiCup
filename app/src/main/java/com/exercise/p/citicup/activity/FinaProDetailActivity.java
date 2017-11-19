package com.exercise.p.citicup.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.exercise.p.citicup.R;
import com.exercise.p.citicup.dto.FinaPro;

public class FinaProDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fina_pro_detail);
        initToolbar();
        final FinaPro pro = (FinaPro) getIntent().getSerializableExtra("product");
        if (pro == null){
            Toast.makeText(this, "网络连接错误", Toast.LENGTH_SHORT).show();
        }
        else {
            TextView pro_name = (TextView) findViewById(R.id.pro_name);
            pro_name.setText(pro.getName());

            TextView pro_company = (TextView) findViewById(R.id.pro_company);
            pro_company.setText("公司：" + pro.getCompany());

            TextView pro_risk_level = (TextView) findViewById(R.id.pro_risk_level);
            pro_risk_level.setText(getLevelStr(pro.getLevel()));

            TextView pro_pre_earn = (TextView) findViewById(R.id.pro_pre_earn);
            pro_pre_earn.setText(pro.getPreEarn() + "%");

            TextView pro_guaranteed = (TextView) findViewById(R.id.pro_guaranteed);
            pro_guaranteed.setText(pro.getGuaranteed()?"保本":"非保本");

            TextView pro_duration = (TextView) findViewById(R.id.pro_duration);
            pro_duration.setText(pro.getDuration());

            TextView pro_redeem = (TextView) findViewById(R.id.pro_redeem);
            pro_redeem.setText(pro.getRedeem()?"可赎回":"不可赎回");

            TextView pro_min_amount = (TextView) findViewById(R.id.pro_min_amount);
            pro_min_amount.setText(pro.getMinAmount());

            TextView pro_issue_date = (TextView) findViewById(R.id.pro_issue_date);
            pro_issue_date.setText(pro.getIssuintDate());

        }
        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Test","click");
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(pro.getUrl());
                intent.setData(content_url);
                startActivity(intent);
            }
        });
    }

    private String getLevelStr(int i){
        if (i==0){
            return "无风险";
        }
        if (i == 1){
            return "低风险";
        }
        if (i == 2){
            return "较低风险";
        }
        if (i == 3){
            return "中等风险";
        }
        if (i == 4){
            return "较高风险";
        }
        return "高风险";
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.fina_detail_toolbar);
        //设置toolbar属性
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinaProDetailActivity.this.finish();
            }
        });
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
