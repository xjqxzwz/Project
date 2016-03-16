package com.zy.project.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.zy.project.R;
import com.zy.project.adapter.SettingListViewAdapter;
import com.zy.project.globle.UrlConstant;
import com.zy.project.utils.FileUtils;
import com.zy.project.utils.UpdataUtils;

import java.io.File;

/**
 * 设置页面
 * Created by xjqxz_000 on 2016/3/10.
 */
public class SettingActivity  extends BaseActivity {

    private Toolbar mToolbar;
    private ListView listView;

    protected void initView() {
        setContentView(R.layout.activity_setting);
        sp=getSharedPreferences("config",MODE_PRIVATE);
        mToolbar= (Toolbar) findViewById(R.id.tb_setting);
        listView= (ListView) findViewById(R.id.lv_setting);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView.setAdapter(new SettingListViewAdapter(this));
    }

    /**
     * 添加listview点击事件
     */
    protected void initEvent(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:

                        File file = FileUtils.getCacheDir(SettingActivity.this, "bitmap");
                        BitmapUtils bitmapUtils = new BitmapUtils(SettingActivity.this, file.getPath());
                        bitmapUtils.clearDiskCache();
                        Toast.makeText(SettingActivity.this, "清空成功", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:

                        break;
                    case 2:
                        break;

                }
            }
        });
    }

    private void createUpdataDialog(){
        AlertDialog alertDialog =new AlertDialog.Builder(SettingActivity.this)
                .setTitle("版本更新")
                .setMessage("简单消息框")
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        alertDialog.show();

    }


}
