package com.ruanko.service;

import java.io.*;
import java.util.*;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.ruanko.dao.NewsExport;
import com.ruanko.dao.impl.FileExportmpl;
import com.ruanko.model.Channel;
import com.ruanko.model.News;

// 创建一个频道类
public class RSSService {
	private List<Channel> channelList;// 使用泛型定义存储频道信息的List集合
	private List<News> newsList;// 使用泛型定义新闻内容信息列表
	private NewsExport rssExport;// 数据访问层NewsExport类对象

	// 无参构造方法
	public RSSService() {
		channelList = null;// 初始化channelList
		rssExport = new FileExportmpl();// 初始化rssExport
	}

	// 初始化频道信息列表
	public List<Channel> getChannelList() {
		if (channelList == null) {
			channelList = new ArrayList<Channel>();

			// 创建"腾讯-国际要闻"频道
			Channel channel1 = new Channel();
			channel1.setName("腾讯-国际要闻");
			channel1.setFilePath("NewsFiles\\rss_newswj.xml");

			// 创建"腾讯-国内新闻"频道
			Channel channel2 = new Channel();
			channel2.setName("腾讯-国内新闻");
			channel2.setFilePath("NewsFiles\\rss_newsgn.xml");

			// 创建"新浪-体育新闻"频道
			Channel channel3 = new Channel();
			channel3.setName("新浪-体育新闻");
			channel3.setFilePath("NewsFiles\\sports.xml");

			// 创建"新浪-社会新闻"频道
			Channel channel4 = new Channel();
			channel4.setName("新浪-社会新闻");
			channel4.setFilePath("NewsFiles\\focus15.xml");

			// 将频道信息添加到频道列表中
			channelList.add(channel1);
			channelList.add(channel2);
			channelList.add(channel3);
			channelList.add(channel4);

		}
		return channelList;
	}

	// 通过jdom.jar将XML文件解析为Document对象并返回
	public Document load(String filePath) {
		Document doc = null;// 初始化Document对象
		// 指定解析器
		SAXBuilder sb = new SAXBuilder(false);

		// 判断文件是否存在
		File fXml = new File(filePath);
		if (fXml.exists() && fXml.isFile()) {
			// 如果文件存在，加载一个XML文件得到Document对象
			try {
				doc = sb.build(fXml);// 调用build()方法得到Document对象
			} catch (JDOMException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return doc;

	}

	// 通过调用load()和parse()方法获得新闻内容信息列表
	public List<News> getNewsList(String filePath) {
		Document doc = load(filePath);
		newsList = parse(doc);

		return newsList;
	}

	// 将新闻列表转换为字符串
	public String newsToString(News news) {
		String content = null;
		content = "标题：" + news.getTitle() + "\r\n" + "链接：" + news.getLink()
				+ "\r\n" + "作者：" + news.getAuthor() + "\r\n" + "发布时间："
				+ news.getPubDate() + "\r\n" + "新闻描述：" + news.getDescription()
				+ "\r\n" + "----------------------------------------------\r\n";
		return content;
	}

	// 将RSS文件中的item元素转化为News
	private News itemToNews(Element item) {
		News news = new News();

		// 获得节点内容
		String title = item.getChildText("title").trim();
		String link = item.getChildText("link");
		String author = item.getChildText("author");
		String guid = item.getChildText("guid");
		String category = item.getChildText("category");
		String pubDate = item.getChildText("pubDate");
		String comment = item.getChildText("comment");
		String description = item.getChildText("description").trim();

		// 设置节点内容
		news.setTitle(title);
		news.setLink(link);
		news.setAuthor(author);
		news.setGuid(guid);
		news.setGuid(guid);
		news.setCategory(category);
		news.setPubDate(pubDate);
		news.setComment(comment);
		news.setDescription(description);

		return news;
	}

	// 解析内存中的RSS文件，返回新闻对象News列表
	private List<News> parse(Document doc) {
		// 创建News类型的ArrayList集合
		List<News> newsList = new ArrayList<News>();
		News news = null;

		// 得到XML文档的根节点
		Element root = doc.getRootElement();

		// 获得item标签
		Element eChannel = root.getChild("channel");
		@SuppressWarnings("unchecked")
		List<Element> itemList = eChannel.getChildren("item");

		// 生成News对象列表
		for (int i = 0; i < itemList.size(); i++) {
			Element item = itemList.get(i);
			news = itemToNews(item);
			newsList.add(news);
		}

		return newsList;
	}

	// 调用数据访问层的save()方法，保存新闻内容，成功则返回true
	public boolean save(List<News> newsList){
		boolean flag = false;// 设置标识为false
		
		// 如果数据在访问层保存成功
		if(rssExport.save(newsList)){
			flag = true;// 设置标识为
		}
		
		return flag;
	}
}
