package com.zy.project.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * 内存缓存类
 * Created by xjqxz_000 on 2016/3/9.
 */
public class LruMemoryCache {
    private static final int MEMORY_CACHE_SIZE_LIMIT =
            (int) (Runtime.getRuntime().maxMemory() / 3);
    private LruMemoryCache() {
    }

    private static LruCache<String, Bitmap> mCache;

    public static void openCache() {
        mCache = new LruCache<String, Bitmap>((int) MEMORY_CACHE_SIZE_LIMIT) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    /**
     * 从内存缓存中获的图片
     *
     * @param url
     * @return
     */
    public static Bitmap getBitmapFromMemoryCache(String url) {
        return mCache.get(url);
    }

    /**
     * 向内存缓存添加图片
     *
     * @param bitmap
     * @param url
     */
    public static void setBitmapToMemoryCache(Bitmap bitmap, String url) {
        if (getBitmapFromMemoryCache(url) == null) {
            mCache.put(url, bitmap);
        }
    }
}
