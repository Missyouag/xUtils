package com.sc.data;


import sc.NativeFile;

/**
 * 固定数据位置
 */
public class DefaultData {

    /**
     * Preferences名字
     */
    public static final String Preferences_name = NativeFile.getCompanyName();
    public static final String Parse_url = "url";

    /*
    *  登录账号信息
     */
    public static final String UserName = "UserName";
    /*
    *  登录手机信息
     */
    public static final String UserPhone = "UserPhone";
    public static final String UserPsw = "UserPsw";
    public static final String SelectInt = "selectInt";


    /**
     * 进入activity 位置
     */
    public static final String InInt = "inInt";



    /**
     * 登陆显示 与版本统计
     * 考虑到下次更新显示问题
     */
    public static final String Logo_First = "logo_now";
    /**
     * 可以获得上次版本
     */
    public static final int currInt = 1;
    // 删除原来的key
//	public static String Logo_FirstO = "logo_first";
    //地图放大倍数
    public final static int mapZoom = 18;


    public final static String My_head="my_head.png";

    public class SettingD{
        public final static String picture="setting_picture";
        public final static String notice="setting_notice";
        public final static String recommend="setting_recommend";
        public final static String comment="setting_comment";
    }

}
