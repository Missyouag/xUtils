package com.sc.fragment;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/12.
 */
public abstract class BaseFMana extends BaseF {

    private static BaseFMana mMana;

    /**
     * Id
     */
    public String id;
    /**
     * 当前页
     */
    public int mPage;
    protected FragmentManager fm;
    protected FragmentTransaction ft;
    /**
     * 历史页
     */
    protected int mPageH;
    /**
     * 回退记录
     */
    protected int mPageHistory;
    //    private ArrayList<Integer> Pages = new ArrayList<>();
    protected ArrayList<Integer> HistoryPage = new ArrayList<>();
    protected boolean fail;


    public BaseFMana() {
        super();
        mMana = this;
    }

    public static BaseFMana getFMana() {
        return mMana;
    }

    public final static void startNext(int page, int Type) {
        if (null != mMana) mMana.goPage(page, Type);
    }

    public final static void startNext(int page) {
        if (null != mMana) mMana.goPage(page, -1);
    }

    public void setmPage(int mPage) {
        this.mPage = mPage;
    }

    /**
     * 设置默认标题
     *
     * @return
     */
    @NonNull
    public String getTopTitle() {
        return "在家淘";
    }

    @Override
    public int getViewId() {
        return R.layout.love_frag_main;
    }

    @Override
    public void init() {
        super.init();
        //恢复提交有效。 showdata会加入动画
        initPage();
    }

    private void initPage() {
        if (mPage == 0) {
            if (mType > 0) {
                //初始化type页面加载
                mPage = mType;
                mType = 0;
            } else {
                mPage = MainPage();
            }
            goPage(mPage, -1, false);
        }
    }

    @Override
    public void showData() {
        super.showData();
        initPage();
    }

    ;

    /**
     * 初始化页面
     *
     * @return
     */
    protected int MainPage() {
        return 1;
    }

    /**
     * @param page 页面编号
     * @return new Fragment
     */
    public abstract Fragment getFragment(int page);

    /**
     * 请使用{@link #getFragmentOLd(Fragment, int)}
     *
     * @param page
     * @return
     */
    private Fragment getFragmentOLd(int page) {
        if (fm != null && fm.getFragments() != null) for (Fragment fra : fm.getFragments()) {
            if (getFragmentOLd(fra, page)) return fra;
        }
        return null;
    }

    /**
     * 判断fra与pagep匹配,正确返回true
     *
     * @param fra
     * @param page
     * @return
     */
    protected abstract boolean getFragmentOLd(Fragment fra, int page);

    /**
     * 返回fragment
     *
     * @return true 返回成功
     * false 返回失败
     */
    @Override
    public boolean Back() {
        Fragment page = getFragmentAndold(mPage);
        if ((page instanceof BaseF) && ((BaseF) page).Back()) return true;
        if (mPageHistory > 0) {
            setmPage(mPageH = HistoryPage.get(--mPageHistory));
            boolean flag = fm.popBackStackImmediate();
            switchFragment(getFragmentAndold(mPage), -1, false);
            HistoryPage.remove(mPageHistory);
            return flag;
        }
        return false;
    }

    /**
     * @param page 跳转的页面
     */
    protected void goPage(int page, int Type, boolean history) {
//        Pages.add(mPageHistory, mPage);
        setmPage(page);
        Fragment fra = getFragmentAndold(page);
        if (Type > 0 && fra instanceof BaseF) ((BaseF) fra).setmType(Type);
        if (fra != null) {
            switchFragment(fra, history);
        }
    }

    protected Fragment getFragmentAndold(int page) {
        initFragmentManager();
        Fragment fra = getFragmentOLd(page);
        if (fra == null) {
            fra = getFragment(page);
        }
        return fra;
    }

    protected void initFragmentManager() {
        if (fm == null) fm = getChildFragmentManager();
    }

    /**
     * @param to
     * @param contentId
     * @param style     切换风格
     * @param history
     */
    public void switchFragment(Fragment to, int contentId, int style, boolean history) {
        // 缺少内存时被清空
        if (to == null) check();
        if (to == null) return;
        // Log.d("BUG", "主页错误:"+from+"-"+to);
        ft = null;
        switch (style) {
            case 1:
                if (!fail && isStart() && mPage != mPageH) {
                    if (mPage > mPageH) {
                        ft = fm.beginTransaction().setCustomAnimations(
                                R.anim.in_from_right, R.anim.out_to_left);
                    } else {
                        ft = fm.beginTransaction().setCustomAnimations(
                                R.anim.in_from_left, R.anim.out_to_right);
                    }
                }
                break;
            case 2:
                ft = fm.beginTransaction().setCustomAnimations(
                        R.anim.in_from_top, R.anim.out_to_top);
                break;
            case 3:
                ft = fm.beginTransaction().setCustomAnimations(
                        R.anim.in_from_bottom, R.anim.out_to_bottom);
                break;
            default:
                break;
        }
        if (ft == null) {
            ft = fm.beginTransaction();
        }

        if (fm.getFragments() != null) for (Fragment from : fm.getFragments()) {
            // 没有不需要隐藏...
            if (from != null && from != to)
                ft.hide(from);
        }
        if (!to.isVisible()) {
            if (to.isAdded()) {
                ft.show(to);
            } else {
                ft.add(contentId, to);
            }
            if (history && !(to instanceof IndexF)) {
                HistoryPage.add(mPageHistory, new Integer(mPageH));
                ft.addToBackStack("" + ++mPageHistory);
            }
        }
        Ftcommit(ft);
        //把消息界面加入控制
    }

    /**
     * 切换页面的重载，优化了fragment的切换
     *
     * @param to
     */
    public void switchFragment(Fragment to, boolean history) {
        switchFragment(to, R.id.fragment_content, 1, history);
    }

    /**
     * 切换页面的重载，优化了fragment的切换
     *
     * @param to
     */
    public void switchFragment(Fragment to, int style, boolean history) {
        switchFragment(to, R.id.fragment_content, style, history);
    }

    /**
     * 数据检查
     */
    protected void check() {

    }

    public void Ftcommit(FragmentTransaction ft2) {
        if (fail) {
            ft2.commitAllowingStateLoss();
        } else {
            ft2.commit();
        }
        mPageH = mPage;
    }

    protected abstract void goPage(int page, int Type);
}
