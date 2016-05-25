package com.sc.xutils_utils.callback;

/**
 * Created by Administrator on 2016/1/14.
 */
public class Receiver {
    protected HttpReturn receiver;

    public Receiver(HttpReturn Receiver) {
        this.receiver = Receiver;
    }

    /**
     * @param flag
     * @param result
     * @return true, 处理成功；
     * false,数据不对或未处理；
     */
    protected boolean ReceiverData(int flag, String result) {
        if (receiver != null) {
            receiver.flash(flag, result);
        }
        return flag == ASKFlag.SUCCESS;
    }
}
