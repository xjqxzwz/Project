package com.zy.project.bean;

import java.util.ArrayList;

/**
 * 新闻数据
 */
public class NewsData {

	public News data;

	public class News {
		public String more;//加载更多
		public ArrayList<ListNews> news;
		
		
	}

	public class ListNews {
		public String id;
		public String listimage;//图片
		public String pubdate;//时间
		public String title;//标题
		public String type;//类型
		public String url;//地址
		
		
	}
}
