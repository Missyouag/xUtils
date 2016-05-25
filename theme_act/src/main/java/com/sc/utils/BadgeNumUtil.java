package com.sc.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;


import org.xutils.common.util.LogUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 角标管理
 * 应用角图标
 * Created by Missyouag on 2015/11/13.
 */
public class BadgeNumUtil {


    private static BadgeNumUtil mBadgeNuntil;
    private Activity a;
    private final String launcherActivity ;

    private BadgeNumUtil(Activity a, String launcherActivity) {
        this.a = a;
        this.launcherActivity = launcherActivity;
    }

    /**
     *
     * @param a
     * @param launcherActivity = MainA.class.getName()
     * @return
     */
    public static BadgeNumUtil getBadgeUtil(Activity a,String launcherActivity) {
        if (mBadgeNuntil == null) {
            mBadgeNuntil = new BadgeNumUtil(a, launcherActivity);
        }
        return mBadgeNuntil;
    }

    public void sendBadgeNumber(int numInt) {
        numInt=Math.max(0, Math.min(numInt, 99));
        String number=Integer.toString(numInt);
        if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
            sendToXiaoMi(numInt);
        } else if (Build.MANUFACTURER.equalsIgnoreCase("samsung")) {
            sendToSony(number);
        } else if (Build.MANUFACTURER.toLowerCase().contains("sony")) {
            sendToSamsumg(number);
        } else {
//            Toast.makeText(a, "Not Support", Toast.LENGTH_LONG).show();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void sendToXiaoMi(int number) {
        NotificationManager nm = (NotificationManager) a.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = null;
        boolean isMiUIV6 = true;
        try {
            Notification.Builder builder = new Notification.Builder(a);
            builder.setContentTitle("您有" + number + "未读消息");
            builder.setTicker("您有" + number + "未读消息");
            builder.setAutoCancel(false);
            builder.setDefaults(Notification.DEFAULT_LIGHTS);
            notification = builder.build();
            Field field = notification.getClass().getDeclaredField("extraNotification");
            Object extraNotification = field.get(notification);
            Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);
            method.invoke(extraNotification, number);
        } catch (Exception e) {
            e.printStackTrace();
            //miui 6之前的版本
            isMiUIV6 = false;
            Intent localIntent = new Intent("android.intent.action.APPLICATION_MESSAGE_UPDATE");
            localIntent.putExtra("android.intent.extra.update_application_component_name", a.getPackageName() + "/" + launcherActivity);
            localIntent.putExtra("android.intent.extra.update_application_message_text", number);
            a.sendBroadcast(localIntent);
        } finally {
            LogUtil.d("???" + notification+isMiUIV6);
            if (notification != null && isMiUIV6) {
                //miui6以上版本需要使用通知发送
                nm.notify(101010, notification);
            }
        }

    }

    private void sendToSony(String number) {
        boolean isShow = true;
        if ("0".equals(number)) {
            isShow = false;
        }
        Intent localIntent = new Intent();
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", isShow);//是否显示
        localIntent.setAction("com.sonyericsson.home.action.UPDATE_BADGE");
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME", launcherActivity);//启动页
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE", number);//数字
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME", a.getPackageName());//包名
        a.sendBroadcast(localIntent);

    }

    private void sendToSamsumg(String number) {
        Intent localIntent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        localIntent.putExtra("badge_count", number);//数字
        localIntent.putExtra("badge_count_package_name", a.getPackageName());//包名
        localIntent.putExtra("badge_count_class_name", launcherActivity); //启动页
        a.sendBroadcast(localIntent);
    }
}
