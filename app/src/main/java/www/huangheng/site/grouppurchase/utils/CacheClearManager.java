package www.huangheng.site.grouppurchase.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.math.BigDecimal;

/**
 * 清除缓存
 */

public class CacheClearManager {


    /**
     * 删除文件
     *
     * @param cacheDir 目录
     */
    private static void deleteFileByDirectory(File cacheDir) {
        if (cacheDir != null && cacheDir.exists() && cacheDir.isDirectory()) {
            for (File file : cacheDir.listFiles()) {
                file.delete();
            }
        }
    }

    /**
     * 删除目录
     *
     * @param filePath 路径
     */
    public static void deleteFolderFile(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            if (file.isDirectory()) {
                File[] listFiles = file.listFiles();
                for (File f : listFiles) {
                    deleteFolderFile(f.getAbsolutePath());
                }
            }
            file.delete();
        }
    }


    /**
     * 获取格式化后的缓存大小
     *
     * @param size 缓存大小
     * @return 格式化后的缓存大小
     */
    public static String getFormatSize(double size) {
        double kb = size / 1024;
        if (kb < 1) {
            return size + "Byte";
        }

        double mb = kb / 1024;
        if (mb < 1) {
            BigDecimal bigDecimal = new BigDecimal(Double.toString(kb));
            return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gb = mb / 1024;
        if (gb < 1) {
            BigDecimal bigDecimal = new BigDecimal(Double.toString(mb));
            return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }
        BigDecimal result = new BigDecimal(gb);
        return result.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
    }


    /**
     * 获取缓存大小
     *
     * @param file 文件
     * @return 缓存大小
     */
    public static String getCacheSize(File file) {
        return getFormatSize(getFoldSize(file));
    }


    /**
     * 获取文件大小
     *
     * @param Dir 目录
     * @return 文件大小
     */
    public static long getFoldSize(File Dir) {
        long size = 0;
        try {
            File[] listFiles = Dir.listFiles();
            for (File file : listFiles) {
                if (file.isDirectory()) {
                    size += getFoldSize(file);
                } else {
                    size = size + file.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }


    /**
     * 清除内部缓存
     *
     * @param context 上下文
     */
    public static void clearInternalCache(Context context) {
        deleteFileByDirectory(context.getCacheDir());
    }


    /**
     * 清除所有数据库
     *
     * @param context 上下文
     */
    public static void clearDatabases(Context context) {
        deleteFileByDirectory(new File("/data/data/" + context.getPackageName() + "/databases"));
    }


    /**
     * 删除指定名称的数据库
     *
     * @param context 上下文
     * @param name    数据库名
     */
    public static void clearDatabaseByName(Context context, String name) {
        context.deleteDatabase(name);
    }


    /**
     * 清除SharePreference缓存
     *
     * @param context 上下文
     */
    public static void clearSharePreference(Context context) {
        deleteFileByDirectory(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * 清除/data/data/packagename/files下的内容
     *
     * @param context 上下文
     */
    public static void clearFiles(Context context) {
        deleteFileByDirectory(context.getFilesDir());
    }


    /**
     * 清除外部缓存
     *
     * @param context 上下文
     */
    public static void clearExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteFileByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * 清除自定义缓存
     *
     * @param filePath 文件路径
     */
    public static void clearCustomCache(String filePath) {
        deleteFileByDirectory(new File(filePath));
    }


    /**
     * 清除本应用中的缓存
     *
     * @param context  上下文
     * @param filePath 文件路径
     */
    public static void clearApplicationData(Context context, String... filePath) {
        clearInternalCache(context);
        clearExternalCache(context);
        clearSharePreference(context);
        clearFiles(context);
        if (filePath == null) {
            return;
        } else {
            for (String path : filePath) {
                clearCustomCache(path);
            }
        }

    }


}
