package com.zy.project.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zy.project.R;

/**
 * setting页面adapter
 * Created by xjqxz_000 on 2016/3/13.
 */
public class SettingListViewAdapter extends BaseAdapter{
    private  Context mContext;
    private String[] items=new String[]{"清空图片缓存","检查更新","关于"};

    public SettingListViewAdapter(Context context){
        mContext=context;
    }
    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView==null){
            view=View.inflate(mContext,R.layout.item_setting,null);
            TextView textView= (TextView) view.findViewById(R.id.tv_settingText);
            textView.setText(items[position]);
        }else{
            view=convertView;
        }
        return view;
    }
}
