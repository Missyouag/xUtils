package sc.xutils_utils.callback;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;

/**
 * 网络初步，仅判断是否成功
 * Created by Administrator on 2016/1/6.
 */
public class HttpCallBack implements Callback.CommonCallback<String> {
    public Receiver getReceive() {
        return receive;
    }

    public HttpCallBack setReceive(JsonReceiver receive) {
        this.receive = receive;
        return this;
    }

    private Receiver receive;
    protected HttpReturn Receiver;

    public HttpCallBack(HttpReturn Receiver) {
        this.Receiver = Receiver;
    }
    public HttpCallBack(Receiver receive) {
        this.receive = receive;
    }

    protected void ReceiverData(int flag, String result) {
        if (null == receive)
            receive = new Receiver(Receiver);
        receive.ReceiverData(flag, result);
    }

    @Override
    public void onSuccess(String result) {
        ReceiverData(HttpReturn.SUCCESS, result);
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        LogUtil.e(ex.toString(),ex);
        ReceiverData(isOnCallback ? HttpReturn.ERR_CANCEL : HttpReturn.ERR_INTERNET, "");
    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }
}