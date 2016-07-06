package com.sc.activity.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;


import com.sc.R;
import com.sc.activity.WebA;
import com.sc.utils.SweetAlert.SweetAlertToast;

import java.io.File;
import java.util.Arrays;


/**
 * 解析字符串<p/>
 * 跳转界面帮助
 * Created by sc on 2015/12/1.
 */
public class XSkipUtil {
    /**
     * 传递顶部栏所用的标题参数 "title"
     */
    public static final String Skip_title = "title";
    /**
     * 传递参数
     * 值请传String
     */
    public final static String Skip_key = "id";
    /**
     * 传递参数
     * 值请传String[]
     */
    public final static String Skip_keys = "sub_keys";
    /**
     * mana管理  分类
     * 值请传int
     */
    public final static String Skip_type = "type";


/**
// /**
// * 登录注册界面
// * 1 登录
// * 2 注册
// *
// * @param c
// * @param key
//*//*
    public static void ToLogin(Activity c, int type, String key) {
        if (c instanceof LoginA) {
            if (null != key) ((LoginA) c).key = key;
            ((LoginA) c).startNext(type);
            return;
        }
        toLift(c, LoginA.class, type, key);
    }
//*/









    public static void SkipActivity(Activity c, Class<?> classType, String id) {
        Intent intent = getIntent(c, classType, id);
        toLift(c, intent);
    }

    private static Intent getIntent(Activity c, Class<?> classType, String id) {
        return getIntent(c, classType, -1, id);
    }

    /**
     * @param c
     * @param classType
     * @param keys      key
     * @return
     */
    @NonNull
    public static <T> Intent getIntent(Activity c, Class<T> classType, int type, String key, String... keys) {
        Intent intent = new Intent(c, classType);
        if (type > 0) intent.putExtra(Skip_type, type);
        if (null != keys) intent.putExtra(Skip_keys, keys);
        if (null != key) intent.putExtra(Skip_key, key);
        return intent;
    }


//    /**
//     * 跳转至搜索界面
//     *
//     * @param c
//     * @param key  搜索内容,id
//     * @param flag 3 商品,4 店铺,5 店铺详情
//     */
//    public static void ToSearch(Activity c, String key, int flag) {
//        if (null != key && key.contains("%")) {
//            try {
//                key = URLDecoder.decode(key, "utf-8");
//            } catch (UnsupportedEncodingException e) {
////                LogUtil.e("解码失败:" + key, e);
//            }
//        }
//    }

    /**
     * 快捷跳转
     * +动画
     *
     * @param c
     * @param other
     */
    public static void SkipActivity(Activity c, Class<?> other) {
        Intent intent = new Intent(c, other);
        toLift(c, intent);
    }

    /**
     * 快捷跳转
     * +动画
     *
     * @param c
     * @param other
     */
    public static void SkipActivityForResult(Activity c, Class<?> other, int require) {
        Intent intent = new Intent(c, other);
        toLiftForResult(c, intent, require);
    }

    /**
     * 左跳转activity
     *
     * @param c
     * @param intent
     */
    public static void toLift(Activity c, Intent intent) {
        c.startActivity(intent);
        c.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    /**
     * 左跳转activity
     *
     * @param c
     * @param intent
     */
    public static void toLiftForResult(Activity c, Intent intent, int requset) {
        c.startActivityForResult(intent, requset);
        c.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }
    public static void ShowToast(String str, Context c) {
//
//      if(null!=mMainView)  HintShow.show(mMainView,str);
//        else
        SweetAlertToast.makeText(c, str).show();
    }
    /**
     * 右结束activity
     *
     * @param c
     */
    public static void toRightFinish(Activity c) {
        c.finish();
        c.overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    public static void installAPK(String fileSavePath, Activity a) {
        installAPK(new File(fileSavePath), a);
    }

    public static void installAPK(File file, Activity a) {
//        if (!FileUtil.existsSdcard()) {
//            Runtime runtime = Runtime.getRuntime();
//            String command = "chmod -R 777 " + file.getAbsolutePath();
//            try {
//                runtime.exec(command);
//            } catch (IOException e) {
//                LogUtil.d(e.getMessage());
//            }
//        }

        // 下载完成，点击安装
        Uri uri = Uri.fromFile(file);
        // // 启动安装
        Intent install = new Intent();
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        install.setAction(Intent.ACTION_VIEW);
        install.setDataAndType(uri,
                "application/vnd.android.package-archive");
        // 安装
        toLift(a, install);
        a.finish();
    }

    /**
     * 链接跳转
     * 只有后半部分，如“categories/103”
     *
     * @param link 链接地址 自动过滤多余的参数
     * @return 跳转成功， 解析失败
     */
    public static boolean parseLink(String link, Activity c) {
        if (TextUtils.isEmpty(link)) {
//            if (x.isDebug()) ShowToast("请不要给空，没有你叫我干什么？", c);
            return false;
        }
        String[] urls = link.split("[/,?,=,&]");//,\\,\\\\          \\{   \\}
//        LogUtil.d(解析：" +link+":"+ Arrays.toString(urls));
        System.out.println("解析：" + link + ":" + Arrays.toString(urls));
        /**
         * 跳转选项
         * 1 为产品
         * 3 为搜索
         *
         */
//        int flag = -1;
//        String pram[] = null;
//        for (String url : urls) {
//            if (url == null || url.length() == 0) continue;
//            if (flag < 0) {
//                if (url.contains("products")) flag = 1;
//                else if (url.contains("categories")) flag = 2;
//                else if (url.contains("shops")) flag = 3;
//                else if (url.contains("search")) flag = 10;
//            } else {
//                switch (flag) {
//                    case 1:
//                        System.out.println("商品详情：" + url);
//                        return true;
//                    case 2:
//                        System.out.println("商品分类：" + url);
//                        return true;
//                    case 3:
//                        System.out.println("店铺详情：" + url);
//                        return true;
//                    case 10:
//                        if (null == pram) {
//                            pram = new String[2];
//                        }
//                        if (url.contains("keyword") || url.contains("scope")) continue;
//                        if (null == pram[0]) pram[0] = url;
//                        else if (null == pram[1]) {
//                            pram[1] = url;
//                            System.out.println("搜索：" + Arrays.toString(pram));
//                            // 跳转到搜索界面
////                        LogUtil.d("搜索：" + flag + Arrays.toString(pram));
//                            if ("products".equals(pram[0]) || "products".equals(pram[1])) {
//                            } else if ("shops".equals(pram[0]) || "shops".equals(pram[1])) {
//                                //Todo 商铺搜索
//                            } else break;
//                            return true;
//                        }
//                        break;
//
//                }
//
//
//            }
//
//        }
//        System.out.println("打开失败：" + flag + Arrays.toString(pram));
        return false;
    }

    /**
     * 商铺全地址解析
     *
     * @param url
     * @param c
     * @return
     */
    public static boolean parseShopAllUrl(String url, Activity c) {
        return parseLink(url, c);
    }

    /**
     * 拨打电话
     *
     * @param a
     * @param phone
     */
    public static void Call(Activity a, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        a.startActivity(intent);
        //动画；
        //a.overridePendingTransition(R.);
    }


    public static void toWeb(Activity c, String url, String title) {
        toLift(c, WebA.get(c, url, title));
    }





















    /**
     * 到应用市场评分
     *
     * @param activity
     */
    public static void ToMarket(Activity activity) {
        Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        toLift(activity, intent);
    }






















/**
 * 测试java使用
 */
    /*//
    public static void main(String[] s) {
        String str[] = {"/products/61103", "/search?keyword=匹克&scope=products"};
        for (String s2 : str)
            parseLink(s2, null);

    }
//*/


}
