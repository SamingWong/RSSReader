package com.ruanko.dao;

import java.util.List;

import com.ruanko.model.News;

// 新闻数据操作接口
public interface NewsExport {
	// 保存新闻的方法
	public boolean save(List<News> newsList);
}
