package com.zy.project.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import com.zy.project.R;
import com.zy.project.adapter.PhotosViewPagerAdapter;
import com.zy.project.bean.PhotoInfo;
import com.zy.project.page.BasePage;
import com.zy.project.page.PhotoPage;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片查看器
 * Created by xjqxz_000 on 2016/3/15.
 */
public class PhotoActivity extends BaseActivity{


    private PhotoInfo photoInfo;

    private ViewPager mViewPager;

    private List<BasePage> pageList;

    private  PhotosViewPagerAdapter photosViewPagerAdapter;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_photo);
        pageList=new ArrayList<>();
        Intent intent = getIntent();
        int position = intent.getIntExtra("position",0);//获得图片地址
        photoInfo= (PhotoInfo) intent.getSerializableExtra("data");
        for(int i=0;i<photoInfo.data.size();i++){
            pageList.add(new PhotoPage(this));
        }
        mViewPager= (ViewPager) findViewById(R.id.vp_photos);
        photosViewPagerAdapter=new PhotosViewPagerAdapter(this,pageList,photoInfo.data);
        mViewPager.setAdapter(photosViewPagerAdapter);

        mViewPager.setCurrentItem(position);
    }
}
