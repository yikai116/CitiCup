package com.exercise.p.citicup.activity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.exercise.p.citicup.PhotoUtils;
import com.exercise.p.citicup.R;

import java.util.ArrayList;

public class FeedbackActivity extends AppCompatActivity {


    // 相册请求码
    protected static final int ALBUM_CODE = 101;
    ArrayList<Uri> pics = new ArrayList<>();
    ArrayList<ImageView> images = new ArrayList<>();
    ImageView add;
    EditText fd_des;
    EditText fd_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initToolBar();
        initView();
    }

    /**
     * 初始化标题栏
     */
    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.feedback_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedbackActivity.this.finish();
            }
        });
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initView() {
        add = (ImageView) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, ALBUM_CODE);
            }
        });

        images.add((ImageView) findViewById(R.id.fd_pic1));
        images.add((ImageView) findViewById(R.id.fd_pic2));
        images.add((ImageView) findViewById(R.id.fd_pic3));
        images.add((ImageView) findViewById(R.id.fd_pic4));

        fd_des = (EditText) findViewById(R.id.fb_question_des);
        fd_phone = (EditText) findViewById(R.id.fd_phone);

        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fd_des.getText().toString().equals("") || fd_des.getText().toString() == null) {
                    Toast.makeText(FeedbackActivity.this, "请填写问题描述~", Toast.LENGTH_SHORT).show();
                    return;
                }

                String content = fd_des.getText().toString();
                if (fd_phone.getText().toString().equals("")||fd_des.getText().toString() == null){
                    String temp = "\n联系电话：" + fd_phone.getText().toString();
                    content +=  temp;
                }
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:way.ping.li@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "CitiCup反馈");
                intent.putExtra(Intent.EXTRA_TEXT, content);
                for (int i = 0 ; i < pics.size() ; i++){
                    intent.putExtra(Intent.EXTRA_STREAM,pics.get(i));
                }
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ALBUM_CODE && resultCode == RESULT_OK) {
            //相册回来进入裁剪activity
            //获取选择图片的uri
            Uri uri = data.getData();
            if (uri != null) {
                Log.i("Test", "Uri：" + uri.toString());
                pics.add(uri);
                images.get(pics.size() - 1).setImageURI(uri);
                images.get(pics.size() - 1).setVisibility(View.VISIBLE);
                if (pics.size() == 4) {
                    add.setVisibility(View.GONE);
                }
            } else {
                Log.i("Test", "Uri：NULL");
            }
        }
    }
}
