package com.sc.utils.ui;

import android.app.Activity;
import android.os.CountDownTimer;

import java.util.Timer;
import java.util.TimerTask;

import com.sc.utils.SweetAlert.SweetAlertDialog;
import com.sc.utils.SweetAlert.SweetAlertWarn;


/**
 * 等待UiWait
 * Created by Administrator on 2015/11/18.
 */
public class UiWait {
    private static UiWait mUiWait;
    private SweetAlertWarn dialog;
    private static final Object lock = new Object();
    private Timer t;
    private boolean isShown;
    /**
     * 等待显示，不会等待消失
     */
    private boolean isWaitShow;


    public boolean getWaitTime() {
        if (oldTime < 1) oldTime = System.currentTimeMillis();
        return (mWaitTime > 1 && (System.currentTimeMillis() - oldTime >= mWaitTime));
    }

    public void setWaitTime(int WaitTime) {
        this.mWaitTime = WaitTime;
        oldTime = 0;
    }

    /**
     * 等待消失时间
     */
    private int mWaitTime;

    private UiWait() {
    }


    public static UiWait getUiWait() {
        if (null == mUiWait)
            synchronized (lock) {
                if (null == mUiWait)
                    mUiWait = new UiWait();
            }
        return mUiWait;
    }

    private long oldTime;

    /**
     * 请写在访问前
     * 避免多线程错误
     *
     * @param c 考虑到加载列表需要
     * @ deprecated
     *          不需要马上显示；
     *          {@link #Wait(Activity)}
     */
    public void ShowWait(Activity c) {
        ShowWait(c, -1);
    }

    /**
     *
     * @param c
     * @param time
     *  @ deprecated  考虑到加载列表需要
     *          不需要马上显示；
     *          {@link #Wait(Activity)}
     */
    public void ShowWait(Activity c, int time) {
        setWaitTime(0);
        isShown = true;
        showWait(c, time);
    }

    private synchronized void showWait(final Activity c, final int time) {
        if (!isShown||null==c) return;
        CancelDialog();
        dialog = new SweetAlertWarn(c, SweetAlertDialog.PROGRESS_TYPE);
        dialog.showCancelButton(false);
        dialog.setCancelable(false);
        new CountDownTimer(time == -1 ? 800 * 60 : 800 * time, 200) {
            int i;

            public void onTick(long millisUntilFinished) {
                // you can change the progress bar color by ProgressHelper every 800 millis
                synchronized (UiWait.this) {
                    if (dialog == null || !isShown || c.isFinishing()
                            || getWaitTime()) {
                        cancel();
                        CancelImmediately();
                        return;
                    }
                    if (millisUntilFinished % 800 > 700) {
                        i++;
                        if (time == -1) i %= 7;
                        switch (i) {
                            case 0:
                                dialog.getProgressHelper().setBarColor(0xffAEDEF4);
                                break;
                            case 1:
                                dialog.getProgressHelper().setBarColor(0xff009688);
                                break;
                            case 2:
                                dialog.getProgressHelper().setBarColor(0xffA5DC86);
                                break;
                            case 3:
                                dialog.getProgressHelper().setBarColor(0xff80cbc4);
                                break;
                            case 4:
                                dialog.getProgressHelper().setBarColor(0xff37474f);
                                break;
                            case 5:
                                dialog.getProgressHelper().setBarColor(0xffF8BB86);
                                break;
                            case 6:
                                dialog.getProgressHelper().setBarColor(0xffA5DC86);
                                break;
                            case 7:
                                dialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                dialog.setTitleText("失败").showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .setConfirmText(null);
                                dialog.setCancelable(true);
                                dialog.setCanceledOnTouchOutside(true);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }

            public void onFinish() {
                CancelImmediately();
            }
        }.start();
        if (c.isFinishing()) CancelImmediately();
        else dialog.show();
    }

    /**
     * 等待半秒关闭（显示有效）
     */
    public synchronized void Cancel() {
        Cancel(800);
    }

    /**
     * 等待一段时间关闭（显示有效）
     *
     * @param WaitTime
     */
    public synchronized void Cancel(int WaitTime) {
        if (isWaitShow) {
            CancelImmediately();
        } else
            setWaitTime(WaitTime);
    }

    public boolean isShown() {
        return isShown;
    }

    public void setShown(boolean shown) {
        isShown = shown;
    }

    /**
     * 立即关闭窗口
     */
    public synchronized void CancelImmediately() {
        setWaitTime(0);
        isWaitShow=false;
        isShown = false;
        CancelTimer();
        CancelDialog();
    }


    private synchronized void CancelTimer() {
        if (t != null) t.cancel();
        t = null;
    }

    private synchronized void CancelDialog() {
        if (dialog != null) dialog.cancel();
        dialog = null;
    }
    /**
     * 等待显示
     *
     * @param c
     */
    public synchronized void Wait(Activity c) {
        Wait(500, 10, c);
    }

    /**
     * 等待显示
     *
     * @param time
     * @param c
     */
    public synchronized void Wait(int time, final Activity c) {
        Wait(time, 10, c);
    }

    /**
     * 等待显示
     *
     * @param time
     * @param showtime
     * @param c
     * @return 已创建返回true
     */
    public synchronized boolean Wait(int time, final int showtime, final Activity c) {
        if (null != dialog && null != t) return true;
        Cancel();
        setWaitTime(0);
        isShown = true;
        t = new Timer();
        isWaitShow = true;
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if (null != c) c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showWait(c, showtime);
                    }
                });
            }
        }, time);
        return false;
    }


}
