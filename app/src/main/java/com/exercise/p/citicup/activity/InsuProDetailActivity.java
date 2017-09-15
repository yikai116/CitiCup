package com.exercise.p.citicup.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.exercise.p.citicup.R;
import com.exercise.p.citicup.dto.InsuPro;

public class InsuProDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insu_pro_detail);
        InsuPro pro = (InsuPro) getIntent().getSerializableExtra("product");
        if (pro == null){
            Toast.makeText(this, "网络连接错误", Toast.LENGTH_SHORT).show();
        }
        else {
            ImageView pro_company_icon = (ImageView) findViewById(R.id.pro_company_icon);

            TextView pro_name = (TextView) findViewById(R.id.pro_name);
            pro_name.setText(pro.getName());

            TextView pro_company = (TextView) findViewById(R.id.pro_company);
            pro_company.setText("公司：" + pro.getCompany());

            TextView pro_price = (TextView) findViewById(R.id.pro_price);
            pro_price.setText(pro.getPrice());

            TextView pro_type = (TextView) findViewById(R.id.pro_type);
            pro_type.setText(pro.getType());

            TextView pro_suitable = (TextView) findViewById(R.id.pro_suitable);
            pro_suitable.setText(pro.getSuitable());

            TextView pro_term = (TextView) findViewById(R.id.pro_term);
            pro_term.setText(pro.getTerm());

            TextView pro_advance = (TextView) findViewById(R.id.pro_advance);
            pro_advance.setText(pro.getAdvance());

            Log.i("Test",pro.toString());
            Log.i("Test",pro.getId() + "");

            Log.i("Test",pro.getName() + "");
            Log.i("Test",pro.getCompany() + "");
            Log.i("Test",pro.getType() + "");
            Log.i("Test",pro.getPrice() + "");
            Log.i("Test",pro.getPayMethod() + "");
            Log.i("Test",pro.getAdvance());
            Log.i("Test",pro.getSuitable() + "");
            Log.i("Test",pro.getType() + "");
        }
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.insu_detail_toolbar);
        //设置toolbar属性
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsuProDetailActivity.this.finish();
            }
        });
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
