package com.exercise.p.citicup.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Browser;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.signature.StringSignature;
import com.exercise.p.citicup.Manifest;
import com.exercise.p.citicup.helper.Helper;
import com.exercise.p.citicup.helper.MyFragAdapter;
import com.exercise.p.citicup.helper.PhotoUtils;
import com.exercise.p.citicup.R;
import com.exercise.p.citicup.dto.response.MyResponse;
import com.exercise.p.citicup.fragment.main.InsuFragment;
import com.exercise.p.citicup.fragment.main.FinaFragment;
import com.exercise.p.citicup.fragment.main.StocFragment;
import com.exercise.p.citicup.model.RetrofitInstance;
import com.exercise.p.citicup.model.SetModel;
import com.exercise.p.citicup.presenter.MainPresenter;
import com.exercise.p.citicup.view.MainView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MainView {

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
    RelativeLayout notTestView;
    NavigationView naviView;
    ImageView avatar;
    TextView userName;
    ProgressDialog dialog;
    MainPresenter presenter;
    LocationManager mLocationManager;
    //临时文件路径
    private String tempPath = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/tempPhoto.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);
        findView();
        initToolBar();
        initTab();
        initNavi();
        presenter.verTest();
        presenter.setRegId(this);
        getLastKnownLocation();
    }
    private void findView() {
        tab = (TabLayout) findViewById(R.id.main_tab);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawerLayout);
        pager = (ViewPager) findViewById(R.id.main_pager);
        notTestView = (RelativeLayout) findViewById(R.id.notTest);
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
        TextView exchange = (TextView) findViewById(R.id.exchange);
        exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SignActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
        notTestView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.verTest();
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
//        tab.addTab(tab.newTab().setText("股票"));

        //初始化ViewPager
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new InsuFragment());
        fragments.add(new FinaFragment());
//        fragments.add(new StocFragment());
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
                    case R.id.side_insuTest:
                        intent.setClass(MainActivity.this, InsuTestActivity.class);
                        break;
                    case R.id.side_insuPrefer:
                        intent.setClass(MainActivity.this, InsuPreferActivity.class);
                        break;
                    case R.id.side_riskTest:
                        intent.setClass(MainActivity.this, RiskTestActivity.class);
                        break;
                    case R.id.side_personal:
                        intent.setClass(MainActivity.this, PersonalActivity.class);
                        break;
                    default:
                        break;
                }
                startActivity(intent);
                return false;
            }
        });
        userName.setText(Helper.user.getName());
        setAvatar(Helper.user.getAvatar(), null);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPopupWindow();
            }
        });
    }


    private void getLastKnownLocation() {
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        for (String s : providers) {
            Log.i("Location","provider:---  " + s);
        }
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("Location", "onLocationChanged");
                if (location != null) {
                    Log.i("Location", "Current longitude = "
                            + location.getLongitude());
                    Log.i("Location", "Current latitude = "
                            + location.getLatitude());
                    presenter.savePlace(location.getLatitude(),location.getLongitude());
                }
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.i("Location", "onProviderDisabled");
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.i("Location", "onProviderEnabled");
            }

            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
                Log.i("Location", "onStatusChanged");
            }
        };
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.i("Location","continue: permission  " + provider);
                continue;
            }
            Location l = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (l != null){
                mLocationManager.requestLocationUpdates(provider, 1000, (float) 1000.0, listener);
                break;
            }
        }
        return;
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

    @Override
    public void showDialog(String title) {
        if (dialog == null) {
            dialog = new ProgressDialog(MainActivity.this);
        }
        dialog.setTitle(title);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void dismissDialog() {
        if (dialog != null)
            dialog.dismiss();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void myFinish(boolean finish) {
        MainActivity.this.finish();
    }

    @Override
    public void showNotTest(boolean show) {
        pager.setVisibility(show ? View.GONE : View.VISIBLE);
        notTestView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
