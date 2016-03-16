package com.zy.project.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.zy.project.R;
import com.zy.project.activity.PhotoActivity;
import com.zy.project.activity.WebViewActivity;
import com.zy.project.bean.NewsData;
import com.zy.project.bean.PhotoInfo;
import com.zy.project.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻页面适配器
 * Created by xjqxz_000 on 2016/3/12.
 */
public class PhotoRecyclerViewAdapter extends RecyclerView.Adapter<PhotoRecyclerViewAdapter.NewsViewHolder> {


    private LayoutInflater mInflater;
    private Context mContext;
    private PhotoInfo dataList;
    public PhotoRecyclerViewAdapter(Context context,PhotoInfo data){
        mInflater=LayoutInflater.from(context);
        mContext=context;
        dataList=data;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= mInflater.inflate(R.layout.item_photos,null);
        NewsViewHolder nvh=new NewsViewHolder(v);
        return nvh;

    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, final int position) {
        BitmapUtils bitmapUtils=new BitmapUtils(mContext, FileUtils.getCacheDir(mContext, "photos").getPath());
        bitmapUtils.display(holder.photo_pic, dataList.data.get(position).small);
        holder.photo_name.setText(dataList.data.get(position).name);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PhotoActivity.class);
                String url=dataList.data.get(position).url;
                int position=Integer.parseInt(url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".")));
                Log.e("Project", "onClick: "+position,null );
                intent.putExtra("position", position-1);
                intent.putExtra("data",dataList);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.data.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView photo_pic;
        TextView photo_name;

        public NewsViewHolder(View itemView) {
            super(itemView);
            cardView= (CardView) itemView.findViewById(R.id.cv_photos);
            photo_pic= (ImageView) itemView.findViewById(R.id.iv_photo);
            photo_name= (TextView) itemView.findViewById(R.id.tv_photoname);


        }
    }

}
