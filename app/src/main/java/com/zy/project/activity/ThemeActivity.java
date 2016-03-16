package com.zy.project.activity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zy.project.R;
import com.zy.project.globle.UrlConstant;

/**
 * 主题设置页面
 * Created by xjqxz_000 on 2016/3/10.
 */
public class ThemeActivity extends BaseActivity {

    private Toolbar mToolbar;
    private ListView themeList;

    protected void initView() {
        setContentView(R.layout.activity_theme);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        mToolbar = (Toolbar) findViewById(R.id.tb_theme);
        themeList = (ListView) findViewById(R.id.lv_themeList);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        themeList.setAdapter(new ThemeListAdapter(this));
    }

    public class ThemeListAdapter extends BaseAdapter {
        private String[] themeName = new String[]{"蓝色", "红色", "粉色", "黑色", "黄色", "绿色", "紫色"};//主题名称
        private Integer[] themeColor = new Integer[]{R.color.blue, R.color.red, R.color.fen, R.color.black, R.color.yellow, R.color.green, R.color.zi};//主题颜色
        private LayoutInflater mInflater;
        private Context mContext;

        public ThemeListAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            mContext = context;
        }

        @Override
        public int getCount() {
            return themeName.length;
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
            final  int j=position;
            ViewHolder mViewHolder;
            View view = mInflater.inflate(R.layout.item_theme, null);
            mViewHolder = new ViewHolder(view);
            mViewHolder.themeText.setTextColor(mContext.getResources().getColor(themeColor[position]));
            mViewHolder.themeText.setText(themeName[position]);
            //setButtonBorder(mViewHolder.themeButton);

            mViewHolder.themeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setCurrentTheme(j);
                    System.exit(0);
                    final Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
            return view;
        }

//        private void setButtonBorder(Button button){
//            GradientDrawable p = (GradientDrawable) button.getBackground();
//            switch (getCurrentTheme()){
//
//                case 0:
//                    p.setStroke(1, getResources().getColor(R.color.blue));
//                    break;
//                case 1:
//                    p.setStroke(1, getResources().getColor(R.color.red));
//                    break;
//                case 2:
//                    p.setStroke(1, getResources().getColor(R.color.fen));
//                    break;
//                case 3:
//                    p.setStroke(1, getResources().getColor(R.color.black));
//                    break;
//                case 4:
//                    p.setStroke(1, getResources().getColor(R.color.yellow));
//                    break;
//                case 5:
//                    p.setStroke(1, getResources().getColor(R.color.green));
//                    break;
//                case 6:
//                    p.setStroke(1,getResources().getColor(R.color.zi));
//                    break;
//            }
//        }
        class ViewHolder {
            ImageView themeIcon;
            TextView themeText;
            Button themeButton;

            public ViewHolder(View view) {
                themeIcon = (ImageView) view.findViewById(R.id.iv_colorIcon);
                themeText = (TextView) view.findViewById(R.id.tv_colorText);
                themeButton = (Button) view.findViewById(R.id.btn_use);
            }
        }
    }


}
