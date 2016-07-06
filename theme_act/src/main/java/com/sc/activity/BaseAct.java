package com.sc.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.sc.R;
import com.sc.activity.utils.XSkipUtil;
import com.sc.data.DefaultData;
import com.sc.utils.system.SystemBarTintManager;

import sc.xutils_utils.XView;


/**
 * Created by Missyouag on 2015/11/13.
 */
public class BaseAct extends Activity {
    public boolean isBefore;
    public SharedPreferences preferences;
    /**
     * 前置数据
     */
    public Intent mIntent;
    protected View titleBar;
    /**
     * 隐藏将没有bar,空白高度
     */
    protected View titleALL;
    protected Handler handler;
    /**
     * 中间主显View
     */
    protected View mMainView;
    /**
     * 系统状态栏
     * <p/>
     * <p/>
     * android:fitsSystemWindows="true"
     * android:clipToPadding="true"
     */
    protected SystemBarTintManager mSystemBar;
    /**
     * 如果重写init（）函数，请调用父类
     */
    protected String mKey;
    /**
     * 如果重写init（）函数，请调用父类
     */
    protected int mType;
    protected String[] mKeys;
//    private ConnectivityManager connectivityManager;
//    private NetworkInfo info;
//    private RelativeLayout internet_hint;
//    private View titleShow;
    private TextView top;
    @NonNull
    private ActivityHelp mHelper;
    protected TextView subMenu;

    //全屏
    protected void setFullScreen() {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    //退出全屏
    protected void quitFullScreen() {
        final WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setAttributes(attrs);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
//       setWindowForSystemBar();
        setHelper();
        //设置沉浸效果
        mSystemBar = new SystemBarTintManager(this);
        mSystemBar.setStatusBarTintEnabled(true);
        mSystemBar.setNavigationBarTintEnabled(true);

        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 竖屏锁定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mIntent = getIntent();
        preferences = getSharedPreferences(DefaultData.Preferences_name, MODE_PRIVATE);
        init();
        setWindowForSystemBar();

        titleBar=  findViewById(R.id.title_show);
        top= (TextView) findViewById(R.id.title);
        titleALL=findViewById(R.id.title_all);
        subMenu = (TextView) findViewById(R.id.base_submenu);

        XView.ViewFind(this);
        setTitleBackColor(0);
        initMethod();
        getData();
    }

    /**
     * 设置帮助类
     */
    protected void setHelper() {
        mHelper = new ActivityHelp(this);
    }

    /**
     * 默认状态栏颜色
     * 默认为透明
     *
     * @return
     */
    protected int defaultSystemBarColor() {
        return mHelper.defaultSystemBarColor();
    }

    public int getColor2(int id) {
        return getResources().getColor(id);
    }

    /**
     * 设置标题 是否隐藏
     * 隐藏color0 hide true
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
     * 设置系统状态栏
     * 和标题栏
     * 0为自定义标题栏
     * 隐藏全部标题
     * {@link  #hideTitleBar()}
     *
     * @param color
     */
    protected void setTitleBackColor(int color) {
        setTitleBackColor(color, false);
    }

    /**
     * 隐藏标题栏
     */
    protected void hideTitleBar() {
        setTitleBackColor(0, true);
    }

    /**
     * 严重错误
     * 加载失败调用以关闭回退；
     */
    public void initFail() {
        ShowToast(getString(R.string.loading_err));
        finishAct();
    }

    /**
     * 获取标题
     */
    private void getData() {
        String str = mIntent.getStringExtra(XSkipUtil.Skip_title);
        if (str == null || str.equals("")) {
            str = getTopTitle();
        }
        setTitle(str);
    }

    public void setTitle(String title) {
        super.setTitle(title);
        if (top != null) top.setText(title);
    }

    /**
     * 设置默认标题
     *
     * @return
     */
    protected String getTopTitle() {
        return mHelper.getTopTitle();
    }

    /**
     * handler 重写进程 和前置加载数据
     */
    protected void initMethod() {
        handler = new Handler() {
            public void handleMessage(Message msg) {
                if (handler == null) {
                    return;
                }
                BaseMessage(msg);
            }
        };

    }

    /**
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

    @Override
    protected void onPause() {
        isBefore = false;
        super.onPause();
    }


    /**
     * 初始化view
     * 不能写入数据监听
     */
    protected void init() {
        setContentView(R.layout.a_act_base);

        mKey = mIntent.getStringExtra(XSkipUtil.Skip_key);
        mType = mIntent.getIntExtra(XSkipUtil.Skip_type, -1);
        mKeys = mIntent.getStringArrayExtra(XSkipUtil.Skip_keys);

//        IntentFilter mFilter = new IntentFilter();
//        // 网络连接监听广播
//        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        // 强制下线广播
//        mFilter.addAction("com.stcyclub.e_community.exit_system");
//        // 通知广播
//        mFilter.addAction("com.stcyclub.e_community.push_notice");
//        registerReceiver(mReceiver, mFilter);
    }

    @Override
    protected void onResume() {
        isBefore = true;
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initMethod();
        getData();
    }

    /**
     * 设置主内容区域的layoutRes
     *
     * @param layoutResId
     */
    public void alabSetContentView(int layoutResId) {
        mMainView = getViewbyID(layoutResId);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        mMainView.setLayoutParams(lp);
        alabSetContentView(mMainView);
    }

    public View getViewbyID(int layoutResId) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(layoutResId, null);
    }

    public void alabSetContentView(View mMainView) {
        LinearLayout llContent = (LinearLayout) findViewById(R.id.main_layout);
        llContent.addView(mMainView);
    }

    /**
     * 标题栏透明 不占位置
     */
    protected void setWindowForSystemBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
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

    @Override
    public void onBackPressed() {
        finishAct();
    }

    /**
     * 结束动画
     */
    protected void finishAct() {
        finish();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    public void btnClick(View v) {
        if (v.getId() == R.id.btn_back) {
            finishAct();
        }
//        else if (v.getId() == R.id.internet_hint) {
//			startActivity(new Intent(Settings.ACTION_SETTINGS));// 进入无线网络配置界面
//		}

    }

    @Override
    protected void onDestroy() {
//        unregisterReceiver(mReceiver);
        // handler.removeMessages(100);
        handler = null;
        super.onDestroy();
    }

//    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
////				connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
////				info = connectivityManager.getActiveNetworkInfo();
////				if (info != null && info.isAvailable()) {
////					internet_hint.setVisibility(View.GONE);
////					String name = info.getTypeName();
////					// 判断是否更新了数据，如果没有更新数据就启动服务更新数据
////					// if(!preferences.getBoolean("E_COMMUNITY_ASYLOGIN_SUCCESS",
////					// true)){
////					// intent=new Intent(BaseActivity.this,UpdateInfo.class);
////					// startService(intent);
////					// }
////				} else {
////					internet_hint.setVisibility(View.VISIBLE);
////					// intent=new Intent(BaseActivity.this,UpdateInfo.class);
////					// stopService(intent);
////				}
//            } else if (action.equals("com.stcyclub.e_community.exit_system")) {
//                if (mFlag != 1) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
//                    builder.setMessage("您的账号已在其他设备上登陆！如果不是您本人操作，强烈建议您修改密码！");
//                    builder.setTitle("强制下线通知");
//                    builder.setPositiveButton("重新登陆",
//                            new AlertDialog.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog,
//                                                    int arg1) {
//                                    dialog.dismiss();
//                                }
//                            });
//                    builder.setNegativeButton("退出",
//                            new AlertDialog.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog,
//                                                    int arg1) {
//                                    dialog.dismiss();
//                                }
//                            });
//                    builder.setCancelable(false);
//                    builder.create().show();
//                }
//            } else if (action.equals("com.stcyclub.e_community.push_notice")) {
//            }
//        }
//    };

    protected boolean IsLogin() {
        return true;
    }


    public void ShowToast(String str) {
        mHelper.ShowToast(str);
    }


    /**
     * 刷新界面信息
     */
    public void ReFlash() {
    }


}
