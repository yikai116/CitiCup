package com.exercise.p.citicup.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exercise.p.citicup.MyFragAdapter;
import com.exercise.p.citicup.R;
import com.exercise.p.citicup.fragment.main.InsuFragment;
import com.exercise.p.citicup.fragment.main.ManaFragment;
import com.exercise.p.citicup.fragment.main.StocFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TabLayout tab;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ViewPager pager;
    NavigationView naviView;
    ImageView avatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        initToolBar();
        initTab();
        initNavi();
    }

    private void findView(){
        tab = (TabLayout) findViewById(R.id.main_tab);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawerLayout);
        pager = (ViewPager) findViewById(R.id.main_pager);
        naviView = (NavigationView) findViewById(R.id.main_side);
        avatar = (ImageView)
                naviView.getHeaderView(0).findViewById(R.id.side_avatar);

        TextView exit = (TextView) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });
    }

    /**
     * 设置toolbar以及侧边栏的结合
     */
    private void initToolBar(){
        //设置toolbar属性
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START,true);
            }
        });
        //设置toolbar结合drawerLayout
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
    }

    /***
     * 设置Tab以及ViewPager
     */
    private void initTab(){
        //初始化TabLayout
        tab.addTab(tab.newTab().setText("保险"));
        tab.addTab(tab.newTab().setText("理财"));
        tab.addTab(tab.newTab().setText("股票"));

        //初始化ViewPager
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new InsuFragment());
        fragments.add(new ManaFragment());
        fragments.add(new StocFragment());
        pager.setAdapter(new MyFragAdapter(getSupportFragmentManager(),fragments));

        //结合
//        tab.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        tab.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));
    }

    /**
     * 设置侧边栏点击事件
     */
    private void initNavi(){
        naviView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.side_insul:
                        Toast.makeText(MainActivity.this, "1", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this,InsulActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.side_manal:
                        Toast.makeText(MainActivity.this, "2", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.side_riskt:
                        Toast.makeText(MainActivity.this, "3", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.side_riskl:
                        Toast.makeText(MainActivity.this, "4", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.side_other:
                        Toast.makeText(MainActivity.this, "5", Toast.LENGTH_SHORT).show();
                        break;
                    default:break;
                }
                return false;
            }
        });
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog mCameraDialog = new Dialog(MainActivity.this,R.style.BottomDialog);
                LinearLayout root = (LinearLayout) LayoutInflater.from(MainActivity.this).inflate(
                        R.layout.layout_avatar_popup, null);
                //初始化视图
                root.findViewById(R.id.btn_choose_img).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "相册选取", Toast.LENGTH_SHORT).show();
                    }
                });
                root.findViewById(R.id.btn_open_camera).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "拍照", Toast.LENGTH_SHORT).show();
                    }
                });
                root.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
                    }
                });
                mCameraDialog.setContentView(root);
                Window dialogWindow = mCameraDialog.getWindow();
                dialogWindow.setGravity(Gravity.BOTTOM);
//        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
                WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
                lp.x = 0; // 新位置X坐标
                lp.y = 0; // 新位置Y坐标
                lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
                root.measure(0, 0);
                lp.height = root.getMeasuredHeight();

                lp.alpha = 9f; // 透明度
                dialogWindow.setAttributes(lp);
                mCameraDialog.show();
            }
        });
    }
}
