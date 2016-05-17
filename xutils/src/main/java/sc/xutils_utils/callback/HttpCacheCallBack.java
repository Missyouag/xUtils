package sc.xutils_utils.callback;

import android.text.TextUtils;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;

/**
 * 网络初步，仅判断是否成功
 * Created by Administrator on 2016/1/6.
 */
public class HttpCacheCallBack implements Callback.CacheCallback<String> {
    public Receiver getReceive() {
        return receive;
    }

    public HttpCacheCallBack setReceive(JsonReceiver receive) {
        this.receive = receive;
        return this;
    }

    private Receiver receive;
    protected HttpReturn HttpReceiver;


    public HttpCacheCallBack(Receiver receive) {
        this.receive = receive;
    }

    protected boolean ReceiverData(int flag, String result) {
        if (null == receive)
            receive = new Receiver(HttpReceiver);
       return receive.ReceiverData(flag, result);
    }


    @Override
    public void onSuccess(String result) {
        ReceiverData(HttpReturn.SUCCESS, result);
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        LogUtil.LogNet(ex.toString());
        ReceiverData(isOnCallback ? HttpReturn.ERR_CANCEL : HttpReturn.ERR_INTERNET, "");
    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }

    @Override
    public boolean onCache(String result) {
        if (TextUtils.isEmpty(result)) return false;
        return ReceiverData(HttpReturn.SUCCESS, result);
    }

}