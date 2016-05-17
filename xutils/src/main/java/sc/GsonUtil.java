package sc;


import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Gson解析
 * <p/>
 * <p/>
 * type=new TypeToken<BaseBean<IndexBean>>(){}.getType();
 *
 * @author chengxuyuan2
 */

public class GsonUtil {
    private static Gson g;

//	public static final Type[] types = new Type[] { new TypeToken<BaseBean<IndexBean>>() {
//	}.getType() };

    public static <T> T JsonToObject(String json, Type type) {
        if (g == null) {
            g = new Gson();
        }
        return g.fromJson(json, type);
    }


    public static <T> String ObjectToJson(T t) {
        if (g == null) {
            g = new Gson();
        }
        return g.toJson(t);
    }

}
