package com.zy.project.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by xjqxz_000 on 2016/3/5.
 */
public abstract class BasePage {
    public View mRootView;//视图
    protected Context mContext;
    public LayoutInflater mInflater;
    public BasePage(Context context){
        mContext=context;
        mInflater=LayoutInflater.from(mContext);
    }
    protected  abstract void initView();

    protected  void initEvent(){

    }

    public  void initData(){

    }
}
