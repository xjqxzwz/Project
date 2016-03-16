package com.zy.project.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.google.gson.Gson;
import com.zy.project.bean.UpdataInfo;
import com.zy.project.globle.UrlConstant;

/**
 * //获得包信息
 * Created by xjqxz_000 on 2016/3/13.
 */
public class UpdataUtils {
    /**
     * 获取app版本
     * @param context
     * @return
     */
    public static String getPackageInf(Context context){
        PackageManager pm=context.getPackageManager();
        PackageInfo info = null;
        try {
            info = pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info.versionName;
    }

    /**
     * 获得网络版本
     * @param context
     */
    public static void getNetVersion(Context context){
        MyHttpUtils httpUtils=new MyHttpUtils(context);

        httpUtils.send(UrlConstant.UPDATA_PATH, new MyHttpUtils.RequestCallBack() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                UpdataInfo updataInfo=gson.fromJson(result, UpdataInfo.class);
            }
            @Override
            public void onFailed() {

            }
        });
    }
    /**
     * 检查是否需要更新
     * @return
     */
    public static boolean checkUpdata(UpdataInfo updataInfo) {
        //double currentVersion = getPackageInf();
        //double netVersion;
        return true;
    }
}
