package com.exercise.p.citicup.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.exercise.p.citicup.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        WelAsyncTask task = new WelAsyncTask();
        task.execute();
    }

    private class WelAsyncTask extends AsyncTask<Void, Void, Boolean> {
        SharedPreferences sharedPreferences;

        @Override
        protected Boolean doInBackground(Void... params) {
            sharedPreferences = getSharedPreferences("AppInfo", MODE_PRIVATE);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return sharedPreferences.getBoolean("isLogin", false);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                Intent intent = new Intent();
                intent.setClass(WelcomeActivity.this, MainActivity.class);
                WelcomeActivity.this.startActivity(intent);
            }
            else {
                Intent intent = new Intent();
                intent.setClass(WelcomeActivity.this, SignActivity.class);
                WelcomeActivity.this.startActivity(intent);
            }
            WelcomeActivity.this.finish();
        }
    }

}
