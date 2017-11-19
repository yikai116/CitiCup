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
import com.exercise.p.citicup.dto.InsuPro;
import com.exercise.p.citicup.dto.response.MyResponse;
import com.exercise.p.citicup.helper.Helper;
import com.exercise.p.citicup.model.InsuTestModel;
import com.exercise.p.citicup.model.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsuProDetailActivity extends AppCompatActivity {

    InsuTestModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insu_pro_detail);
        final InsuPro pro = (InsuPro) getIntent().getSerializableExtra("product");
        if (pro == null){
            Toast.makeText(this, "网络连接错误", Toast.LENGTH_SHORT).show();
        }
        else {
            model = RetrofitInstance.getRetrofitWithToken().create(InsuTestModel.class);
            Call<MyResponse> call = model.setKeyword(pro.getType());
            call.enqueue(new Callback<MyResponse>(){
                @Override
                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                    Log.i("Test",response.code() + "");
                }

                @Override
                public void onFailure(Call<MyResponse> call, Throwable t) {
                }
            });
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
