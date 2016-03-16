package com.zy.project.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.zy.project.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 基础Activity
 * Created by xjqxz_000 on 2016/3/4.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp=getSharedPreferences("config",MODE_PRIVATE);
        setActivityTheme(getCurrentTheme());
        initView();
        initData();
        initEvent();
    }

    /**
     * 获得当前主题
     * @return
     */
    public  int getCurrentTheme(){
        return sp.getInt("theme",0);
    }

    /**
     * 保存主题
     * @param themeId
     */
    public   void setCurrentTheme(int themeId){
        sp.edit().putInt("theme",themeId).commit();
    }

    /**
     * 根据主题id设置主题
     * @param themeId
     */
    public void setActivityTheme(int themeId){
        switch (themeId){
            case 0:
                setTheme(R.style.BlueTheme);
                break;
            case 1:
                setTheme(R.style.RedTheme);
                break;
            case 2:
                setTheme(R.style.FenTheme);
                break;
            case 3:
                setTheme(R.style.BlackTheme);
                break;
            case 4:
                setTheme(R.style.YellowTheme);
                break;
            case 5:
                setTheme(R.style.GreenTheme);
                break;
            case 6:
                setTheme(R.style.ZiTheme);
                break;
        }
    }


    /**
     * 初始化界面
     */
    protected abstract void initView();
    /**
     * 初始化数据
     */
    protected  void initData(){

    }
    /**
     * 初始化事件
     */
    protected void initEvent(){

    }


}
