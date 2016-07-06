package sc;

import android.os.Environment;
import android.text.TextUtils;

import org.xutils.common.util.FileUtil;
import org.xutils.x;

import java.io.File;

/**
 * 存放本地文件的位置记录
 * <p>
 * 默认位置
 * Created by Missyouag on 2015/9/6.
 */
public class NativeFile {

    /**
     * 公司文件夹名
     */
    private static String companyName = "SC";
    /**
     * 公司文件夹名
     */
    private static String companyNameChinese = "";
    /**
     * 工程名
     */
    private static String projectName = "xUtils";

    public static String getCompanyName() {
        return companyName;
    }
    public static String getCompanyNameChinese() {
        return companyNameChinese;
    }

    public static void setCompanyNameChinese(String companyNameChinese) {
        NativeFile.companyNameChinese = companyNameChinese;
    }
    public static String getProjectName() {
        return projectName;
    }

    /**
     * 初始化文件夹
     * @param companyName
     * @param projectName
     */
    public static void initFolder(String companyName, String projectName) {
        if (!TextUtils.isEmpty(companyName)) {
            NativeFile.companyName = companyName;
        }
        if (!TextUtils.isEmpty(projectName)) {
            NativeFile.projectName = projectName;
        }
    }

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
