package com.sc.utils;


import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebView;

public class MyJavaScript {  
	public static final int CAMERA  = 0x01; 
      
      
	private final int REQUEST_CONTACT = 1;
    private WebView webview;
    //使用一个handler来处理加载事件  
    private Handler handler;
     private Context mContext;
    public MyJavaScript(Context context, Handler handler, WebView webView){
        this.handler = handler;
        webview = webView; 
        mContext  =  context;
    }  


    /**
     * 关闭加载框
     */
   public void closeDialog(){
//    	Log.d("TAG", "关闭加载框");
//    	handler.sendEmptyMessage(Utils.CLOSE_DIALOG);
   }
    /**
     * 打开加载框
     */
    public void openDialog(){
    	Log.d("TAG", "关闭加载框");
//    	handler.sendEmptyMessage(Utils.OPEN_DIALOG);
    }

}  
