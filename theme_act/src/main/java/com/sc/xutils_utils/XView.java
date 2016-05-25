package com.sc.xutils_utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.x;

/**
 *
 * 封装三方代码
 * Created by Administrator on 2015/12/4.
 */
public class XView {

    public static void ViewFind(Activity a) {
        x.view().inject(a);
    }

    public static void ViewFind(View v) {
        x.view().inject(v);
    }

    public static void ViewFind(Object handler, View view) {
        x.view().inject(handler, view);
    }

    /**
     * 注入fragment
     *
     * @param fragment
     * @param inflater
     * @param container
     * @return
     */
    public static void ViewFind(Object fragment, LayoutInflater inflater, ViewGroup container) {
        x.view().inject(fragment, inflater, container);
    }


}
