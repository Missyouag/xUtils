package com.sc.fragment;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;

import com.sc.activity.BaseMana;


/**
 * 基础Fragment
 * Created by Missyouag on 2015/8/2
 */

public class BaseF extends Fragment implements ReFlashInterface {

    public boolean IsPretrain;
    public BaseMana mAct;
    /**
     * 主显View
     */
    public View mMainView;
    /**
     * fragment参数
     */
    public int mType;
    //是否显示
    protected boolean mShown;
    private boolean IsStart;
    protected Handler handler;

    public BaseF() {
        Prestrain();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        mShown = !hidden;
        if (mShown) showData();
        else hiddenData();
        super.onHiddenChanged(hidden);
    }

    /**
     * handler数据处理
     * 仅仅加写msg处理
     * 100 为固定网络监听
     *
     * @param msg
     */
    protected void BaseMessage(Message msg) {
        // Log.d("优化", msg.toString());
        if (msg.what == 100) {
        }
    }

    /**
     * 隐藏取消加载
     */
    protected void hiddenData() {
    }

    @Override
    public void onResume() {
        if (mShown) showData();
        super.onResume();
    }


    /**
     * 加载或意外关闭之后
     * 需要重新刷新数据
     */
    public void showData() {
    }

    /**
     * 预加载网络数据
     * <p/>
     * 回调需要判断isStart
     */
    protected void Prestrain() {
//        IsPretrain = true;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (!IsStart || null == mMainView) {
            SetView(inflater);
        } else {
            ViewParent group = mMainView.getParent();
            if (group instanceof ViewGroup)
                ((ViewGroup) group).removeView(mMainView);
        }
        return mMainView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!IsStart) {
            init();
            IsStart = true;
        }
        showData();
        mShown = true;
        if (IsPretrain) Flash(0);
    }

    public void init() {
        if (null == handler) handler = new Handler() {
            public void handleMessage(Message msg) {
                if (handler == null) {
                    return;
                }
                BaseMessage(msg);
            }
        };
        if (getHasSecondInit())
            mMainView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public int time;
                long old_time;

                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && time > 10) {
                        mMainView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    /**
                     * 刷新频率
                     */
                    if (old_time < System.currentTimeMillis() - (32 << time)) {
                        time++;
                        old_time = System.currentTimeMillis();
                        ViewInitSecond();
                    }
                }
            });
    }

    /**
     * 返回操作处理，
     *
     * @return 返回true表示已处理；
     */
    public boolean Back() {
        return false;
    }

    /**
     * 有高度的初始化view
     * 二次初始化view
     * <p/>
     * 需要设置{@link #getHasSecondInit()}为true
     */
    protected void ViewInitSecond() {

    }

    private void SetView(LayoutInflater inflater) {
        if (mMainView == null) {
            mMainView = inflater.inflate(getViewId(), null);
        }
    }

    /**
     * 主显view的Id
     *
     * @return
     */
    @LayoutRes
    public int getViewId() {
        return R.layout.a_aa_test_view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mAct = (BaseMana) activity;
    }

    /**
     * 设置界面风格
     *
     * @param color
     */
    public void setThemeStyle(int color) {

    }

    public void setmType(int Type) {
        this.mType = Type;
    }

    /**
     * 是否已经被创建
     *
     * @return
     */
    public boolean isStart() {
        return IsStart;
    }

    /**
     * {@link #ViewInitSecond()}
     *
     * @return
     */
    public boolean getHasSecondInit() {
        return false;
    }

    /**
     * 刷新界面
     * 默认0刷新所有
     *
     * @param flag
     */
    @Override
    public boolean Flash(int flag) {
        if (!isStart() || flag < 0|| Looper.myLooper() != Looper.getMainLooper()) {
            if (null != mAct) {
                mAct.handler.removeMessages(mAct.FlagFlash);
                mAct.handler.sendMessageDelayed(mAct.handler.obtainMessage(mAct.FlagFlash, 0, -flag), 40);
            }
            return false;
        }
        return true;
    }
}
