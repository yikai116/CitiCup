package com.exercise.p.citicup.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.exercise.p.citicup.MyHolder;
import com.exercise.p.citicup.R;
import com.exercise.p.citicup.ViewPagerAdapter;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.ArrayList;
import java.util.List;

public class InsulActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager pager;
    View page1;
    View page2;
    TreeNode root = TreeNode.root();
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
        LinearLayout layout = (LinearLayout) page1.findViewById(R.id.insul_1_root);
        initData();
        AndroidTreeView tView = new AndroidTreeView(this, root);

        tView.setDefaultViewHolder(MyHolder.class);
        layout.addView(tView.getView(),
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void initData(){

        TreeNode p1 = new TreeNode(new MyHolder.IconTreeItem("理财型",R.drawable.icon_insul_1_1));
        TreeNode p1_1 = new TreeNode(new MyHolder.IconTreeItem("保本"));
        TreeNode p1_2 = new TreeNode(new MyHolder.IconTreeItem("非保本"));
        p1.addChildren(p1_1,p1_2);

        TreeNode p2 = new TreeNode(new MyHolder.IconTreeItem("保障型",R.drawable.icon_insul_1_2));

        TreeNode p2_1 = new TreeNode(new MyHolder.IconTreeItem("寿险",R.drawable.icon_insul_2));
        TreeNode p2_1_1 = new TreeNode(new MyHolder.IconTreeItem("普通型"));
        TreeNode p2_1_2 = new TreeNode(new MyHolder.IconTreeItem("两全险"));
        p2_1.addChildren(p2_1_1,p2_1_2);

        TreeNode p2_2 = new TreeNode(new MyHolder.IconTreeItem("年金险",R.drawable.icon_insul_2));
        TreeNode p2_2_1 = new TreeNode(new MyHolder.IconTreeItem("养老年金"));
        TreeNode p2_2_2 = new TreeNode(new MyHolder.IconTreeItem("教育年金"));
        TreeNode p2_2_3 = new TreeNode(new MyHolder.IconTreeItem("普通年金"));
        p2_2.addChildren(p2_2_1,p2_2_2,p2_2_3);

        TreeNode p2_3 = new TreeNode(new MyHolder.IconTreeItem("意外险",R.drawable.icon_insul_2));
        TreeNode p2_3_1 = new TreeNode(new MyHolder.IconTreeItem("人身年金"));
        TreeNode p2_3_2 = new TreeNode(new MyHolder.IconTreeItem("交通年金"));
        TreeNode p2_3_3 = new TreeNode(new MyHolder.IconTreeItem("航空年金"));
        p2_3.addChildren(p2_3_1,p2_3_2,p2_3_3);

        TreeNode p2_4 = new TreeNode(new MyHolder.IconTreeItem("个人财险",R.drawable.icon_insul_2));
        TreeNode p2_4_1 = new TreeNode(new MyHolder.IconTreeItem("家财险"));
        TreeNode p2_4_2 = new TreeNode(new MyHolder.IconTreeItem("汽车险"));
        TreeNode p2_4_3 = new TreeNode(new MyHolder.IconTreeItem("房贷险"));
        p2_4.addChildren(p2_4_1,p2_4_2,p2_4_3);

        TreeNode p2_5 = new TreeNode(new MyHolder.IconTreeItem("企业财险",R.drawable.icon_insul_2));
        TreeNode p2_5_1 = new TreeNode(new MyHolder.IconTreeItem("财产保险"));
        TreeNode p2_5_2 = new TreeNode(new MyHolder.IconTreeItem("短期意健险"));
        TreeNode p2_5_3 = new TreeNode(new MyHolder.IconTreeItem("保证保险"));
        TreeNode p2_5_4 = new TreeNode(new MyHolder.IconTreeItem("信用保险"));
        TreeNode p2_5_5 = new TreeNode(new MyHolder.IconTreeItem("农业保险"));
        TreeNode p2_5_6 = new TreeNode(new MyHolder.IconTreeItem("责任保险"));
        p2_5.addChildren(p2_5_1,p2_5_2,p2_5_3,p2_5_4,p2_5_5,p2_5_6);

        TreeNode p2_6 = new TreeNode(new MyHolder.IconTreeItem("旅游险",R.drawable.icon_insul_2));
        TreeNode p2_6_1 = new TreeNode(new MyHolder.IconTreeItem("境内"));
        TreeNode p2_6_2 = new TreeNode(new MyHolder.IconTreeItem("境外"));
        TreeNode p2_6_3 = new TreeNode(new MyHolder.IconTreeItem("港澳台"));
        p2_6.addChildren(p2_6_1,p2_6_2,p2_6_3);

        TreeNode p2_7 = new TreeNode(new MyHolder.IconTreeItem("健康险",R.drawable.icon_insul_2));
        TreeNode p2_7_1 = new TreeNode(new MyHolder.IconTreeItem("护理"));
        TreeNode p2_7_2 = new TreeNode(new MyHolder.IconTreeItem("女性疾病"));
        TreeNode p2_7_3 = new TreeNode(new MyHolder.IconTreeItem("失能收入损失险"));
        TreeNode p2_7_4 = new TreeNode(new MyHolder.IconTreeItem("重大疾病"));
        TreeNode p2_7_5 = new TreeNode(new MyHolder.IconTreeItem("住院医疗"));
        p2_7.addChildren(p2_7_1,p2_7_2,p2_7_3,p2_7_4,p2_7_5);

        p2.addChildren(p2_1,p2_2,p2_3,p2_4,p2_5,p2_6,p2_7);

        root.addChildren(p1,p2);

    }
}

