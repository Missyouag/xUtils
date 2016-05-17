package sc;

import android.os.Environment;

import org.xutils.common.util.FileUtil;
import org.xutils.x;

import java.io.File;

/**
 * 存放本地文件的位置记录
 *
 * 默认位置
 * Created by Missyouag on 2015/9/6.
 */
public class NativeFile {

    /**
     * 公司文件夹名
     */
    private static final String companyName="SC";
    /**
     * 工程名
     */
    private static final String projectName="xUtils";

    public static String getSystemDirectory(boolean b) {
        return b ? Environment
                .getExternalStorageDirectory().getAbsolutePath() : x.app().getCacheDir().getAbsolutePath();
    }

    public static String getSystemDirectory() {
        return getSystemDirectory(FileUtil.existsSdcard());
    }



    public static String getDownloadDir() {
        return getDownloadDir(FileUtil.existsSdcard());
    }

    public static String getDownloadDir(boolean f) {
        return getSystemDirectory(f) + File.separator + companyName + File.separator + projectName + File.separator;
    }

    public static final String File_Cache = "cache";
    public static final String Http_Cache = "http_cache";
    public static final String File_BackUp = "backup";
    public static final String TEMP_FILE_SUFFIX = ".tmp";
    public static final String File_Cache_Front = "cache_front";
    public final static String DISK_CACHE_DIR_NAME = "img";
    public final static String dbName = "main.db"; // default db name


    public static String getDownApk() {
        return getDownApk(FileUtil.existsSdcard());
    }

    /**
     * 获得多种情况的目录
     *
     * @param f
     * @return
     */
    public static String getDownApk(boolean f) {
        return getDownloadDir(f) + File.separator;
    }


    public static String getApkFile(String name) {
        return getDownApk() + name + ".apk";
    }

    /**
     * 不判定是否存在
     *
     * @param fileName
     * @return
     */
    public static File getFile(String fileName) {
        return new File(new File(NativeFile.getDownloadDir()), fileName);
    }
}
