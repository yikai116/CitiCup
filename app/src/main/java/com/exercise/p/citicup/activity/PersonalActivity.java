package com.exercise.p.citicup.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.signature.StringSignature;
import com.exercise.p.citicup.helper.Helper;
import com.exercise.p.citicup.helper.PhotoUtils;
import com.exercise.p.citicup.R;
import com.exercise.p.citicup.dto.response.MyResponse;
import com.exercise.p.citicup.model.RetrofitInstance;
import com.exercise.p.citicup.model.SetModel;
import com.exercise.p.citicup.presenter.UserInfoPresenter;
import com.exercise.p.citicup.view.UserInfoView;

import java.io.File;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalActivity extends AppCompatActivity implements UserInfoView {

    // 拍照请求码
    protected static final int CAMERA_CODE = 100;
    // 相册请求码
    protected static final int ALBUM_CODE = 101;
    // 剪裁请求码
    protected static final int ZOOM_CODE = 102;

    ProgressDialog dialog;
    UserInfoPresenter presenter;
    ImageView avatar;
    //临时文件路径
    private String tempPath = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/tempPhoto.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        initToolBar();
        avatar = (ImageView) findViewById(R.id.personal_avatar);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPopupWindow();
            }
        });
        setAvatar(Helper.user.getAvatar(),null);
        TextView user_name = (TextView) findViewById(R.id.personal_name);
        user_name.setText(Helper.user.getName());
        initView();
        presenter = new UserInfoPresenter(this);
        if (Helper.info == null){
            presenter.getDate();
        }
        else {
            initData();
        }
    }


    /**
     * 初始化标题栏
     */
    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.person_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalActivity.this.finish();
            }
        });
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initView(){

        findViewById(R.id.age_modify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.age_1).setVisibility(View.GONE);
                findViewById(R.id.age_2).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.gender_modify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.gender_1).setVisibility(View.GONE);
                findViewById(R.id.gender_2).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.income_modify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.income_1).setVisibility(View.GONE);
                findViewById(R.id.income_2).setVisibility(View.VISIBLE);
            }
        });


        findViewById(R.id.age_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.personal_age_edit);
                findViewById(R.id.age_1).setVisibility(View.VISIBLE);
                findViewById(R.id.age_2).setVisibility(View.GONE);
                if (editText.getText().toString() == null|| Objects.equals(editText.getText().toString(), "")) {
                    return;
                }
                Helper.info.setAge(Integer.parseInt(editText.getText().toString()));
                initData();
                presenter.setUserInfo();
            }
        });
        findViewById(R.id.gender_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup group = (RadioGroup) findViewById(R.id.personal_gender_edit);
                findViewById(R.id.gender_1).setVisibility(View.VISIBLE);
                findViewById(R.id.gender_2).setVisibility(View.GONE);
                Log.i("Test","id : " + group.getCheckedRadioButtonId());
                if (group.getCheckedRadioButtonId() == -1){
                    return;
                }
                Helper.info.setGender((int)group.getCheckedRadioButtonId() == 1);
                initData();
                presenter.setUserInfo();
            }
        });
        findViewById(R.id.income_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.personal_income_edit);
                findViewById(R.id.income_1).setVisibility(View.VISIBLE);
                findViewById(R.id.income_2).setVisibility(View.GONE);
                if (editText.getText().toString() == null|| Objects.equals(editText.getText().toString(), "")) {
                    return;
                }
                Helper.info.setIncome(Integer.parseInt(editText.getText().toString()));
                initData();
                presenter.setUserInfo();
            }
        });
    }

    protected void setAvatar(final String url, @Nullable final ProgressDialog dialog) {
        Glide.with(PersonalActivity.this)
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
                                RoundedBitmapDrawableFactory.create(PersonalActivity.this.getResources(), resource);
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

    /**
     * 显示头像选取弹窗
     */
    private void setPopupWindow() {
        final Dialog mCameraDialog = new Dialog(PersonalActivity.this, R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(PersonalActivity.this).inflate(
                R.layout.layout_avatar_popup, null);
        //初始化视图
        root.findViewById(R.id.btn_open_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoUtils.getPhotoFromCamera(PersonalActivity.this, CAMERA_CODE, tempPath);
                mCameraDialog.dismiss();
            }
        });
        root.findViewById(R.id.btn_choose_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoUtils.getPhotoFromAlbum(PersonalActivity.this, ALBUM_CODE);
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
            final ProgressDialog dialog = new ProgressDialog(PersonalActivity.this);
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
                        setAvatar(response.body().getData(),dialog);
                    } else {
                        dialog.dismiss();
                        Toast.makeText(PersonalActivity.this, response.body().getStatus().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MyResponse<String>> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(PersonalActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void showDialog(String title) {
        if (dialog == null)
            dialog = new ProgressDialog(PersonalActivity.this);
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
    public void initData() {
        TextView age = (TextView) findViewById(R.id.personal_age);
        TextView gender = (TextView) findViewById(R.id.personal_gender);
        TextView income = (TextView) findViewById(R.id.personal_income);
        age.setText("年龄：" + Helper.info.getAge() + "岁");
        String str = Helper.info.getGender()?"男":"女";
        gender.setText("性别：" + str);
        income.setText("家庭年收入：" + Helper.info.getIncome() + "万");
    }
}
