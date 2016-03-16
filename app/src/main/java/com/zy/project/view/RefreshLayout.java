package com.zy.project.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 带上拉加载更多的SwipeRefreshLayout
 * Created by xjqxz_000 on 2016/3/13.
 */
public class RefreshLayout extends SwipeRefreshLayout {

    /**
     * 滑动到最下面时的上拉操作
     */

    private int mTouchSlop;


    /**
     * RecyclerView实例
     */
    private RecyclerView mRecyclerView;

    /**
     * 上拉监听器, 到了最底部的上拉加载操作
     */
    private OnLoadListener mOnLoadListener;


    /**
     * 按下时坐标
     */
    private int downY;
    /**
     * 抬起时坐标
     */
    private int moveY;

    /**
     * 是否在加载中
     */
    private boolean isLoading = false;

    public RefreshLayout(Context context) {
        super(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //如果mRecyclerView为空则在布局中找到
        if (mRecyclerView == null) {
            initView();
        }
    }

    /**
     * 获取RecyclerView对象
     */
    private void initView() {
        int childs = getChildCount();
        if (childs > 0) {
            View child = getChildAt(0);
            if (child instanceof RecyclerView) {
                mRecyclerView = (RecyclerView) child;
                mRecyclerView.addOnScrollListener(new myOnScrollListener());
            }
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 按下
                downY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 移动
                moveY = (int) ev.getRawY();
                break;

            case MotionEvent.ACTION_UP:
                // 抬起
                if (canLoad()) {
                    loadData();
                }
                break;
        }


        return super.dispatchTouchEvent(ev);
    }

    private void loadData() {

        if (mOnLoadListener != null) {
            mOnLoadListener.onLoad();
        }
    }

    /**
     * 判断是否可以加载更多
     *
     * @return
     */
    private boolean canLoad() {
        return isBottom() && !isLoading && isPullUp();
    }

    /**
     * 判断是否到了最底部
     */
    private boolean isBottom() {

        if (mRecyclerView != null && mRecyclerView.getAdapter() != null) {
            LinearLayoutManager lm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            Log.e("Project", "总数量: "+lm.getItemCount(), null);
            return (lm.findLastVisibleItemPosition() == lm.getItemCount() - 1);
        }
        return false;
    }

    /**
     * 是否是上拉操作
     *
     * @return
     */
    private boolean isPullUp() {
        return (downY - moveY) >= mTouchSlop;
    }


    class myOnScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            // 滚动时到了最底部也可以加载更多

            if (canLoad()) {
                isLoading = true;
                loadData();
            }

        }
    }

    public void setOnLoadListener(OnLoadListener loadListener) {
        mOnLoadListener = loadListener;
    }

    /**
     * 加载更多的监听器
     *
     * @author mrsimple
     */
    public interface OnLoadListener {
        void onLoad();
    }

    /**
     * 自动刷新
     */
    public void autoRefresh() {
        try {
            Field mCircleView = SwipeRefreshLayout.class.getDeclaredField("mCircleView");
            mCircleView.setAccessible(true);
            View progress = (View) mCircleView.get(this);
            progress.setVisibility(VISIBLE);

            Method setRefreshing = SwipeRefreshLayout.class.getDeclaredMethod("setRefreshing", boolean.class, boolean.class);
            setRefreshing.setAccessible(true);
            setRefreshing.invoke(this, true, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
