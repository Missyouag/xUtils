package com.sc.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.sc.data.DefaultData;

import org.xutils.x;

/**
 * Created by Administrator on 2015/11/18.
 */
public class CacheUtil {

    private static SharedPreferences sp;

    public static SharedPreferences getPreference() {
        if(sp==null)sp = x.app().getSharedPreferences(DefaultData.Preferences_name, Context.MODE_PRIVATE);
        return sp;
    }

    /**
     * 保存数据
     *
     * @param key  键
     * @param data 值
     */
    public static void SaveData(String key, String data) {
        getPreference().edit().putString(key, data).commit();
    }

}
