package com.ruanko.dao.impl;

import java.io.*;
import java.util.*;

import com.ruanko.dao.NewsExport;
import com.ruanko.model.News;
import com.ruanko.service.RSSService;

public class FileExportmpl implements NewsExport{
	// 定义一个文件用来保存新闻内容
	private static final String saveFilePath = "NewsFiles\\rss.txt";
	
	private FileWriter fw;// 写入字符流
	private BufferedWriter bfw;// 输出缓冲区的缓冲字符输出流
	
	// RSSService对象
	private RSSService rssService;
	
	
	// 实现NewsDao接口的save()方法，将新闻内容列表保存到指定文件中，保存成功返回true
	public boolean save(List<News> newsList) {
		// 如果newsList为空则保存失败
		if(newsList == null){
			return false;
		}
		
		// 创建RSSService对象
		rssService = new RSSService();
		
		// 以指定路径为参数创建文件
		File file = new File(saveFilePath);
		
		// 初始化操作文件的类
		fw = null;
		bfw = null;
		
		try{
			// 根据给定的文件名以及附加写入数据来构造 FileWriter 
			fw = new FileWriter(file, true);
			bfw = new BufferedWriter(fw);// 包装FileWriter
			
			// 遍历新闻内容列表，将新闻内容保存到文件中
			for(int i = 0; i < newsList.size(); i++){
				News news = newsList.get(i);// 遍历获取新闻内容
				// 将获取到的内容写入文件，写完换行
				bfw.write(rssService.newsToString(news));
				bfw.newLine();
			}
			
			bfw.flush();// 刷新该流的缓冲
		}catch (IOException e){
			e.printStackTrace();
		}finally{
			try {
				// 先关闭缓冲流再关闭写入字符流
				bfw.close();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return true;
	}
	
}
