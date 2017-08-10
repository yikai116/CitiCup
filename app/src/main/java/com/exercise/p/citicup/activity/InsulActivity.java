package com.exercise.p.citicup.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.exercise.p.citicup.R;
import com.exercise.p.citicup.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class InsulActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager pager;
    View page1;
    View page2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insul);
        findView();
        initToolBar();
        initTab();
        initPager();
        initPage1();
    }

    private void findView(){
        toolbar = (Toolbar) findViewById(R.id.insul_toolbar);
        tabLayout = (TabLayout) findViewById(R.id.insul_tab);
        pager = (ViewPager) findViewById(R.id.insul_pager);
    }

    private void initToolBar(){
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsulActivity.this.finish();
            }
        });
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initTab(){
        tabLayout.addTab(tabLayout.newTab().setText("险种自选"));
        tabLayout.addTab(tabLayout.newTab().setText("其他条件"));
    }

    private void initPager(){
        List<View> views = new ArrayList<>();
        page1 = getLayoutInflater().inflate(R.layout.fragment_insul_1,null);
        page2 = getLayoutInflater().inflate(R.layout.fragment_insul_2,null);
        views.add(page1);
        views.add(page2);
        ViewPagerAdapter adapter = new ViewPagerAdapter(views);
        pager.setAdapter(adapter);
        //结合TabLayout和Pager
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));
    }

    private void initPage1(){
        final TextView textView = (TextView) page1.findViewById(R.id.textview);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InsulActivity.this, "1111", Toast.LENGTH_SHORT).show();
                textView.setCompoundDrawables(null,null,getDrawable(R.drawable.icon_insu_read),null);
            }
        });
    }
}
