package com.zy.project.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * 基类Fragment
 * Created by xjqxz_000 on 2016/3/4.
 */
public abstract class BaseFragment extends Fragment {
    protected View mRootView;

    protected  abstract void initView();

    public  void initEvent(){

    }
    public  void initData(){

    }
}
