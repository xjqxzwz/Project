package com.zy.project.bean;

import java.util.List;

/**
 * APP信息
 * Created by xjqxz_000 on 2016/3/11.
 */
public class AppsData {
    public List<App> data;

    public class App {
        public String appname;
        public String appsize;
        public String appicon;
        public String appurl;
        public String appinfo;

    }
}
