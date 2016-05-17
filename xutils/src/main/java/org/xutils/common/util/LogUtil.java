/*
 * Copyright (c) 2013. wyouflf (wyouflf@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.xutils.common.util;

import android.text.TextUtils;
import android.util.Log;

import org.xutils.x;

/**
 * Log工具，类似android.util.Log。
 * tag自动产生，格式: customTagPrefix:className.methodName(L:lineNumber),
 * customTagPrefix为空时只输出：className.methodName(L:lineNumber)。
 * Author: wyouflf
 * Date: 13-7-24
 * Time: 下午12:23
 */
public class LogUtil {

    public static String customTagPrefix = "HuChongkj";

    private LogUtil() {
    }

    private static String generateTag() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }
    private static String generateTagmy() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag+"-test";
        return tag;
    }


    public static void d(String content) {
        if (!x.isDebug()) return;
        String tag = generateTag();

        Log.d(tag, content);
    }

    public static void d(String content, Throwable tr) {
        if (!x.isDebug()) return;
        String tag = generateTag();

        Log.d(tag, content, tr);
    }

    public static void e(String content) {
        if (!x.isDebug()) return;
        String tag = generateTag();

        Log.e(tag, content);
    }
    /**
     * 当前进程
     *
     * @param info
     */
    public static void LogThread(String info) {
        if (x.isDebug()) Log.d(generateTagmy() + "_Thread", Thread.currentThread().getName() + ":" + info);

    }

    /**
     * 容错Log
     *
     * @param info
     */
    public static void LogOrErr(Object info) {
        try {
            if (x.isDebug()) Log.d(generateTagmy() + ":", "有错？" + ":" + info.toString());
        } catch (Exception e) {
            Log.e(generateTagmy(), ""+e.getMessage());
        }

    }

    /**
     * 当前进程
     *
     * @param info
     */
    public static void LogNet(String info) {
//        LogLocation(info);
        if (x.isDebug()) Log.d(generateTagmy() + "_NET", info);

    }
    /**
     * 当前网络数据
     *
     * @param result
     */
    public static void LogNet(int flag,String result) {
//        LogLocation(info);
        if (x.isDebug()) Log.d(generateTagmy() ,"flag:" + flag + " result:" + result);

    }
    /**
     * Imgeview
     *
     * @param info
     *
     *
     *
     * org.xutils.common.Callback$CancelledException
     *
     */
    public static void LogIMG(String info) {
        if (x.isDebug()) Log.d(generateTagmy() + "_IMG", info);

    }

    /**
     * 显示函数调用关系
     *
     * @param err
     */
    public static void LogLocation(String err) {
        if (x.isDebug()) Log.e(generateTagmy() + "_Code", err, new Throwable("在这里！"));
    }

    public static void LogMI(String secret) {
        if (x.isDebug()) Log.d(generateTagmy() + "_Secret", secret.replaceAll("\\.", "*"));
    }

    public static void e(String content, Throwable tr) {
        if (!x.isDebug()) return;
        String tag = generateTag();
        Log.e(tag, content, tr);
    }

    public static void i(String content) {
        if (!x.isDebug()) return;
        String tag = generateTag();

        Log.i(tag, content);
    }

    public static void i(String content, Throwable tr) {
        if (!x.isDebug()) return;
        String tag = generateTag();

        Log.i(tag, content, tr);
    }

    public static void v(String content) {
        if (!x.isDebug()) return;
        String tag = generateTag();

        Log.v(tag, content);
    }

    public static void v(String content, Throwable tr) {
        if (!x.isDebug()) return;
        String tag = generateTag();

        Log.v(tag, content, tr);
    }

    public static void w(String content) {
        if (!x.isDebug()) return;
        String tag = generateTag();

        Log.w(tag, content);
    }

    public static void w(String content, Throwable tr) {
        if (!x.isDebug()) return;
        String tag = generateTag();

        Log.w(tag, content, tr);
    }

    public static void w(Throwable tr) {
        if (!x.isDebug()) return;
        String tag = generateTag();

        Log.w(tag, tr);
    }


    public static void wtf(String content) {
        if (!x.isDebug()) return;
        String tag = generateTag();

        Log.wtf(tag, content);
    }

    public static void wtf(String content, Throwable tr) {
        if (!x.isDebug()) return;
        String tag = generateTag();

        Log.wtf(tag, content, tr);
    }

    public static void wtf(Throwable tr) {
        if (!x.isDebug()) return;
        String tag = generateTag();

        Log.wtf(tag, tr);
    }

    public static void LogView(String info) {
        if (x.isDebug()) Log.d(generateTag() + "_view", info);
    }
}
