package com.sc.activity;

import android.content.Context;

import com.sc.activity.utils.SkipUtil;


/**
 * 页面帮助类
 * Created by Administrator on 2016/2/23.
 * 1 提供defaultSystemColor;
 */
public class ActivityHelp {

    private Context a;

    public ActivityHelp(Context a) {
        this.a = a;
    }

    /**
     * 默认状态栏颜色
     * 默认为透明
     *
     * @return
     */
    public int defaultSystemBarColor() {
            return getColor(R.color.street_top_color);

    }

    public int getColor(int id) {
        return a.getResources().getColor(id);
    }

    public void ShowToast(String str) {
//        if (null != v) HintShow.show(v, str);
//        else
        SkipUtil.ShowToast( str,a);
    }
}
