package com.sc.xutils_utils;

/**
 * Created by Missyouag on 2015/8/5.
 */

import android.content.Context;
import android.text.TextUtils;


import com.sc.xutils_utils.callback.HttpCacheCallBack;
import com.sc.xutils_utils.callback.HttpCallBack;
import com.sc.xutils_utils.callback.HttpReturn;
import com.sc.xutils_utils.callback.Receiver;

import org.xutils.common.Callback;
import org.xutils.common.task.Priority;
import org.xutils.http.RequestParams;
import org.xutils.x;



/**
 * 网络请求 6.15 需要借助BaseBean 返回数据在T new TypeToken<BaseBean>() { }.getType()
 * <p>
 * xutil库抽取
 * <p>
 *
 * @author chengxuyuan2
 */

public class XHttp {


    /**
     * 使用Post
     * <p>
     * b,是否显示信息 returnBean<T> 接受bean
     */

    public static Callback.Cancelable AskPost(String url, Receiver callBack, String... body) {
        return Post(getRequestParams(url, body), callBack);
    }

    public static Callback.Cancelable Post(RequestParams params, Receiver callBack) {
        return Post(params, new HttpCallBack(callBack));
    }

    public static <T> Callback.Cancelable Post(RequestParams entity,
                                               Callback.CommonCallback<T> callback) {
//        entity.setCancelFast(false);
        return x.http().post(entity, callback);
    }

    /**
     * returnBean<T> 接受bean
     */
    public static Callback.Cancelable HttpGet(String url,
                                              HttpReturn flash, String... body) {
        return Get(url, new Receiver(flash), body);
    }

    /**
     * returnBean<T> 接受bean
     */
    public static Callback.Cancelable Get(String url,
                                          Receiver callBack, String... body) {
        return Get(callBack, getRequestParams(url, body));
    }

    public static Callback.Cancelable GetWithCache(Receiver callBack, RequestParams params) {
        return GetWithCache(callBack, params, 7 * 24 * 60 * 60 * 1000);
    }

    /**
     * @param callBack
     * @param params
     * @param MaxAge   缓存时间
     * @return
     */
    public static Callback.Cancelable GetWithCache(Receiver callBack, RequestParams params, long MaxAge) {
        params.setCacheMaxAge(MaxAge);
//        params.setCacheDirName(NativeFile.Http_Cache);
        params.setCacheSize(40 * 1024 * 1024);
//        params.setConnectTimeout(1000 * 8);
        params.setPriority(Priority.BG_TOP);
//        params.setCancelFast(false);
//        params.setCacheSize(1024 * 1024 * 2);
        return Get(params, new HttpCacheCallBack(callBack));
    }

    public static Callback.Cancelable Get(Receiver callBack, RequestParams params) {
//        params.setCacheDirName(NativeFile.File_Cache);
//        params.setCacheSize(1024 * 1024 * 2);
        return Get(params, new HttpCallBack(callBack));
    }

    public static <T> Callback.Cancelable Get(RequestParams params, Callback.CommonCallback<T> callback) {
        return x.http().get(params, callback);
    }

    /**
     * 通过Url和请求参数获得请求头
     *
     * @param url
     * @param body 成双  参数名+参数值
     * @return
     */
    public static RequestParams getRequestParams(String url, String... body) {
        RequestParams params = new RequestParams(url);
        if (null != body) for (int i = 0; i < body.length - 1; i++) {
            if (!TextUtils.isEmpty(body[i + 1])) params.addBodyParameter(body[i], body[++i]);
            else i++;
        }
        return params;
    }


    /**
     * 得到错误字符串
     *
     * @param flag
     * @param str
     * @param c
     * @return
     */
    public static String getAskString(int flag, String str, Context c) {
        switch (flag) {
//            case ASKInterface.SUCCESS:
//                return c.getString(R.string.success);
//            case ASKInterface.ERR_INTERNET:
//                return c.getString(R.string.errinternet);
//            case ASKInterface.ERR_SERVICE:
//                return c.getString(R.string.errserver);
//            case ASKInterface.ERR_CANCEL:
//                return c.getString(R.string.dialog_cancel);
            //服务器提供错误信息
//            case ASKInterface.ERR_PARAMETER:
//                return c.getString(R.string.success);

        }

        return str;
    }


}


