package com.zy.project.activity;

import android.content.Intent;
import android.content.SharedPreferences;

import android.view.Window;
import android.view.animation.Animation;

import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.zy.project.R;


/**
 * //欢迎界面
 * Created by xjqxz_000 on 2016/3/4.
 */
public class WelcomeActivity extends BaseActivity{
    private RelativeLayout mLayout;
    private boolean isGuided;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_welcome);
        mLayout= (RelativeLayout) findViewById(R.id.rl_bg);
        Animation loadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_welcome);
        mLayout.startAnimation(loadAnimation);
        loadAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(isGuided){
                    startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                }else{
                    startActivity(new Intent(WelcomeActivity.this,GuideActivity.class));
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void initData() {
        SharedPreferences sp=getSharedPreferences("config",MODE_PRIVATE);
        isGuided=sp.getBoolean("isGuided",false);
    }
}
