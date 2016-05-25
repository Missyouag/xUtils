package com.sc.adapter;

import java.util.List;

/**
 * list适配 工具类
 * Created by Missyouag on 2016/3/14.
 */
public class AdapterUtils {
    /**
     * 得到集合的长度，空返回0
     * @param list
     * @return
     */
    public static int getEmptyListSize(List list){
        return null==list?0:list.size();
    }
}
