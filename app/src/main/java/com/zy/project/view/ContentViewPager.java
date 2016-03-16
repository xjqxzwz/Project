package com.zy.project.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 新闻图片适配器
 * Created by xjqxz_000 on 2016/3/4.
 */
public class ContentViewPager extends ViewPager{
    private boolean isCanScroll = false;//是否可滑动标记
    public ContentViewPager(Context context) {
        super(context);
    }

    public ContentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanScroll == false) {
            return false;
        } else {
            return super.onTouchEvent(ev);
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isCanScroll == false) {
            return false;
        } else {
            return super.onInterceptTouchEvent(ev);
        }

    }
}
