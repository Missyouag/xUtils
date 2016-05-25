package com.sc.utils.ui;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Administrator on 2015/12/4.
 */
public class HintShow {


    public static void show(View v,String str){
        Snackbar.make(v, str, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }
}
