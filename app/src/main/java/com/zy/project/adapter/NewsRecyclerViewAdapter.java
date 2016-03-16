package com.zy.project.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.zy.project.R;
import com.zy.project.activity.WebViewActivity;
import com.zy.project.bean.NewsData;
import com.zy.project.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻页面适配器
 * Created by xjqxz_000 on 2016/3/12.
 */
public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {


    private LayoutInflater mInflater;
    private List<NewsData.ListNews> dataList;
    private Context mContext;
    private SharedPreferences sp;
    private List<String> historys;
    private String sHistory;
    public NewsRecyclerViewAdapter(Context context, List<NewsData.ListNews> data){
        mInflater=LayoutInflater.from(context);
        dataList=data;
        mContext=context;
        historys=new ArrayList<>();
        paresHistory(context);
    }

    /**
     * 获得历史列表
     * @param context
     */
    private void paresHistory(Context context) {
        sp=context.getSharedPreferences("history", Context.MODE_PRIVATE);
        sHistory=sp.getString("key", null);
        if(sHistory!=null){
            String[] temp=sHistory.split("#");
            for (String v:temp) {
                historys.add(v);
            }
        }
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= mInflater.inflate(R.layout.page_newdetails,null);
        NewsViewHolder nvh=new NewsViewHolder(v);
        return nvh;

    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, int position) {
        final  int j=position;
        BitmapUtils bitmapUtils=new BitmapUtils(mContext, FileUtils.getCacheDir(mContext, "bitmap").getPath());
        bitmapUtils.display(holder.news_photo, dataList.get(position).listimage);
        holder.news_photo.setTag(dataList.get(position).listimage);//设置图片
        holder.news_title.setText(dataList.get(position).title);//设置文章标题
        if(historys.contains(dataList.get(position).id))//判断当前文章id是否在历史中
            holder.news_title.setTextColor(Color.DKGRAY);//设置文章标题灰色
        holder.news_time.setText(dataList.get(position).pubdate);//设置时间
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,WebViewActivity.class);
                intent.putExtra("url", dataList.get(j).url);
                mContext.startActivity(intent);
                sHistory=sp.getString("key", null);//获得history
                sp.edit().putString("key",sHistory+dataList.get(j).id+"#").commit();//将sp中数据加上点击数据存入sp中
                holder.news_title.setTextColor(Color.DKGRAY);//设置文章标题灰色
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView news_photo;
        TextView news_title;
        TextView news_time;

        public NewsViewHolder(View itemView) {
            super(itemView);
            cardView= (CardView) itemView.findViewById(R.id.card_view);
            news_photo= (ImageView) itemView.findViewById(R.id.iv_newsPic);
            news_title= (TextView) itemView.findViewById(R.id.tv_newsTitle);
            news_time= (TextView) itemView.findViewById(R.id.tv_newsTime);

        }
    }

    /**
     * 上拉加载更多
     * @param data
     */
    public void addData(List<NewsData.ListNews> data){
        dataList.addAll(data);
        notifyDataSetChanged();
    }
}
