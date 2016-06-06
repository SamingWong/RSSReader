package com.ruanko.dao.impl;

import java.io.*;
import java.util.*;

import com.ruanko.dao.NewsExport;
import com.ruanko.model.News;
import com.ruanko.service.RSSService;

public class FileExportmpl implements NewsExport{
	// ����һ���ļ�����������������
	private static final String saveFilePath = "NewsFiles\\rss.txt";
	
	private FileWriter fw;// д���ַ���
	private BufferedWriter bfw;// ����������Ļ����ַ������
	
	// RSSService����
	private RSSService rssService;
	
	
	// ʵ��NewsDao�ӿڵ�save()�����������������б��浽ָ���ļ��У�����ɹ�����true
	public boolean save(List<News> newsList) {
		// ���newsListΪ���򱣴�ʧ��
		if(newsList == null){
			return false;
		}
		
		// ����RSSService����
		rssService = new RSSService();
		
		// ��ָ��·��Ϊ���������ļ�
		File file = new File(saveFilePath);
		
		// ��ʼ�������ļ�����
		fw = null;
		bfw = null;
		
		try{
			// ���ݸ������ļ����Լ�����д������������ FileWriter 
			fw = new FileWriter(file, true);
			bfw = new BufferedWriter(fw);// ��װFileWriter
			
			// �������������б����������ݱ��浽�ļ���
			for(int i = 0; i < newsList.size(); i++){
				News news = newsList.get(i);// ������ȡ��������
				// ����ȡ��������д���ļ���д�껻��
				bfw.write(rssService.newsToString(news));
				bfw.newLine();
			}
			
			bfw.flush();// ˢ�¸����Ļ���
		}catch (IOException e){
			e.printStackTrace();
		}finally{
			try {
				// �ȹرջ������ٹر�д���ַ���
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
