package com.zy.project.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.zy.project.cache.LruDiskCache;
import com.zy.project.cache.LruMemoryCache;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 设置图片工具类
 * Created by xjqxz_000 on 2016/3/9.
 */
public class BitmapUtils {

    private HashMap<String, AsyncTask> taskMap = new HashMap<>();

    public BitmapUtils(Context context) {
        initMemoryCache();
        initDiskCache(context);
    }

    /** 初始化内存缓存器。 */
    private void initMemoryCache() {
        LruMemoryCache.openCache();
    }

    /** 初始化磁盘缓存器。 */
    private void initDiskCache(Context context) {
        int appVersion = 1;
        try {
            appVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        LruDiskCache.openCache(context, "bitmap");
    }

    /** 载入图片。
     *  @param parent 要显示图片的视图的父视图。
     *  @param url 要显示的图片的URL。
     * */
    public void load(View parent, String url) {
        // 尝试从内存缓存载入图片。
        boolean succeeded = loadImageFromMemory(parent, url);
        if (succeeded) return;

        boolean hasCache = LruDiskCache.hasCache(url);
        if (hasCache) {
            // 有磁盘缓存。
            Log.e("Project", "从硬盘获取图片 ",null );
            loadImageFromDisk(parent, url);
        } else {
            // 联网下载。
            Log.e("Project", "从网络获取图片 ", null);
            loadFromInternet(parent, url);
        }
    }

    /** 取消任务。 */
    public void cancel(String tag) {
        AsyncTask removedTask = taskMap.remove(tag);
        if (removedTask != null) {
            removedTask.cancel(false);
        }
    }

    /** 从内存缓存中加载图片。 */
    private boolean loadImageFromMemory(View parent, String url) {
        Bitmap bitmap = LruMemoryCache.getBitmapFromMemoryCache(url);
        if (bitmap != null) {
            setImage(parent, bitmap, url);
            return true;
        }

        return false;
    }

    /** 从磁盘缓存中加载图片。 */
    private void loadImageFromDisk(View parent, String url) {
        LoadImageDiskCacheTask task = new LoadImageDiskCacheTask(parent);
        taskMap.put(url, task);
        task.execute(url);
    }

    /** 从网络上下载图片。 */
    private void loadFromInternet(View parent, String url) {
        DownloadImageTask task = new DownloadImageTask(parent);
        taskMap.put(url, task);
        task.execute(url);
    }

    /** 把图片保存到内存缓存。 */
    private void putImageIntoMemoryCache(String url, Bitmap bitmap) {
        LruMemoryCache.setBitmapToMemoryCache(bitmap,url);
    }

    /** 把图片保存到磁盘缓存。 */
    private void putImageIntoDiskCache(String url, Bitmap bitmap) throws IOException {
        LruDiskCache.setCacheToDisk(bitmap,url);
    }

    /** 重新设置图片。 */
    private void setImage(final View parent, final Bitmap bitmap, final String url) {
        parent.post(new Runnable() {
            @Override
            public void run() {
                ImageView imageView = findImageViewWithTag(parent, url);
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        });
    }

    /** 根据Tag找到指定的ImageView。 */
    private ImageView findImageViewWithTag(View parent, String tag) {
        View view = parent.findViewWithTag(tag);
        if (view != null) {
            return (ImageView) view;
        }

        return null;
    }

    /** 读取图片磁盘缓存的任务。 */
    class LoadImageDiskCacheTask extends AsyncTask<String, Void, Bitmap> {
        private final View parent;
        private String url;

        public LoadImageDiskCacheTask(View parent) {
            this.parent = parent;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;

            url = params[0];
            try {
                bitmap = LruDiskCache.getCacheFromDisk(url);

                if (bitmap != null && !isCancelled()) {
                    // 读取完成后保存到内存缓存。
                    putImageIntoMemoryCache(url, bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            // 显示图片。
            if (bitmap != null) setImage(parent, bitmap, url);
            // 移除任务。
            if (taskMap.containsKey(url)) taskMap.remove(url);
        }
    }

    /** 下载图片的任务。 */
    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private final View parent;
        private String url;

        public DownloadImageTask(View parent) {
            this.parent = parent;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;

            url = params[0];
            try {
                // 下载并解析图片。
                InputStream inputStream =getInputStreamFromUlr(url);
                if(inputStream!=null){
                    bitmap = BitmapFactory.decodeStream(inputStream);

                    if (bitmap != null && !isCancelled()) {
                        // 保存到缓存。
                        putImageIntoMemoryCache(url, bitmap);
                        putImageIntoDiskCache(url, bitmap);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            // 显示图片。
            if (bitmap != null) setImage(parent, bitmap, url);
            // 移除任务。
            if (taskMap.containsKey(url)) taskMap.remove(url);
        }
    }

    /** 使用完毕必须调用。 */
    public void close() {
        for (Map.Entry<String, AsyncTask> entry : taskMap.entrySet()) {
            entry.getValue().cancel(true);
        }

        LruDiskCache.closeCache();
    }

    private InputStream getInputStreamFromUlr(String url){
        URL mUrl= null;
        HttpURLConnection mURLConnection;
        InputStream in=null;
        try {
            mUrl = new URL(url);
            mURLConnection= (HttpURLConnection) mUrl.openConnection();
            in=mURLConnection.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }
}
