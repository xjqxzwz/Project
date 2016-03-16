package com.zy.project.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zy.project.fragment.BaseFragment;

import java.util.List;

/**
 * 内容fragment适配器
 * Created by xjqxz_000 on 2016/3/9.
 */
public class ContentFragmentAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mistfragment;
    public ContentFragmentAdapter(FragmentManager fm,List<BaseFragment> listfragment) {
        super(fm);
        mistfragment=listfragment;
    }

    @Override
    public Fragment getItem(int position) {
        return mistfragment.get(position);
    }

    @Override
    public int getCount() {
        return mistfragment.size();
    }
}
