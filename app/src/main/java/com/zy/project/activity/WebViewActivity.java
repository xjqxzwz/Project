
package com.zy.project.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zy.project.R;

/**
 * 网页浏览Activity
 * Created by xjqxz_000 on 2016/3/10.
 */
public class WebViewActivity extends BaseActivity {
    private WebView mWebView;
    private String mUrl;
    private WebSettings ws;
    private int textSize;//网页字体
    private Toolbar mToolbar;

    protected void initView() {
        setContentView(R.layout.avtivity_webview);
        sp=getSharedPreferences("config",MODE_PRIVATE);
        mWebView = (WebView) findViewById(R.id.wb_webview);
        mToolbar= (Toolbar) findViewById(R.id.tb_webview);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ws=mWebView.getSettings();
        ws.setJavaScriptEnabled(true);
        int size =sp.getInt("textSize", 2);
        setTextSize(size);
        mUrl = getIntent().getStringExtra("url");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
        mWebView.loadUrl(mUrl);
    }

    /**
     * 加载菜单
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_webview_toolmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_textsize:
                createAlertDialog();
                break;
        }
        return false;
    }

    /**
     * 显示字体选择对话框
     */
    private void createAlertDialog(){
        textSize=sp.getInt("textSize", 2);
        AlertDialog.Builder buidler=new AlertDialog.Builder(this);
        buidler.setTitle("字体设置");
        String[] str=new String[]{"超大号字体","大号字体","正常字体","小号字体","超小号字体"};
        buidler.setSingleChoiceItems(str,textSize, new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                textSize=which;
            }
        });
        buidler.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                setTextSize(textSize);
                sp.edit().putInt("textSize", textSize).commit();
            }
        });
        buidler.setNegativeButton("取消", null);
        buidler.show();
    }

    /**
     * 设置网页字体
     * @param size
     */
    private void setTextSize(int size){
        switch (size) {
            case 0:
                ws.setTextSize(WebSettings.TextSize.LARGER);
                break;
            case 1:
                ws.setTextSize(WebSettings.TextSize.LARGEST);
                break;
            case 2:
                ws.setTextSize(WebSettings.TextSize.NORMAL);
                break;
            case 3:
                ws.setTextSize(WebSettings.TextSize.SMALLER);
                break;
            case 4:
                ws.setTextSize(WebSettings.TextSize.SMALLEST);
                break;
        }
    }

}
