package com.sc.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Missyouag on 2016/3/12.
 */
public class EmptyAdapter extends HBaseAdapter {
    private final BaseAdapter a;
    private String empty;
    private View emptyV;

    @Override
    public int getCount() {
        return a.getCount() > 0 ? a.getCount() : 1;
    }

    @Override
    public Object getItem(int position) {
        return a.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return a.getItemId(position);
    }


    public EmptyAdapter(String empty, @Nullable HBaseAdapter a) {
        super(a.c);
        this.a = a;
        this.empty = empty;
    }

    public EmptyAdapter(View empty, @Nullable HBaseAdapter a) {
        super(a.c);
        this.a = a;
        this.emptyV = empty;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (a.getCount() < 1) {
            if (convertView == null || !(convertView instanceof TextView)) {
                if (null != emptyV) return emptyV;
                if(TextUtils.isEmpty(empty))empty="请稍等";
                TextView tv = new TextView(c);
                tv.setGravity(Gravity.CENTER);
                tv.setPadding(100, 100, 100, 100);
                tv.setText(empty);
                return tv;
            } else {
                return convertView;
            }
        } else {
            if (convertView instanceof TextView) convertView = null;
        }
        return a.getView(position, convertView, parent);
    }

}
