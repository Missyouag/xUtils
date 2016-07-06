package com.sc.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;


import org.xutils.x;

import java.util.ArrayList;
import java.util.Timer;

import com.sc.R;
import com.sc.activity.utils.XPageUtil;
import com.sc.activity.utils.XSkipUtil;
import com.sc.data.DefaultData;
import com.sc.fragment.BaseF;
import com.sc.utils.system.SystemBarTintManager;

/**
 * @author shenchen 11/13
 */
public abstract class BaseMana extends FragmentActivity {

    public static final int FlagNOFull = 80;
    public static final int FlagFull = 81;
    /**
     * 界面刷新，
     * <p/>
     * arg1为界面参数 0为当前界面
     * arg2为刷新参数
     */
    public static final int FlagFlash = 100;
    /**
     * Id
     */
    public String key;
    /**
     * 子类型
     */
    public String[] subtitle;
    /**
     * `
     * Utils.PREFERENCES_NAME 的本地数据
     */
    public SharedPreferences preferences;
    /**
     * 当前页
     */
    public int mPage;
    /**
     * handler
     */
    public Handler handler;
    /**
     * 标题
     */

    private TextView topTitleTextView;
    /**
     * 所有view
     */

    protected RelativeLayout mAllView;


    public View titleALL;
    protected View titleBar;
    // private Spinner spinner;

    protected View bottomBar;

    public TextView subMenu;
    /**
     * 类型
     * 设置页面
     */
    protected int type;
    /**
     * 顶部标题栏图片
     */
    protected FragmentManager fm;
    protected boolean isStart = true;
    protected Intent mIntent;
    /**
     * 历史页
     */
    protected int mPageH;
//    private ArrayList<Integer> Pages = new ArrayList<>();
    /**
     * 回退记录
     */
    protected int mPageHistory;

    protected ArrayList<Integer> HistoryPage = new ArrayList<>();


    /**
     * 系统状态栏
     */
    protected SystemBarTintManager mSystemBar;
    protected boolean fail;
    /**
     * 当按手机上的回退按钮市退出系统
     */
    Timer timer = new Timer();


    //    private void initSystemBar() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(true);
//            SystemBarTintManager tintManager = new SystemBarTintManager(this);
//            tintManager.setStatusBarTintEnabled(true);
//            tintManager.setStatusBarTintResource(R.color.actionbar_bg);
//            SystemBarConfig config = tintManager.getConfig();
//            listViewDrawer.setPadding(0, config.getPixelInsetTop(true), 0, config.getPixelInsetBottom());
//        }
//    }
    @NonNull
    private ActivityHelp mHelper;
    private LayoutInflater mInflater;

    /**
     * 监听页面改动
     *
     * @param mPage
     */
    public void setPage(int mPage) {
        this.mPage = mPage;
    }

    //全屏
    protected void setFullScreen() {
//        getWindow().setBackgroundDrawable(ContextCompat.getDrawable(this, R.mipmap.welcome_bg_first_show));
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAllView.setVisibility(View.INVISIBLE);
        handler.sendEmptyMessageDelayed(FlagNOFull, 2000);
    }

    //退出全屏
    protected void quitFullScreen() {
        final WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setAttributes(attrs);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //转到第一屏
        mAllView.setVisibility(View.VISIBLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(0xffEEEEEE));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setHelper();
        setContentView(getContentViewId());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //透明状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = getWindow();
//            // Translucent status bar
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//        setWindowForSystemBar();

        //设置沉浸效果
        mSystemBar = new SystemBarTintManager(this);
        mSystemBar.setStatusBarTintEnabled(true);
        mSystemBar.setNavigationBarTintEnabled(true);
        setWindowForSystemBar();

        //初始化控件
        titleBar = findViewById(R.id.title_show);
        topTitleTextView = (TextView) findViewById(R.id.title);
        mAllView = (RelativeLayout) findViewById(R.id.main);
        subMenu = (TextView) findViewById(R.id.base_submenu);
        titleALL = findViewById(R.id.title_all);
        bottomBar = findViewById(R.id.main_bottom);


        //设置前置效果
        x.view().inject(this);


        initData();
        ChangeDefaultView();
//        PushManager.startWork(this, PushConstants.LOGIN_TYPE_API_KEY, "PeCAlGXHthIBOjjD5HCl3BlE");
//        PushManager.enableLbs(this);
//        //测试  pushManager
//        PushSettings.enableDebugMode(this, true);
//    首页背景
    }

    protected void setHelper() {
        mHelper = new ActivityHelp(this);
    }

    /**
     * 标题栏透明 不占位置
     */
    public void setWindowForSystemBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
    }

    /**
     * 严重错误
     * 加载失败调用以关闭回退；
     */
    public void initFail() {
        ShowToast("加载失败");
        finishAct();
    }

    /**
     * 仅仅加写msg处理
     * 100 为固定网络监听
     *
     * @param msg
     */
    protected void BaseMessage(Message msg) {
        switch (msg.what) {
            case FlagFlash:
                BaseF b;
                if (msg.arg1 != 0) {
                    b = ((BaseF) getFragmentAndOld(msg.arg1));
                } else
                    b = ((BaseF) getFragmentAndOld(mPage));
                if (null != b) b.Flash(msg.arg2);
                break;
        }
        // Log.d("优化", msg.toString());
//        if (msg.what == 100) {
//        }
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (topTitleTextView != null) topTitleTextView.setText(title);
    }

    /**
     * 设置默认标题
     *
     * @return
     */
    @NonNull
    public String getTopTitle() {
        return mHelper.getTopTitle();
    }

    /**
     * 修改默认界面
     */
    protected void ChangeDefaultView() {
    }


    /**
     * 修改标题栏
     *
     * @param Style 0 隐藏
     *              1 搜索
     *              4 搜索左侧栏
     *              2 普通
     *              3 留下顶部背景
     */
    public void setTitleBarStyle(int Style) {
        if (null != titleBar) { //&& null != titleBar2
            switch (Style) {
                case 0:
                    setTitleBackColor(0, true);
                    titleBar.setVisibility(View.GONE);
//                    titleBar2.setVisibility(View.GONE);
                    break;
                case 3:
                    setTitleBackColor(0, false);
                    titleBar.setVisibility(View.GONE);
//                    titleBar2.setVisibility(View.GONE);
                    break;
                case 4:
                case 1:
//                    subSelect.setVisibility(Style == 4 ? View.VISIBLE : View.GONE);
                    setTitleBackColor(0);
                    titleBar.setVisibility(View.GONE);
//                    titleBar2.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    setTitleBackColor(0);
                    titleBar.setVisibility(View.VISIBLE);
//                    titleBar2.setVisibility(View.GONE);
                    break;
            }
        }
    }

    public void setSubMenu(@NonNull String str) {
        if (null != subMenu) {
            subMenu.setVisibility(View.VISIBLE);
            subMenu.setText(str);
        }
    }

    /**
     * 默认状态栏颜色
     * 默认为透明
     *
     * @return
     */
    public int defaultSystemBarColor() {
        return mHelper.defaultSystemBarColor();
    }

    /**
     * 设置系统状态栏
     * 和标题栏
     * 0为自定义标题栏
     *
     * @param color
     */
    protected void setTitleBackColor(int color) {
        setTitleBackColor(color, false);
    }

    /**
     * 设置标题 是否隐藏
     * 隐藏color 0 hide true
     *
     * @param color
     * @param hide
     */
    protected void setTitleBackColor(int color, boolean hide) {
        if (color == 0 && !hide) color = defaultSystemBarColor();
        if (color != 0) {
            titleALL.setVisibility(View.VISIBLE);
            titleALL.setBackgroundColor(color);
        } else {
            titleALL.setVisibility(View.GONE);
            mSystemBar.setStatusBarTintColor(color);
        }
    }

    /**
     * 设置主显view
     *
     * @return
     */
    protected int getContentViewId() {
        return R.layout.a_actfrag_base;
    }

    /**
     * 使用finishAct代替finish
     * <p/>
     * {@link #finishAct()}
     */
    @Deprecated
    @Override
    public void finish() {
        super.finish();
    }

    /**
     * 结束动画
     */
    public void finishAct() {
        finish();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    @Override
    protected void onResume() {
        fail = true;
        super.onResume();
        initView();
//        fail=false;
        fail = false;
    }

    /**
     * oResume调用
     */
    protected void initView() {
        if (mPage <= 0) {
            if (type > 0) {
                //初始化type页面加载
                setPage(type);
                type = 0;
            } else {
                setPage(MainPage());
            }
        }
        goPage(mPage, -1, false);
    }

    /**
     * 初始化页面
     *
     * @return
     */
    public int MainPage() {
        return 1;
    }

    /**
     * @param page 页面编号
     * @return new Fragment
     */
    public abstract Fragment getFragment(int page);

    /**
     * 请重写{@link #getFragmentOld(Fragment, int)}
     *
     * @param page
     * @return
     */
    protected Fragment getFragmentOld(int page) {
        if (fm != null && fm.getFragments() != null) for (Fragment fra : fm.getFragments()) {
            if (getFragmentOld(fra, page)) return fra;
        }
        return null;
    }

    /**
     * 请重写{@link #getFragmentOld(Fragment, int)}
     *
     * @param page
     * @return
     */
    protected void removeFragmentOld(int page) {
        fm.getFragments().remove(getFragmentOld(page));
    }

    ;

    /**
     * 判断fra与pagep匹配,正确返回true
     *
     * @param fra
     * @param page
     * @return
     */
    protected abstract boolean getFragmentOld(Fragment fra, int page);

    /**
     * 返回fragment
     *
     * @return true 返回成功
     * false 返回失败
     */
    public boolean BackFragment() {
        Fragment page = getFragmentAndOld(mPage);
        if ((page instanceof BaseF) && ((BaseF) page).Back()) return true;
        if (mPageHistory > 0) {
            boolean flag = fm.popBackStackImmediate();
            setPage(mPageH = HistoryPage.get(--mPageHistory));
            switchFragment(getFragmentAndOld(mPage), -1, false);
            HistoryPage.remove(mPageHistory);
            return flag;
        }
        return false;
    }

    /**
     * 取消大字体的效果
     *
     * @return
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    /**
     * 布局按钮监听
     *
     * @param v
     */
    public void btnClick(View v) {
        if (v.getId() == R.id.btn_back) {
            onBackPressed();
        }
    }

    /**
     * handler 重写进程 和前置加载数据
     * Initialize interface controls
     */
    protected void initData() {
        if (null == handler) handler = new Handler() {
            public void handleMessage(Message msg) {
                if (handler == null) {
                    return;
                }
                BaseMessage(msg);
            }
        };

        // 获取本地数据
        preferences = getSharedPreferences(DefaultData.Preferences_name,
                Activity.MODE_PRIVATE);
        fm = getSupportFragmentManager();
        initIntent();
    }

    private void initIntent() {
        mIntent = getIntent();
        type = mIntent.getIntExtra(XSkipUtil.Skip_type, -1);
        String keyCache = mIntent.getStringExtra(XSkipUtil.Skip_key);
        if (!TextUtils.isEmpty(keyCache)) key = keyCache;
        String[] subtitleCache = mIntent.getStringArrayExtra(XSkipUtil.Skip_keys);
        if (null != subtitleCache) subtitle = subtitleCache;
        String str = mIntent.getStringExtra(XSkipUtil.Skip_title);
        if (str == null || str.equals("")) {
            str = getTopTitle();
        }
        setTitle(str);
    }

    /**
     * @param page 跳转的页面
     */
    protected void goPage(int page, int Type, boolean history) {
//        Pages.add(mPageHistory, mPage);
        setPage(page);
        Fragment fra = getFragmentAndOld(page);
        if (fra != null) {
            if (Type > 0 && fra instanceof BaseF) ((BaseF) fra).setmType(Type);
            switchFragment(fra, history);
        }
    }

    /**
     * 重新启动页面
     * @param page 跳转的页面
     */
    protected void reStartPage(int page, int Type, boolean history) {
//        Pages.add(mPageHistory, mPage);
//        setPage(page);
//        Fragment fra = getFragmentOld(page);
        removeFragmentOld(page);
        goPage(page, Type, history);
    }


    public Fragment getFragmentAndOld(int page) {
        initFragmentManager();
        Fragment fra = getFragmentOld(page);
        if (fra == null) {
            fra = getFragment(page);
        }
        return fra;
    }

    protected void initFragmentManager() {
        if (fm == null) fm = getSupportFragmentManager();
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
        FragmentTransaction ft = null;
        switch (style) {
            case 1:
                if (!fail && !isStart && mPage != mPageH && (mPageH % XPageUtil.indexPageNum < 100 || ((null != getFragmentOld(mPage)) && ((BaseF)
                        getFragmentOld(mPage)).isStart()))) {
                    if (mPage > mPageH & mPage % XPageUtil.indexPageNum < 100) {
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
        if (null == ft) ft = fm.beginTransaction();
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

            isStart = false;
            if (history) {//&& !(to instanceof IndexF)
                HistoryPage.add(mPageHistory, new Integer(mPageH));
                ft.addToBackStack("" + mPageHistory++);
            }
        }
        FtCommit(ft);
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

    @Override
    protected void onDestroy() {
        // CacheData.shopMainMune = null;
        // CacheData.shopSonMune = null;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        getWindow().setBackgroundDrawable(null);
        super.onDestroy();
        //关闭所有
//        System.exit(0);
    }

    @Override
    public void onBackPressed() {
        if (!BackFragment()) {
            finishAct();
        }
    }

    public void ShowToast(String str) {
        mHelper.ShowToast(str);
    }

    /**
     * 提交变换
     *
     * @param ft2
     */
    public void FtCommit(FragmentTransaction ft2) {
        if (fail) {
            ft2.commitAllowingStateLoss();
        } else {
            ft2.commit();
        }
        mPageH = mPage;
    }

    public void HiddenBottom() {
        if (bottomBar.getVisibility() != View.GONE) {
            setAnimaGoneDown(bottomBar);
        }
    }

    public void ShowBottom() {
        if (bottomBar.getVisibility() != View.VISIBLE) {
            setAnimShowBottom(bottomBar);
        }
    }

    public void HiddenTop() {
        if (titleBar.getVisibility() != View.GONE) {//|| titleBar2.getVisibility() != View.GONE
            setAnimGoneUp(titleBar);
//            setAnimGoneUp(titleBar2);
        }
    }

    public void ShowTop() {
        if (titleBar.getVisibility() != View.VISIBLE) {//|| titleBar2.getVisibility() != View.VISIBLE
            setAnimShowUp(titleBar);
//            setAnimShowUp(titleBar2);
        }

    }


    public void setAnimaGoneDown(View v) {
//        ObjectAnimator animator1 = ObjectAnimator.ofInt(v, "translationY", 0, 100);
//        animator1.setDuration(500);
//        animator1.start();
        v.setAnimation(AnimationUtils.loadAnimation(this, R.anim.out_to_bottom));
        v.setVisibility(View.GONE);
    }

    public void setAnimGoneUp(View v) {
//        ObjectAnimator animator = ObjectAnimator.ofInt(v, "top", 0, -v.getHeight());
//        animator.setDuration(500);
//        animator.start();
//        animator.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                int left = view.getLeft()+(int)(p2-p1);
//                int top = view.getTop();
//                int width = view.getWidth();
//                int height = view.getHeight();
//                view.clearAnimation();
//                view.layout(left, top, left+width, top+height);
//            }
        v.setAnimation(AnimationUtils.loadAnimation(this, R.anim.out_to_top));
        v.setVisibility(View.GONE);
    }

    public void setAnimShowUp(View v) {
        v.setAnimation(AnimationUtils.loadAnimation(this, R.anim.in_from_top));
        v.setVisibility(View.VISIBLE);
    }

    public void setAnimShowBottom(View v) {
        v.setAnimation(AnimationUtils.loadAnimation(this, R.anim.in_from_bottom));
        v.setVisibility(View.VISIBLE);
    }

    public View getViewByID(int layoutResId) {
        return getViewByID(layoutResId, null, false);
    }

    public View getViewByID(int layoutResId, ViewGroup root, boolean attachToRoot) {
        if (null == mInflater)
            mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return mInflater.inflate(layoutResId, root, attachToRoot);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initIntent();
        ChangeDefaultView();
    }

    /**
     * 解析字符串，跳转到指定页面。
     *
     * @param url
     */
    public final void ParseUrl(String url) {
        XSkipUtil.parseShopAllUrl(url, this);
    }

    /**
     * 快捷跳转到固定页面
     * 使用复杂
     * gopage代替
     * {@link XPageUtil}
     *
     * @param page 0为跳转到首页
     */
    public void startNext(int page) {
    }
}
