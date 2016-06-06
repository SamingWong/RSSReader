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

// ����һ��Ƶ����
public class RSSService {
	private List<Channel> channelList;// ʹ�÷��Ͷ���洢Ƶ����Ϣ��List����
	private List<News> newsList;// ʹ�÷��Ͷ�������������Ϣ�б�
	private NewsExport rssExport;// ���ݷ��ʲ�NewsExport�����

	// �޲ι��췽��
	public RSSService() {
		channelList = null;// ��ʼ��channelList
		rssExport = new FileExportmpl();// ��ʼ��rssExport
	}

	// ��ʼ��Ƶ����Ϣ�б�
	public List<Channel> getChannelList() {
		if (channelList == null) {
			channelList = new ArrayList<Channel>();

			// ����"��Ѷ-����Ҫ��"Ƶ��
			Channel channel1 = new Channel();
			channel1.setName("��Ѷ-����Ҫ��");
			channel1.setFilePath("NewsFiles\\rss_newswj.xml");

			// ����"��Ѷ-��������"Ƶ��
			Channel channel2 = new Channel();
			channel2.setName("��Ѷ-��������");
			channel2.setFilePath("NewsFiles\\rss_newsgn.xml");

			// ����"����-��������"Ƶ��
			Channel channel3 = new Channel();
			channel3.setName("����-��������");
			channel3.setFilePath("NewsFiles\\sports.xml");

			// ����"����-�������"Ƶ��
			Channel channel4 = new Channel();
			channel4.setName("����-�������");
			channel4.setFilePath("NewsFiles\\focus15.xml");

			// ��Ƶ����Ϣ��ӵ�Ƶ���б���
			channelList.add(channel1);
			channelList.add(channel2);
			channelList.add(channel3);
			channelList.add(channel4);

		}
		return channelList;
	}

	// ͨ��jdom.jar��XML�ļ�����ΪDocument���󲢷���
	public Document load(String filePath) {
		Document doc = null;// ��ʼ��Document����
		// ָ��������
		SAXBuilder sb = new SAXBuilder(false);

		// �ж��ļ��Ƿ����
		File fXml = new File(filePath);
		if (fXml.exists() && fXml.isFile()) {
			// ����ļ����ڣ�����һ��XML�ļ��õ�Document����
			try {
				doc = sb.build(fXml);// ����build()�����õ�Document����
			} catch (JDOMException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return doc;

	}

	// ͨ������load()��parse()�����������������Ϣ�б�
	public List<News> getNewsList(String filePath) {
		Document doc = load(filePath);
		newsList = parse(doc);

		return newsList;
	}

	// �������б�ת��Ϊ�ַ���
	public String newsToString(News news) {
		String content = null;
		content = "���⣺" + news.getTitle() + "\r\n" + "���ӣ�" + news.getLink()
				+ "\r\n" + "���ߣ�" + news.getAuthor() + "\r\n" + "����ʱ�䣺"
				+ news.getPubDate() + "\r\n" + "����������" + news.getDescription()
				+ "\r\n" + "----------------------------------------------\r\n";
		return content;
	}

	// ��RSS�ļ��е�itemԪ��ת��ΪNews
	private News itemToNews(Element item) {
		News news = new News();

		// ��ýڵ�����
		String title = item.getChildText("title").trim();
		String link = item.getChildText("link");
		String author = item.getChildText("author");
		String guid = item.getChildText("guid");
		String category = item.getChildText("category");
		String pubDate = item.getChildText("pubDate");
		String comment = item.getChildText("comment");
		String description = item.getChildText("description").trim();

		// ���ýڵ�����
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

	// �����ڴ��е�RSS�ļ����������Ŷ���News�б�
	private List<News> parse(Document doc) {
		// ����News���͵�ArrayList����
		List<News> newsList = new ArrayList<News>();
		News news = null;

		// �õ�XML�ĵ��ĸ��ڵ�
		Element root = doc.getRootElement();

		// ���item��ǩ
		Element eChannel = root.getChild("channel");
		@SuppressWarnings("unchecked")
		List<Element> itemList = eChannel.getChildren("item");

		// ����News�����б�
		for (int i = 0; i < itemList.size(); i++) {
			Element item = itemList.get(i);
			news = itemToNews(item);
			newsList.add(news);
		}

		return newsList;
	}

	// �������ݷ��ʲ��save()�����������������ݣ��ɹ��򷵻�true
	public boolean save(List<News> newsList){
		boolean flag = false;// ���ñ�ʶΪfalse
		
		// ��������ڷ��ʲ㱣��ɹ�
		if(rssExport.save(newsList)){
			flag = true;// ���ñ�ʶΪ
		}
		
		return flag;
	}
}
