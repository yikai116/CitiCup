package com.exercise.p.citicup.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.signature.StringSignature;
import com.exercise.p.citicup.Helper;
import com.exercise.p.citicup.MyFragAdapter;
import com.exercise.p.citicup.PhotoUtils;
import com.exercise.p.citicup.R;
import com.exercise.p.citicup.dto.response.MyResponse;
import com.exercise.p.citicup.fragment.main.InsuFragment;
import com.exercise.p.citicup.fragment.main.ManaFragment;
import com.exercise.p.citicup.fragment.main.StocFragment;
import com.exercise.p.citicup.model.RetrofitInstance;
import com.exercise.p.citicup.model.SetModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // 拍照请求码
    protected static final int CAMERA_CODE = 100;
    // 相册请求码
    protected static final int ALBUM_CODE = 101;
    // 剪裁请求码
    protected static final int ZOOM_CODE = 102;
    TabLayout tab;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ViewPager pager;
    NavigationView naviView;
    ImageView avatar;
    TextView userName;
    //临时文件路径
    private String tempPath = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/tempPhoto.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        initToolBar();
        initTab();
        initNavi();
    }

    private void findView() {
        tab = (TabLayout) findViewById(R.id.main_tab);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawerLayout);
        pager = (ViewPager) findViewById(R.id.main_pager);
        naviView = (NavigationView) findViewById(R.id.main_side);
        avatar = (ImageView)
                naviView.getHeaderView(0).findViewById(R.id.side_avatar);
        userName = (TextView) naviView.getHeaderView(0).findViewById(R.id.side_name);
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
    private void initToolBar() {
        //设置toolbar属性
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START, true);
            }
        });
        //设置toolbar结合drawerLayout
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
    }

    /***
     * 设置Tab以及ViewPager
     */
    private void initTab() {
        //初始化TabLayout
        tab.addTab(tab.newTab().setText("保险"));
        tab.addTab(tab.newTab().setText("理财"));
        tab.addTab(tab.newTab().setText("股票"));

        //初始化ViewPager
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new InsuFragment());
        fragments.add(new ManaFragment());
        fragments.add(new StocFragment());
        pager.setAdapter(new MyFragAdapter(getSupportFragmentManager(), fragments));

        //结合
//        tab.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        tab.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));
    }

    /**
     * 设置侧边栏点击事件
     */
    private void initNavi() {
        naviView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = new Intent();
                switch (item.getItemId()) {
                    case R.id.side_insul:
                        intent.setClass(MainActivity.this, InsulActivity.class);
                        break;
                    case R.id.side_manal:
                        intent.setClass(MainActivity.this, ManalActivity.class);
                        break;
                    case R.id.side_riskt:
                        intent.setClass(MainActivity.this, RisktActivity.class);
                        break;
                    case R.id.side_insut:
                        intent.setClass(MainActivity.this, InsutActivity.class);
                        break;
                    case R.id.side_other:
                        Toast.makeText(MainActivity.this, "5", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                startActivity(intent);
                return false;
            }
        });
        userName.setText(Helper.userInfo.getName());
        setAvatar(Helper.userInfo.getAvatar(),null);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPopupWindow();
            }
        });
    }

    /**
     * 显示头像选取弹窗
     */
    private void setPopupWindow() {
        final Dialog mCameraDialog = new Dialog(MainActivity.this, R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(MainActivity.this).inflate(
                R.layout.layout_avatar_popup, null);
        //初始化视图
        root.findViewById(R.id.btn_open_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoUtils.getPhotoFromCamera(MainActivity.this, CAMERA_CODE, tempPath);
                mCameraDialog.dismiss();
            }
        });
        root.findViewById(R.id.btn_choose_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoUtils.getPhotoFromAlbum(MainActivity.this, ALBUM_CODE);
                mCameraDialog.dismiss();
            }
        });
        root.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCameraDialog.dismiss();
            }
        });
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        assert dialogWindow != null;
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }

    //处理请求码
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CODE && resultCode == RESULT_OK) {
            //拍照回来进入剪裁activity
            Uri uri = Uri.fromFile(new File(tempPath));
            PhotoUtils.photoZoom(this, uri, tempPath, ZOOM_CODE, 1, 1);
        }
        if (requestCode == ALBUM_CODE && resultCode == RESULT_OK) {
            //相册回来进入裁剪activity
            //获取选择图片的uri
            Uri uri = data.getData();
//            uri = Uri.fromFile(new File((PhotoUtils.getRealFilePath(this,uri))));
            PhotoUtils.photoZoom(this, uri, tempPath, ZOOM_CODE, 1, 1);
        }
        if (requestCode == ZOOM_CODE && resultCode == RESULT_OK) {
            File file = new File(tempPath);

            //在这里可以把临时图片上传到服务器保存，方便下次登录从服务器获取头像
            final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("正在上传...");
            dialog.setCancelable(false);
            dialog.show();

            // 创建 RequestBody，用于封装构建RequestBody
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

            // MultipartBody.Part avatar是参数名字
            MultipartBody.Part part =
                    MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
            SetModel temp = RetrofitInstance.getRetrofitWithToken().create(SetModel.class);
            Call<MyResponse<String>> call = temp.uploadAvatar(part);
            call.enqueue(new Callback<MyResponse<String>>() {
                @Override
                public void onResponse(Call<MyResponse<String>> call, Response<MyResponse<String>> response) {
                    if (response.body().getStatus().getCode() == Helper.SUCCESS) {
                        dialog.dismiss();
                        dialog.setTitle("正在加载...");
                        dialog.show();
                        setAvatar(response.body().getData(), dialog);
                    } else {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, response.body().getStatus().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MyResponse<String>> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    protected void setAvatar(final String url, @Nullable final ProgressDialog dialog) {
        Glide.with(MainActivity.this)
                .load(url)
                .asBitmap()
                .placeholder(R.drawable.rotate_loading)
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .centerCrop()
                .into(new BitmapImageViewTarget(avatar) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(MainActivity.this.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        avatar.setImageDrawable(circularBitmapDrawable);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        e.printStackTrace();
                        super.onLoadFailed(e, errorDrawable);
                        avatar.setImageResource(R.drawable.icon_avatar_fail);
                    }
                });
    }
}
