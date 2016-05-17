package sc.xutils_utils.callback;

/**
 * Created by Administrator on 2016/1/6.
 */

/**
 * 网络响应
 * 其成功下info数据
 */
public interface HttpReturn extends ASKFlag {
    /**
     * @param e    登录成功与否 1为成功 2为问题详情（参数错误），3 为网络错误 4 为服务器问题 5 取消
     * @param gson 信息
     */
    void flash(int e, String gson);
}
