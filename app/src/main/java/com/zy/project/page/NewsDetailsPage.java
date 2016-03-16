package com.zy.project.page;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.zy.project.R;
import com.zy.project.adapter.NewsRecyclerViewAdapter;
import com.zy.project.bean.NewsData;
import com.zy.project.globle.UrlConstant;
import com.zy.project.view.RefreshLayout;

import java.util.List;

/**
 * 新闻页面详情页
 * Created by xjqxz_000 on 2016/3/5.
 */
public class NewsDetailsPage extends BasePage {

    private RecyclerView mRecyclerView;
    private RefreshLayout mRefreshLayout;
    List<NewsData.ListNews> dataList;
    private String mUrl;
    private String mMoreUrl;
    private NewsRecyclerViewAdapter newsRecyclerViewAdapter;

    public NewsDetailsPage(Context context, String url) {
        super(context);
        mUrl = url;
        initView();
        initEvent();//初始化监听器
        initNetData(url);//初始化网络数据
    }

    @Override
    protected void initView() {
        mRootView = mInflater.inflate(R.layout.page_newslist, null);
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.rv_news_list);
        mRefreshLayout = (RefreshLayout) mRootView.findViewById(R.id.swipe_container);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(llm);//设置为线性布局

    }

    @Override
    protected void initEvent() {
        //设置下拉刷新监听器
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        initNetData(mUrl);
                    }
                }, 2000);

            }
        });
        mRefreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                Log.e("Project", "加载更多", null);
                loadMore(mMoreUrl);
            }
        });
    }

    /**
     * 初始化网络数据
     *
     * @param url
     */
    private void initNetData(String url) {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                NewsData data = praseJsonData(responseInfo.result);
                mMoreUrl = UrlConstant.NET_PATH + data.data.more;//获取更多地址
                dataList = data.data.news;//获取新闻列表
                newsRecyclerViewAdapter = new NewsRecyclerViewAdapter(mContext, dataList);
                mRecyclerView.setAdapter(newsRecyclerViewAdapter);
                mRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(mContext, "获取数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 加载更多
     *
     * @param url
     */
    private void loadMore(String url) {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                NewsData data = praseJsonData(responseInfo.result);
                mMoreUrl = UrlConstant.NET_PATH + data.data.more;//获取更多地址
                dataList = data.data.news;//获取新闻列表
                newsRecyclerViewAdapter.addData(dataList);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(mContext, "获取数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 解析Json数据
     *
     * @param data
     * @return
     */
    private NewsData praseJsonData(String data) {
        Gson mGson = new Gson();
        return mGson.fromJson(data, NewsData.class);
    }
}
