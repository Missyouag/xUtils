package com.sc.data.service_util;

import android.widget.TextView;

/**
 * Created by Administrator on 2016/1/22.
 */
public class Text_Util {
    public static void setTimeText(TextView textView, String time) {
        if (textView != null) textView.setText(getTime(time));
    }

    /**
     * 时间格式化
     *
     * @param time 2016-05-05 23:30:00
     * @return 2016-05-05
     */
    public static String getTime(String time) {
        return getTime(time, 1);
    }

    /**
     * 时间格式化2
     *
     * @param time 2016-05-05 23:30:00
     * @param type 样式
     * @return 2016-05-05或2016.05.05
     */
    public static String getTime(String time, int type) {
        int i = null != time ? time.indexOf(' ') : 0;
        if (i > 0 && type == 2) time = time.replace("-", ".");
        return i > 0 ? time.substring(0, i) : time;
    }

    /**
     * 时间格式化
     *
     * @param time 2016-05-05 23:30:00.sdsa
     * @param type 样式
     * @return 2016-05-05 23:30:00或2016.05.05 23:30:00
     */
    public static String getTimeWithHour(String time, int type) {
        int i = null != time ? time.indexOf('.') : 0;
        if (i > 0 && type == 2) time = time.replace("-", ".");
        return i > 0 ? time.substring(0, i) : time;
    }

    public static void setSalaryText(TextView textView, String salary) {
        String str = "面议";
        try {
            int s = Integer.parseInt(salary);
            if (s > 0)
                str = salary + " 元/月";
        } catch (Exception e) {
        }
        textView.setText(str);
    }
}
