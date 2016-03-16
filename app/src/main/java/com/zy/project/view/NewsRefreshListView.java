package com.zy.project.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.zy.project.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 下拉列表
 * Created by xjqxz_000 on 2016/3/2.
 */
public class NewsRefreshListView extends ListView implements AbsListView.OnScrollListener {
    private LayoutInflater mLayoutInflater;

    private Animation animation;
    private Animation reverseAnimation;

    private View mHeader, mFooter;//头布局，尾布局
    private int downY = 0;//按下时的y轴坐标
    private int mHeaderHeight;//头布局高度
    private int mFooterHeight;//尾布局高度
    private TextView mRefreshText;//提示
    private TextView mTime;//刷新时间
    private ImageView progress;//进度条
    private ImageView arrow;//箭头
    private boolean isLoadingMore = false;// 当前是否正在处于加载更多
    private final int PULL_REFRESH = 0;//下拉刷新
    private final int RELEASE_REFRESH = 1;//释放刷新
    private final int REFRESHING = 2;//正在刷新
    private Context mContext;
    private int mCurrentStatus = PULL_REFRESH;
    private ImageView footProgress;

    public NewsRefreshListView(Context context) {
        super(context);
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        init();
    }

    public NewsRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        init();
    }

    public NewsRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLayoutInflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        setOnScrollListener(this);
        initAnimation();
        initView();

    }

    //初始化动画
    private void initAnimation() {
        animation = AnimationUtils.loadAnimation(mContext, R.anim.arrow_downtoup);

        reverseAnimation = AnimationUtils.loadAnimation(mContext, R.anim.arrow_uptodown);

    }

    private void initView() {
        this.setVerticalScrollBarEnabled(false);
        mFooter = mLayoutInflater.inflate(R.layout.item_footer, null);
        mHeader = mLayoutInflater.inflate(R.layout.item_header, null);
        //初始化头控件
        mRefreshText = (TextView) mHeader.findViewById(R.id.tv_headText);
        mTime = (TextView) mHeader.findViewById(R.id.tv_headTime);
        arrow = (ImageView) mHeader.findViewById(R.id.iv_arrow);
        progress = (ImageView) mHeader.findViewById(R.id.iv_progress);

        AnimationDrawable animationDrawable = (AnimationDrawable) progress.getBackground();
        animationDrawable.start();//播放动画
        progress.setVisibility(INVISIBLE);//设置隐藏
        //初始化尾控件
        footProgress = (ImageView) mFooter.findViewById(R.id.iv_footer_progress);
        AnimationDrawable footAnimation = (AnimationDrawable) footProgress.getBackground();
        footAnimation.start();//播放动画

        //获得头布局高度并隐藏
        measureView(mHeader);
        mHeaderHeight = mHeader.getMeasuredHeight();
        mHeader.setPadding(0, -mHeaderHeight, 0, 0);
        //获得尾布局高度并隐藏
        measureView(mFooter);
        mFooterHeight = mHeader.getMeasuredHeight();
        mFooter.setPadding(0, -mFooterHeight, 0, 0);

        this.addFooterView(mFooter);
        this.addHeaderView(mHeader);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();//获得点击Y轴坐标
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) (ev.getY() - downY);//获得移动距离
                int currentY = (int) (-mHeaderHeight + (moveY) * 0.3);//计算外边距
                if (currentY > -mHeaderHeight && getFirstVisiblePosition() == 0) {
                    mHeader.setPadding(0, currentY, 0, 0);
                    if (currentY >= 0 && mCurrentStatus == PULL_REFRESH) {
                        mCurrentStatus = RELEASE_REFRESH;
                        setReFreshHeadView();
                    } else if (currentY < 0 && mCurrentStatus == RELEASE_REFRESH) {
                        mCurrentStatus = PULL_REFRESH;
                        setReFreshHeadView();
                    }

                }

                break;
            case MotionEvent.ACTION_UP:

                if (mCurrentStatus == PULL_REFRESH) {
                    mHeader.setPadding(0, -mHeaderHeight, 0, 0);
                } else if (mCurrentStatus == RELEASE_REFRESH) {
                    mCurrentStatus = REFRESHING;
                    mHeader.setPadding(0, 0, 0, 0);
                } else if (mCurrentStatus == REFRESHING) {
                    mHeader.setPadding(0, 0, 0, 0);
                }
                setReFreshHeadView();
                //只有在坚挺地不为空并且状态为正在刷新和listview显示第一个条目时调用刷新
                if (mRefreshLisenter != null &&mCurrentStatus==REFRESHING&& getFirstVisiblePosition() == 0) {
                    mRefreshLisenter.onRefresh();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void setReFreshHeadView() {
        switch (mCurrentStatus) {
            case PULL_REFRESH:
                mRefreshText.setText("下拉刷新");
                arrow.startAnimation(reverseAnimation);
                break;
            case RELEASE_REFRESH:
                mRefreshText.setText("释放刷新");
                arrow.startAnimation(animation);
                break;
            case REFRESHING:
                mRefreshText.setText("正在刷新...");
                arrow.clearAnimation();
                arrow.setVisibility(INVISIBLE);
                progress.setVisibility(VISIBLE);
                break;
        }

    }

    //恢复头布局状态
    public void InitStatus() {
        if (isLoadingMore) {
            isLoadingMore = false;
            mFooter.setPadding(0, -mFooterHeight, 0, 0);
        } else {
            mRefreshText.setText("下拉刷新");
            mHeader.setPadding(0, -mHeaderHeight, 0, 0);
            mTime.setText("刷新时间:" + getCurrentTime());
            progress.setVisibility(INVISIBLE);
            arrow.setVisibility(VISIBLE);
            mCurrentStatus = PULL_REFRESH;
        }

    }

    //获得当前时间
    private String getCurrentTime() {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm");
        return mSimpleDateFormat.format(new Date());
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE && getLastVisiblePosition() == (getCount() - 1) && !isLoadingMore) {
            isLoadingMore = true;
            mFooter.setPadding(0, 0, 0, 0);


            if (mRefreshLisenter != null) {
                mRefreshLisenter.onLoadMore();
            }
            setSelection(getCount());// 让listview最后一条显示出来
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    //获得headView的width以及height
    private void measureView(View child) {
        ViewGroup.LayoutParams params = child.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0,
                params.width);
        int lpHeight = params.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    public void setOnRefreshLisenter(RefreshLisenter refreshlisenter) {
        this.mRefreshLisenter = refreshlisenter;
    }

    private RefreshLisenter mRefreshLisenter;

    //外部接口
    public interface RefreshLisenter {
        void onRefresh();

        void onLoadMore();
    }
}
