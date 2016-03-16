package com.zy.project.adapter;

import android.content.Context;;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.BitmapUtils;
import com.zy.project.R;
import com.zy.project.bean.PhotoInfo;
import com.zy.project.page.BasePage;


import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by xjqxz_000 on 2016/3/15.
 */
public class PhotosViewPagerAdapter extends PagerAdapter{

    private Context mContext;
    private List<PhotoInfo.Photo> dataList;
    private List<BasePage> pageList;

    public PhotosViewPagerAdapter(Context context,List<BasePage> page,List<PhotoInfo.Photo> data){
        pageList=page;
        dataList=data;
        mContext=context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view =pageList.get(position).mRootView;
        container.addView(view);
        PhotoView imageView= (PhotoView) view.findViewById(R.id.iv_pic);
        BitmapUtils bitmapUtils =new BitmapUtils(mContext);
        bitmapUtils.display(imageView, dataList.get(position).url);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }




}
