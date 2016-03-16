package com.zy.project.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Created by xjqxz_000 on 2016/3/11.
 */
public class FileUtils {

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
     * 自动安装apk
     * @param context
     * @param file
     */
    public static  void openFile(Context context,File file){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);


    }

    /**
     * 检查下载文件是否存在
     * @param address 地址
     * @return
     */
    public static boolean checkApkIsDownload(String address){
        File file=new File(address);
        return file.exists();
    }

    /**
     * 获得手机容量总大小
     * @return
     */
    public static  long getMemoryTotalSize(){
        File file=Environment.getDataDirectory();
        StatFs statFs=new StatFs(file.getPath());
        long size = statFs.getBlockSize();
        long num=statFs.getBlockCount();
        return num*size;
    }

    /**
     * 获得手机剩余容量大小
     * @return
     */
    public static  long getMemoryAvaliableSize(){
        File file=Environment.getDataDirectory();
        StatFs statFs=new StatFs(file.getPath());
        long size = statFs.getBlockSize();
        long num=statFs.getFreeBlocks();
        return num*size;
    }

    /**
     * 格式化手机容量
     * @param size 总大小(字节)
     * @return
     */
    public static  String formatSize(long size){
        DecimalFormat df=new DecimalFormat("#0.#");
        String memorySize="";
        if(size<1024&&size>0){
            memorySize=df.format(size)+"B";
        }else if(size>1024&&size<1024*1024){
            memorySize=df.format(size/1024d)+"KB";
        }else if(size>1024*1024&&size<1024*1024*1024){
            memorySize=df.format(size/(1024d*1024d))+"MB";
        }else {
            memorySize=df.format(size/(1024d*1024d*1024d))+"GB";
        }
        return memorySize;
    }
}
