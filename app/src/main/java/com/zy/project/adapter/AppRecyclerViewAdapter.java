package com.zy.project.adapter;

import android.content.Context;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.zy.project.R;
import com.zy.project.bean.AppsData;
import com.zy.project.utils.FileUtils;

import java.io.File;
import java.util.List;

/**
 * Created by xjqxz_000 on 2016/3/12.
 */
public class AppRecyclerViewAdapter extends RecyclerView.Adapter<AppRecyclerViewAdapter.AppViewHolder> {


    private LayoutInflater mInflater;
    private List<AppsData.App> appList;
    private Context mContext;
    public AppRecyclerViewAdapter(Context context,AppsData apps){
        mInflater=LayoutInflater.from(context);
        appList = apps.data;
        mContext=context;
    }
    @Override
    public AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= mInflater.inflate(R.layout.item_application,null);
        AppViewHolder nvh=new AppViewHolder(v);
        return nvh;

    }

    @Override
    public void onBindViewHolder(final AppViewHolder holder, final int position) {
        BitmapUtils bitmapUtils=new BitmapUtils(mContext, FileUtils.getCacheDir(mContext, "icon").getPath());
        bitmapUtils.display(holder.appIcon,appList.get(position).appicon);
        holder.appIcon.setTag(appList.get(position).appicon);
        holder.appName.setText(appList.get(position).appname);
        holder.appSize.setText(appList.get(position).appsize);
        holder.appInfo.setText(appList.get(position).appinfo);
        holder.cv_theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        String url=appList.get(position).appurl;//获得下载地址
        String apkName=url.substring(url.lastIndexOf("/"));//获取下载名称
        final String downloadUrl= FileUtils.getCacheDir(mContext, "download").getPath()+"/"+apkName;//获得存贮地址
        if(FileUtils.checkApkIsDownload(downloadUrl)){
            holder.appDownload.setText("安装");
        }else{
            holder.appDownload.setText("下载");
        }
        holder.appDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.appDownload.getText().equals("下载")){
                    HttpUtils http = new HttpUtils();
                    holder.appDownload.setText("下载中");//设置文字为下载中
                    holder.appDownload.setClickable(false);//设置按钮不可点击
                    http.download(appList.get(position).appurl, downloadUrl, true, true, new RequestCallBack<File>() {
                        @Override
                        public void onSuccess(ResponseInfo<File> responseInfo) {

                            Toast.makeText(mContext, "下载成功", Toast.LENGTH_SHORT).show();//提示
                            holder.appDownload.setText("安装");//设置文本为安装
                            File file=new File(downloadUrl);
                            FileUtils.openFile(mContext, file);//下载成功后自动安装
                            holder.appDownload.setClickable(true);//回复按钮可点击
                        }
                        @Override
                        public void onFailure(HttpException e, String s) {
                            Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    File file=new File(downloadUrl);
                    FileUtils.openFile(mContext, file);//下载成功后自动安装
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return appList.size();
    }


    static class AppViewHolder extends RecyclerView.ViewHolder{

        CardView cv_theme;
        ImageView appIcon;//图标
        TextView appName;//名字
        TextView appSize;//大小
        TextView appInfo;//说明信息
        Button appDownload;//下载按钮

        public AppViewHolder(View itemView) {
            super(itemView);
            cv_theme= (CardView) itemView.findViewById(R.id.cv_app);
            appIcon = (ImageView) itemView.findViewById(R.id.iv_application);
            appName = (TextView) itemView.findViewById(R.id.tv_appname);
            appSize = (TextView) itemView.findViewById(R.id.tv_appsize);
            appInfo = (TextView) itemView.findViewById(R.id.tv_appinfo);
            appDownload = (Button) itemView.findViewById(R.id.btn_appdownload);

        }


    }
}
