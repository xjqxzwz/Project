package com.zy.project.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.zy.project.R;
import com.zy.project.adapter.ContentFragmentAdapter;
import com.zy.project.fragment.BaseFragment;
import com.zy.project.fragment.NewsFragment;
import com.zy.project.fragment.PhotosFragment;
import com.zy.project.view.ContentViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private DrawerLayout drawer;
    private ContentViewPager mPager;
    private List<BaseFragment> mListFragment;//Frament集合
    private long exitTime = 0;
    private ImageView headImage;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    startActivity(new Intent(MainActivity.this, ThemeActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(MainActivity.this, ApplicationActivity.class));
                    break;
                case 3:
                    startActivity(new Intent(MainActivity.this, SettingActivity.class));
                    break;
            }
        }
    };

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);//添加监听器
        mNavigationView.setCheckedItem(R.id.news);//设置默认选择
        View view=mNavigationView.getHeaderView(0);
        headImage= (ImageView) view.findViewById(R.id.ib_head);
        mPager = (ContentViewPager) findViewById(R.id.vp_content);
        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });


    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        initFragment();

    }

    /**
     * 初始化fragment
     */
    private void initFragment() {
        mListFragment = new ArrayList<>();
        mListFragment.add(new NewsFragment());
        mListFragment.add(new PhotosFragment());

        FragmentManager fm = getSupportFragmentManager();
        mPager.setAdapter(new ContentFragmentAdapter(fm, mListFragment));
    }


    /**
     * 抽屉选择
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawer.closeDrawers();//关闭抽屉
        switch (item.getItemId()) {
            case R.id.news:
                mPager.setCurrentItem(0, false);
                break;
            case R.id.photos:
                mPager.setCurrentItem(1, false);
                break;
            case R.id.theme:
                mHandler.sendEmptyMessageDelayed(1, 200);
                break;
            case R.id.application:
                mHandler.sendEmptyMessageDelayed(2, 200);
                break;
            case R.id.setting:
                mHandler.sendEmptyMessageDelayed(3, 200);
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            ExitApp();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //退出应用
    public void ExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }

    }

}
