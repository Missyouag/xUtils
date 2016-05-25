package com.sc;

import android.app.Application;

import com.sc.utils.Utils;

import org.xutils.x;


/**
 * Created by Missyouag on 2015/11/13.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能
        //** 检测屏幕宽高
        Utils.setScreenWH(this);
    }


    //
//    public static String getDString(int id) {
//        try{
//            if(application!=null)return application.getString(id);
//        }catch (Exception e){
//            TestLog.LogLocation(e.hasMessage());
//        }
//        return "";
//    }
}
