package com.zy.project.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.zy.project.io.DiskLruCache;
import com.zy.project.utils.MD5Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 硬盘缓存类
 * Created by xjqxz_000 on 2016/3/9.
 */
public class LruDiskCache {
    private LruDiskCache() {
    }

    private static DiskLruCache mCache;
    private static final int LOCAL_CACHE_SIZE_LIMIT =
            100 * 1024 * 1024;
    public static void openCache(Context context, String name) {

        File file = getCacheDir(context, name);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            mCache = DiskLruCache.open(file, 1, 1, LOCAL_CACHE_SIZE_LIMIT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取图片缓存路径
     *
     * @param context
     * @param cacheDirName
     * @return
     */
    public static File getCacheDir(Context context, String cacheDirName) {
        String cachePath;
        StringBuffer sb = new StringBuffer();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cachePath = sb.append(Environment.getExternalStorageDirectory().getPath())
                    .append(File.separator).append("Project")
                    .append(File.separator)
                    .append("cache").toString();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + cacheDirName);
    }

    /**
     * 写出缓存。
     */
    public static void setCacheToDisk(Bitmap bitmap, String keyCache) {
        if (mCache != null){
            try {
                DiskLruCache.Editor editor = mCache.edit(MD5Utils.md5(keyCache));

                if (editor != null) {
                    OutputStream outputStream = editor.newOutputStream(0);
                    boolean success = bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

                    if (success) {
                        editor.commit();
                    } else {
                        editor.abort();
                    }
                }
            } catch (IOException e) {
                Log.e(null, "setCacheToDisk: 数据加载错误",null );
            }

        }

    }

    /**
     * 读取缓存。
     */
    public static Bitmap getCacheFromDisk(String keyCache) throws IOException {
        if (mCache != null) {
            DiskLruCache.Snapshot snapshot = mCache.get(MD5Utils.md5(keyCache));

            if (snapshot != null) {
                InputStream inputStream = snapshot.getInputStream(0);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                return bitmap;
            }
        }
        return null;
    }
    /**
     * 检查缓存是否存在。
     */
    public static boolean hasCache(String keyCache) {
        try {
            return mCache.get(MD5Utils.md5(keyCache)) != null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
    /**
     * 关闭DiskLruCache。
     */
    public static void closeCache() {
        try {
            mCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
