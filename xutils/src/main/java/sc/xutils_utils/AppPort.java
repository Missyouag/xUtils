package sc.xutils_utils;

import android.text.TextUtils;


/**
 * 图片接口
 * 省略图片服务器地址
 * Created by Administrator on 2016/1/6.
 */
public class AppPort {
    /**
     * 公司主域名
     *
     * @return
     */
    public static String getMain() {
        return "www.baidu.com/";
    }

    /**
     * 图片
     * 判断是否为id
     *
     * @param url
     * @return
     */
    public static boolean isNotID(String url) {
        return url.matches("^[hfa].*?");
    }

    /**
     * 图片地址扩展
     *
     * @param url
     * @return
     */
    public static String getPicture(String url) {
        if (TextUtils.isEmpty(url) || isNotID(url))
            return url;
        return getMain() + url;
    }

    /**
     * 根据尺寸请求大小，width<= 0时应请求全屏宽度
     * @param img
     * @param width
     * @return
     */
    public static String getPicture(String img, int width) {
        return getPicture(img);
    }
}
