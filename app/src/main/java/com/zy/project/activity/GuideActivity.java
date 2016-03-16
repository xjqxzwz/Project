package com.zy.project.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.zy.project.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseActivity {

    private ViewPager mViewPager;
    private int[] guidePic = new int[]{R.drawable.launch_0, R.drawable.launch_1, R.drawable.launch_2};
    private List<ImageView> list_view;
    private Button start;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_guide);
        mViewPager = (ViewPager) findViewById(R.id.vp_guide);
        start= (Button) findViewById(R.id.btn_start);
        start.setVisibility(View.INVISIBLE);
        list_view = new ArrayList<>();
        for (int i = 0; i < guidePic.length; i++) {
            ImageView iv_view = new ImageView(this);
            iv_view.setBackgroundResource(guidePic[i]);
            list_view.add(iv_view);
        }
    }

    @Override
    protected void initData() {
        mViewPager.setAdapter(new GuideViewPagerAdapter());
    }

    @Override
    protected void initEvent() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == guidePic.length - 1) {
                    start.setVisibility(Button.VISIBLE);
                } else {
                    start.setVisibility(Button.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferences("config",MODE_PRIVATE).edit().putBoolean("isGuided",true).commit();
                startActivity(new Intent(GuideActivity.this, MainActivity.class));

                finish();
            }
        });
    }

    class GuideViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return list_view.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list_view.get(position));
            return list_view.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
