package com.ruanko.model;

public class Channel {
	private String name;// Ƶ������
	private String filePath;// ����Ƶ���ļ�·��
	private String url;// ����Ƶ���ļ�·��

	// �޲ι��췽��
	public Channel() {
		// ��ʼ������
		this.setName("");
		this.setFilePath("");
		this.setUrl("");
	}

	// ��ȡƵ������
	public String getName() {
		return name;
	}

	// ����Ƶ������
	public void setName(String name) {
		this.name = name;
	}

	// ��ȡ����Ƶ���ļ�·��
	public String getFilePath() {
		return filePath;
	}

	// ���ñ���Ƶ���ļ�·��
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	// ��ȡ����Ƶ���ļ�·��
	public String getUrl() {
		return url;
	}

	// ��������Ƶ���ļ�·��
	public void setUrl(String url) {
		this.url = url;
	}

	// ��дtoStirng()����
	public String toString() {
		return this.name;
	}
}
