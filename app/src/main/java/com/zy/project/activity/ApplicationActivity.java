package com.zy.project.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.zy.project.R;
import com.zy.project.adapter.AppRecyclerViewAdapter;
import com.zy.project.bean.AppsData;
import com.zy.project.globle.UrlConstant;
import com.zy.project.utils.MyHttpUtils;

/**
 *
 * 应用推荐页面
 * Created by xjqxz_000 on 2016/3/10.
 */
public class ApplicationActivity extends BaseActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;

    protected void initView() {
        setContentView(R.layout.activity_application);
        sp=getSharedPreferences("config",MODE_PRIVATE);
        mToolbar= (Toolbar) findViewById(R.id.tb_application);//获得toolbar
        mRecyclerView= (RecyclerView) findViewById(R.id.rv_theme);//获得RecyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setSupportActionBar(mToolbar);
        //设置返回键
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置标题
        getSupportActionBar().setTitle(R.string.application);
    }
    public void initData(){
        initNetData();
    }

    public void initNetData(){
        MyHttpUtils httpUtils=new MyHttpUtils(this);
        httpUtils.send(UrlConstant.APPS_PATH, new MyHttpUtils.RequestCallBack() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    AppsData data = praseJsonData(result);
                    mRecyclerView.setAdapter(new AppRecyclerViewAdapter(ApplicationActivity.this, data));
                }
            }

            @Override
            public void onFailed() {

            }
        });
    }

    public AppsData praseJsonData(String data){
        Gson mgson=new Gson();
        return mgson.fromJson(data, AppsData.class);
    }

    /**
     * 加载菜单
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_download_toolmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_download:
                startActivity(new Intent(ApplicationActivity.this,DownloadActivity.class));
                break;
        }
        return false;
    }


}
