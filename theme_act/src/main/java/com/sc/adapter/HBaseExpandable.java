package com.sc.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.BaseExpandableListAdapter;

/**
 * Created by Administrator on 2015/12/9.
 */
public abstract class HBaseExpandable extends BaseExpandableListAdapter {
    protected final Activity a;
    protected final LayoutInflater La;




    public interface ExpandableClick {
//        public void GroupClick(int groupid, boolean isExpanded);

        /**
         * 选择数据改变，刷新数据
         */
        public void flash();
    }

    public ExpandableClick getOnGroupClick() {
        return onGroupClick;
    }

    public void setOnGroupClick(ExpandableClick onGroupClick) {
        this.onGroupClick = onGroupClick;
    }

    protected ExpandableClick onGroupClick;


    protected HBaseExpandable(Activity a) {
        this.a = a;
        La = a.getLayoutInflater();
    }

    /**
     * null==list?0:list.size()
     */

    @Override
    public abstract int getGroupCount();

    /**
     * null==list?0:list.size()
     */
    @Override
    public abstract int getChildrenCount(int groupPosition);


    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition << 10 | childPosition;
    }

    public int groupId(int ChildId) {
        return ChildId >> 10;
    }

    public int childId(int ChildId) {
        return ChildId | 0x3FF;
    }

    /**
     * Id排序
     *
     * @return
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

}
