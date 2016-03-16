package com.zy.project.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zy.project.bean.NewsData;
import com.zy.project.bean.Newstype;
import com.zy.project.page.NewsDetailsPage;

import java.util.List;

/**
 * Created by xjqxz_000 on 2016/3/9.
 */
public class NewsDetailsAdapter extends PagerAdapter{

    private List<NewsDetailsPage> pageList;
    private Newstype mType;
    public NewsDetailsAdapter(List<NewsDetailsPage> pagelist, Newstype type){
        pageList=pagelist;
        mType=type;
    }
    @Override
    public int getCount() {
        return pageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(pageList.get(position).mRootView);
        return pageList.get(position).mRootView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mType.data.get(position).title;
    }
}
