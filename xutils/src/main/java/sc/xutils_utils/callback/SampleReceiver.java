package sc.xutils_utils.callback;

import sc.GsonUtil;

/**
 * s示例接受器
 * BaseBean 为示例bean 此处省略。
 * Created by Administrator on 2016/1/6.
 */
public class SampleReceiver extends JsonReceiver {

    public SampleReceiver(HttpReturn Receiver) {
        super(Receiver);
    }

    @Override
    protected void ReceiverJson(int flag, String result) {
        receiver.flash(flag, result);
//        if (flag == HttpReturn.SUCCESS) {
//           BaseBean base = GsonUtil.JsonToObject(result, new TypeToken<BaseBean>() {
//            }.getType());
//            if ("100".equalsIgnoreCase(base.getCode())) {
//                receiver.flash(HttpReturn.SUCCESS, result);
//                return;
//            } else result = base.getMessage().toString();
//            receiver.flash(HttpReturn.ERR_PARAMETER, result);
//        } else
//            receiver.flash(flag, result);
    }
}


