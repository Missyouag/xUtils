package com.sc.utils.other;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by Administrator on 2016/4/20.
 */
public class ScreenShotUtils {
    /**
     * 进行截取屏幕
     * @param pActivity
     * @return bitmap
     */
    public static Bitmap takeScreenShot(Activity pActivity)
    {
        Bitmap bitmap=null;
        View view=pActivity.getWindow().getDecorView();
        // 设置是否可以进行绘图缓存
        view.setDrawingCacheEnabled(true);
        // 如果绘图缓存无法，强制构建绘图缓存
        view.buildDrawingCache();
        // 返回这个缓存视图
        bitmap=view.getDrawingCache();

        // 获取状态栏高度
        Rect frame=new Rect();
        // 测量屏幕宽和高
        view.getWindowVisibleDisplayFrame(frame);
        int stautsHeight=frame.top;
       // Log.d("jiangqq", "状态栏的高度为:"+stautsHeight);

        int width=pActivity.getWindowManager().getDefaultDisplay().getWidth();
        int height=pActivity.getWindowManager().getDefaultDisplay().getHeight();
        // 根据坐标点和需要的宽和高创建bitmap
        bitmap= Bitmap.createBitmap(bitmap, 0, stautsHeight, width, height - stautsHeight);
        return bitmap;
    }

}
