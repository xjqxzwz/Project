package com.zy.project.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.zy.project.R;
import com.zy.project.adapter.PhotoRecyclerViewAdapter;
import com.zy.project.bean.PhotoInfo;
import com.zy.project.globle.UrlConstant;
import com.zy.project.view.RefreshLayout;

import java.util.List;

/**
 * 相册Fragment
 * Created by xjqxz_000 on 2016/3/4.
 */
public class PhotosFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private PhotoInfo photoInfo;
    private List<PhotoInfo.Photo> photoList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView=inflater.inflate(R.layout.fragment_photos,null);
        initView();
        initData();
        return mRootView;
    }
    @Override
    protected void initView() {
        mRecyclerView= (RecyclerView) mRootView.findViewById(R.id.rv_photo_list);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);

    }

    @Override
    public void initEvent() {

    }

    public void initData() {
        initNetData();
    }

    private void initNetData(){
        HttpUtils httpUtils=new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, UrlConstant.PHOTOS_PATH, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result=responseInfo.result;
                if(result!=null){
                    praseJsonData(result);
                    mRecyclerView.setAdapter(new PhotoRecyclerViewAdapter(getActivity(),photoInfo));
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(getActivity(), "网络连接异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  void praseJsonData(String rusult){
        Gson mGson=new Gson();
        photoInfo=mGson.fromJson(rusult, PhotoInfo.class);
        photoList=photoInfo.data;
    }
}
