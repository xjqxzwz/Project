package com.zy.project.activity;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zy.project.R;
import com.zy.project.utils.FileUtils;

/**
 * 下载管理页面
 * Created by xjqxz_000 on 2016/3/13.
 */
public class DownloadActivity extends BaseActivity {

    private Toolbar mToolbar;
    private String totalSize;//内存总容量
    private String avaliableSize;//内存可用容量
    private int sizePercentage;//内存比例
    private ProgressBar mProgressBar;
    private TextView mTextView;

    protected void initView() {
        setContentView(R.layout.activity_download);
        sp=getSharedPreferences("config",MODE_PRIVATE);
        initMemorySize();
        mToolbar= (Toolbar) findViewById(R.id.tb_download);
        mProgressBar= (ProgressBar) findViewById(R.id.pb_memorysize);
        mTextView= (TextView) findViewById(R.id.tv_sizepercentage);
        mTextView.setText("主储存:"+totalSize+"/可用:"+avaliableSize);//设置文字
        mProgressBar.setProgress(sizePercentage);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 初始化总容量与可用容量
     */
    private void initMemorySize(){
        long dtotalSize=FileUtils.getMemoryTotalSize();//获得总大小(字节)
        long davaliableSize=FileUtils.getMemoryAvaliableSize();//获得可用大小(字节)
        totalSize=FileUtils.formatSize(FileUtils.getMemoryTotalSize());//获得总大小(字符串)
        avaliableSize=FileUtils.formatSize(FileUtils.getMemoryAvaliableSize());//获得可用大小(字节)
        sizePercentage=getPercentage(dtotalSize, davaliableSize);//计算比例

    }

    /**
     * 获得可用容量与总容量比例
     * @param totalSize 总容量
     * @param avaliableSize 可用容量
     * @return
     */
    private int getPercentage(double totalSize,double avaliableSize){
        return (int)(100-(avaliableSize/totalSize*100));
    }
}
