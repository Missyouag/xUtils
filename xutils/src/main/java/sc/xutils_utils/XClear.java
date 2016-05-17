package sc.xutils_utils;

import org.xutils.common.util.FileUtil;
import org.xutils.common.util.IOUtil;

import sc.NativeFile;


/**
 * 清空xutil本地缓存
 * Created by Administrator on 2016/1/7.
 */
public class XClear {


    public static void ClearAll(){
        IOUtil.deleteFileOrDir(FileUtil.getCacheDir(NativeFile.Http_Cache));
        IOUtil.deleteFileOrDir(FileUtil.getCacheDir(NativeFile.DISK_CACHE_DIR_NAME));


    }

}
