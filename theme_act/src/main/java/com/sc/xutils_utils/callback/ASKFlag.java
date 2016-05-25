package com.sc.xutils_utils.callback;

/**
 * Created by Missyouag on 2016/4/4.
 */
public interface ASKFlag {
    public final String FAIL = Integer.toString(-1);
    public final String INTERNET_FAIL = Integer.toString(-2);
    /**
     * 成功
     */
    public final static int SUCCESS = 1;
    /**
     * 参数错误
     */
    public final static int ERR_PARAMETER = 2;
    /**
     * 网络错误
     */
    public final static int ERR_INTERNET = 3;
    /**
     * 服务器错误(解析错误)
     */
    public final static int ERR_SERVICE = 4;
    /**
     * 取消
     */
    public final static int ERR_CANCEL = 5;

}
