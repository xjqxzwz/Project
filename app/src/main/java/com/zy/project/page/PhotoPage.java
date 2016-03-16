package com.zy.project.page;

import android.content.Context;
import android.view.LayoutInflater;

import com.zy.project.R;

/**
 * 图片显示页面
 * Created by xjqxz_000 on 2016/3/15.
 */
public class PhotoPage extends BasePage{
    private LayoutInflater layoutInflater;
    public PhotoPage(Context context) {
        super(context);
        layoutInflater=LayoutInflater.from(context);
        initView();
    }

    @Override
    protected void initView() {
        mRootView=layoutInflater.inflate(R.layout.page_photos,null);
    }
}
