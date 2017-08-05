package com.exercise.p.citicup;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.exercise.p.citicup.activity.WelcomeActivity;

import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Random;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by p on 2017/7/31.
 */
public class Helper {

    /**
     * 获取成功
     */
    public static final int SUCCESS = 1;
    /**
     * 用户不存在
     */
    public static final int NO_USER = -2;
    /**
     * 密码错误
     */
    public static final int PSW_ERROR = -3;
    /**
     * 验证码错误
     */
    public static final int VERCODE_ERROR = -4;
    /**
     * 用户存在
     */
    public static final int USER_REGISTERED = -5;

    public static String IMEI = null;

    /**
     * 密码MD5加密算法
     * @param inStr 需要加密的字符串
     * @return 解密之后的字符串
     * @throws Exception
     */
    public static String md5Encode(String inStr) throws Exception {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public static String getIMEI(Context context){
        try {
            TelephonyManager TelephonyMgr = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
            return TelephonyMgr.getDeviceId();
        }
        catch (Exception e){
            e.printStackTrace();
            return getMyIMEI();
        }
    }

    private static String getMyIMEI(){
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        String token;
        for (int i = 0; i < 15; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        token = sb.toString();
        return token;
    }

}