package sc.xutils_utils.callback;

import org.xutils.common.util.LogUtil;

/**
 * Created by Administrator on 2016/1/14.
 */
public class JsonReceiver extends Receiver {

    public JsonReceiver(HttpReturn Receiver) {
        super(Receiver);
    }

    /**
     * @hide
     */
    protected final boolean ReceiverData(int flag, String result) {
        LogUtil.LogNet(flag, result);
        if (receiver != null && result != null) {
            result=result.replace("false","null");
            result = result.replace("[null]", "null");
            while (true) {
                try {
//                        LogUtil.LogNet("长度："+result.length());
                    //不是以"｛"开始错误屏蔽
                    if (!result.startsWith("{")) {
                        int i = result.indexOf("{");
                        if (i > 0) result = result.substring(i);
                    }
                    ReceiverJson(flag, result);
                    return flag == ASKFlag.SUCCESS;
                } catch (Exception e) {
                    LogUtil.e(e.toString(),e);
                    int i = result.indexOf('{', 2);
                    if (i < 2) {
                        break;
                    } else {
                        result = result.substring(i);
                    }
                }
            }
            LogUtil.LogNet("不能解析："+ result);
            ReceiverJson(HttpReturn.ERR_SERVICE, result);
        }
        return false;
    }

    /**
     * 处理json
     *
     * @param flag
     * @param result
     */
    protected void ReceiverJson(int flag, String result) {
        super.ReceiverData(flag, result);
    }

}
