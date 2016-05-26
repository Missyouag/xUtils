package com.sc.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.sc.R;
import com.sc.utils.MyJavaScript;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;


/**
 * 所有网页显示activity
 * @author

 */
@SuppressLint("JavascriptInterface")
public class WebA extends BaseAct {

	private WebView webView;
	private int progress=0;
	private int progressSize=1;
	private View web_view_seekBar;
	private String title;
	private String url;
	
	private int seekBarProgressSize = 0;
	private String pamas;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		alabHideButtonRight(true);
//		alabGetButtonRight().setImageResource(R.drawable.share);
		init2();
	}

	@Override
	protected void init() {
		super.init();
		alabSetContentView(R.layout.a_act_web);
		webView= (WebView) findViewById(R.id.my_web);
	}

	@Override
	protected void BaseMessage(Message msg) {
		super.BaseMessage(msg);
		switch (msg.what) {
			case 1:
				LayoutParams lp = web_view_seekBar.getLayoutParams();
				lp.width = progress*seekBarProgressSize;
				web_view_seekBar.setLayoutParams(lp);
				if(progress>=100){
					handler.sendEmptyMessageDelayed(22, 1500);
				}
				break;
			case 22:
				web_view_seekBar.setVisibility(View.GONE);
				break;
			default:
				break;
		}
	}

	@SuppressLint("JavascriptInterface")
	private void init2() {
		titleBar.setVisibility(View.VISIBLE);
		Display currDisplay = getWindowManager().getDefaultDisplay();// 获取屏幕当前分辨率
		seekBarProgressSize = currDisplay.getWidth()/100;
		Bundle bundle=getIntent().getExtras();
		title=bundle.getString("title");
		url=bundle.getString("url");
//		LogUtil.d("TAG", "title"+title);
//		LogUtil.d("TAG", "url"+url);
		setTitle(title);
		web_view_seekBar= findViewById(R.id.web_view_seekBar);
		LayoutParams lp = web_view_seekBar.getLayoutParams();
		lp.width = 0;
		web_view_seekBar.setLayoutParams(lp);
		new CloseDialogThread().start();
		// 重要：让webview支持javascript
		WebSettings setting = webView.getSettings();
		setting.setJavaScriptEnabled(true);
		webView.setHorizontalScrollBarEnabled(false);
		setting.setCacheMode(WebSettings.LOAD_NO_CACHE);
		//webView.getSettings().setSupportMultipleWindows(true);
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); //设置滚动条样式
		// 重要：添加可以供html中可供javascript调用的接口类
		webView.addJavascriptInterface(new MyJavaScript(WebA.this,handler,webView),
				"myjavascript");
		
		
		//不去调用浏览器
		webView.setWebViewClient(new WebViewClient(){
			 @Override
			    public void onPageStarted(WebView view, String url, Bitmap favicon) {
			        super.onPageStarted(view, url, favicon);
			    }
			    @Override
			    public boolean shouldOverrideUrlLoading(WebView view, String url) {
			        view.loadUrl(url+pamas);
			        return true;
			    }
			    @Override
			    public void onPageFinished(WebView view, String url) {
			    	progressSize=10;
			    	//MyProgressDialog.dismiss();
			        super.onPageFinished(view, url);
			        
			    }
			    @Override
			    public void onReceivedError(WebView view, int errorCode,
			    		String description, String failingUrl) {
			    	super.onReceivedError(view, errorCode, "出错了！", "");
			    }
		 });
		webView.setWebChromeClient(new WebChromeClient() {
			//支持alert弹出
			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				return super.onJsAlert(view, url, message, result);
			}
			@Override
			public boolean onJsConfirm(WebView view, String url,
					String message, JsResult result) {
				return super.onJsConfirm(view, url, message, result);
			}
			});
//		String url=MyApplication.ip +"Member/agree";
//		LogUtil.e("TAG", url+"/from/ANDROID/token/"+preferences.getString("E_COMMUNITY_TOKEN", "")+"/user_type/"+preferences.getString("E_COMMUNITY_USER_TYPE", ""));
		LogUtil.LogNet("http|||"+url);
		webView.loadUrl(url);
		
	}

	//入股加载对话框没有执行就通过线程关闭
	class CloseDialogThread extends Thread {
		@Override
		public void run() {
			while(progress<=100){
				if(null==handler)return;
				try {
					progress+=progressSize;
					if(progressSize>=10){
					}else if(progress>93&&progressSize<10){
						sleep(1000);
						progressSize=1;
					}else if(progress>90&&progressSize<10){
						sleep(500);
						progressSize=1;
					}else if(progress>85&&progressSize<10){
						sleep(300);
						progressSize=1;
					}else if(progress>70&&progressSize<10){
						progressSize=1;
						sleep(90);
					}else if(progress<30){
						progressSize=5;
						sleep(90);
					}else{
						progressSize=3;
						sleep(90);
					}
					sleep(10);
					if(progress>100){
						progress=100;
						break;
					}
					handler.sendEmptyMessage(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	@Override
	protected void onStop() {
		super.onStop();
		progress=100;
	}
	/**
	 * @param cxt 上下文对象
	 * @param url 完整url路径
	 * @param title activity显示的title
	 * @return intent
	 */
	public static Intent get(Context cxt,String url,String title){
		Intent intent = new Intent(cxt,WebA.class);
		intent.putExtra("url", url);
		intent.putExtra("title", title);
		return intent;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	}
}
