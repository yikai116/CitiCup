package com.exercise.p.citicup.helper;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.exercise.p.citicup.dto.User;
import com.exercise.p.citicup.dto.UserInfo;

import java.security.MessageDigest;
import java.util.Random;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by p on 2017/7/31.
 */
public class Helper {

    public static final String TAG = "LastTest";
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
    /**
     * 用户信息
     */
    public static User user = null;
    public static UserInfo info = null;
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

    /**
     * 得到IMEI字符串
     * @param context 上下文
     * @return 字符串
     */
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

    /**
     * getIMEI()调用
     */
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

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * @param context 上下文
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     * @param context 上下文
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
