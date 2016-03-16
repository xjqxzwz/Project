package com.zy.project.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.zy.project.cache.LruDiskCache;
import com.zy.project.cache.LruMemoryCache;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 从网络获取json工具类
 * Created by xjqxz_000 on 2016/3/8.
 */
public class MyHttpUtils {
    private URL mUrl;
    private HttpURLConnection mConnection;
    private InputStream mInputStream;
    private StringBuffer mBuffer = null;
    private Context mContext;

    public MyHttpUtils(Context context) {
        mContext = context;
    }

    /**
     * 从网络地址获取数据
     *
     * @param url
     */
    public String getNetDataFromUrl(final String url) {
        try {
            mUrl = new URL(url);

            if (mUrl != null) {
                mConnection = (HttpURLConnection) mUrl.openConnection();
                mConnection.setRequestMethod("GET");
                mConnection.setConnectTimeout(5000);
                if(mConnection.getResponseCode()==200){
                    if (mConnection != null) {
                        mInputStream = mConnection.getInputStream();
                        mBuffer = new StringBuffer();
                        BufferedReader br = new BufferedReader(new InputStreamReader(mInputStream));
                        String buffer;
                        try {
                            while ((buffer = br.readLine()) != null) {
                                mBuffer.append(buffer).append("\n");

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return mBuffer.toString();
                    }
                }else{
                    mRequestCallBack.onFailed();
                }

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (mInputStream != null) {
                    mInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (mConnection != null) {
                mConnection.disconnect();
            }
    }

    return null;
}

class myAsynTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        return getNetDataFromUrl(params[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        mRequestCallBack.onSuccess(s);
    }
}

    public void send(String url, RequestCallBack requestCallBack) {
        mRequestCallBack = requestCallBack;
        myAsynTask task = new myAsynTask();
        task.execute(url);
    }

private RequestCallBack mRequestCallBack;

public interface RequestCallBack {
    void onSuccess(String result);

    void onFailed();
}
}
