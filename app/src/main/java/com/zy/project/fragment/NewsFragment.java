package com.zy.project.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zy.project.R;
import com.zy.project.adapter.NewsDetailsAdapter;
import com.zy.project.bean.Newstype;
import com.zy.project.globle.UrlConstant;
import com.zy.project.page.NewsDetailsPage;
import com.zy.project.utils.MyHttpUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * 新闻Fragment
 * Created by xjqxz_000 on 2016/3/4.
 */
public class NewsFragment extends BaseFragment{

    private ViewPager newDeails;
    private TabLayout tabLayout;
    private List<NewsDetailsPage> newsDetailsAdapterList;
    private Newstype mType;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView=inflater.inflate(R.layout.fragment_news,null);
        initView();
        initData();
        return mRootView;
    }

    @Override
    protected void initView() {
        newDeails= (ViewPager) mRootView.findViewById(R.id.vp_newsDetails);
        tabLayout= (TabLayout) mRootView.findViewById(R.id.tl_tab);
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        MyHttpUtils http=new MyHttpUtils(getActivity());
        http.send(UrlConstant.GATEGORY_PATH, new MyHttpUtils.RequestCallBack() {
            @Override
            public void onSuccess(String result) {
                mType = praseJsonData(result);
                if (mType != null) {
                    createDetailsPage(mType.data.size());
                    NewsDetailsAdapter mAdapter=new NewsDetailsAdapter(newsDetailsAdapterList, mType);
                    newDeails.setAdapter(mAdapter);
                    tabLayout.setupWithViewPager(newDeails);//
                }

            }

            @Override
            public void onFailed() {
                Toast.makeText(getActivity(), "网络连接异常", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 解析json数据
     * @param data
     * @return
     */
    private Newstype praseJsonData(String data){
        Gson mGson=new Gson();
        Newstype mNewsType=mGson.fromJson(data, Newstype.class);
        return mNewsType;
    }

    private void createDetailsPage(int size){
        newsDetailsAdapterList=new ArrayList<>();
        Log.e("Project", "size: "+size,null );
        for(int i=0;i<size;i++){
            String url=mType.data.get(i).url;
            Log.e("Project", "size: "+url,null );
            newsDetailsAdapterList.add(new NewsDetailsPage(getActivity(),UrlConstant.NET_PATH+url));
        }
    }


}
