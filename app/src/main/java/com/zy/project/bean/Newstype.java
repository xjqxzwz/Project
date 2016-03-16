package com.zy.project.bean;

import java.util.ArrayList;

/**
 * Created by xjqxz_000 on 2016/3/6.
 */
public class Newstype {
    public ArrayList<News> data;

    public class News{
        public String id;
        public String title;
        public String type;
        public String url;
    }

}
