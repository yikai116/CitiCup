package com.exercise.p.citicup;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.provider.Browser;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

/**
 * Created by p on 2017/11/7.
 */

public class MyApplication extends Application {
    public static final String APP_ID = "2882303761517637489";
    public static final String APP_KEY = "5421763797489";
    public static final String TAG = "com.exercise.p.citicup";

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化push推送服务
        if (shouldInit()) {
            MiPushClient.registerPush(this, APP_ID, APP_KEY);
        }
        //打开Log
        LoggerInterface newLogger = new LoggerInterface() {

            @Override
            public void setTag(String tag) {
                // ignore
            }

            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }

            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };
        Logger.setLogger(this, newLogger);
//        history();
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

//    private String history() {
//        String string = null;
//        ContentResolver contentResolver = getContentResolver();
//
////        Cursor cursor = contentResolver.query(
////                Uri.parse("content://browser/bookmarks"),
////                new String[], null, null, null);
////
////        Log.i("HISTORY", cursor == null ? "is null" : "not null");
////        while (cursor != null && cursor.moveToNext()) {
////            string = cursor.getString(cursor.getColumnIndex("url"));
////            Log.i("HISTORY", string == null ? "null" : string);
////        }
//
//        return string;
//    }
}
