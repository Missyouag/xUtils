package com.sc.adapter;

import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Missyouag on 2016/3/12.
 */
public class EmptyExpandableAdapter extends HBaseExpandable {
    private final HBaseExpandable adapter;
    private final String empty;



    public EmptyExpandableAdapter(String empty, @Nullable HBaseExpandable a) {
        super(a.a);
        this.adapter = a;
        this.empty=empty;
    }



    @Override
    public int getGroupCount() {
        return adapter.getGroupCount()<1?1:adapter.getGroupCount();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return adapter.getGroupCount()<1?0:adapter.getChildrenCount(groupPosition);
    }

    @Override
    public Object getGroup(int groupPosition) {
        return adapter.getGroup(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return adapter.getChild(groupPosition, childPosition);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (adapter.getGroupCount() < 1) {
            if (convertView == null || !(convertView instanceof TextView)) {
                TextView tv = new TextView(a);
                tv.setGravity(Gravity.CENTER);
                tv.setPadding(100,100,100,100);
                tv.setText(empty);
                return tv;
            } else {
                return convertView;
            }
        } else {
            if (convertView instanceof TextView) convertView = null;
        }
        return adapter.getGroupView(groupPosition, isExpanded, convertView, parent);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return adapter.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return adapter.isChildSelectable(groupPosition, childPosition);
    }
}
