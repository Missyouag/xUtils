package com.sc.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Missyouag on 2015/9/22.
 */
public abstract class HBaseAdapter extends BaseAdapter {
    protected final Activity c;
    protected final LayoutInflater La;
    protected final AbsListView.LayoutParams prams;

    public HBaseAdapter(Activity c){
        this.c = c;
        La = c.getLayoutInflater();
        prams=new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    /**
     * 判空操作{@link AdapterUtils#getEmptyListSize(List)}
     */

    @Override
    public abstract int getCount();


}
