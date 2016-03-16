package com.zy.project.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 图片信息
 * Created by xjqxz_000 on 2016/3/14.
 */
public class PhotoInfo implements Serializable{
    public String more;
    public List<Photo> data;

    public class Photo implements Serializable{
        public String id;
        public String name;
        public String small;
        public String url;
    }

}
