package com.ruanko.dao;

import java.util.List;

import com.ruanko.model.News;

// �������ݲ����ӿ�
public interface NewsExport {
	// �������ŵķ���
	public boolean save(List<News> newsList);
}
