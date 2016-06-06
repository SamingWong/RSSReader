package com.ruanko.model;

public class News {

	private String title;// ���ű���
	private String link;// ��������
	private String author;// ��������
	private String guid;// ������ַ
	private String category;// ���Ű��
	private String pubDate;// ���ŷ�������
	private String comment;// ����Ҫ��
	private String description;// ��������

	// �޲ι��췽��
	public News() {
		// ��ʼ������
		this.setTitle("");
		this.setLink("");
		this.setAuthor("");
		this.setGuid("");
		this.setCategory("");
		this.setPubDate("");
		this.setComment("");
		this.setDescription("");
	}

	// ��ȡ���ű���
	public String getTitle() {
		return title;
	}

	// �������ű���
	public void setTitle(String title) {
		this.title = title;
	}

	// ��ȡ��������
	public String getLink() {
		return link;
	}

	// ������������
	public void setLink(String link) {
		this.link = link;
	}

	// ��ȡ��������
	public String getAuthor() {
		return author;
	}

	// ������������
	public void setAuthor(String author) {
		this.author = author;
	}

	// ��ȡ������ַ
	public String getGuid() {
		return guid;
	}

	// ����������ַ
	public void setGuid(String guid) {
		this.guid = guid;
	}

	// ��ȡ���Ű��
	public String getCategory() {
		return category;
	}

	// �������Ű��
	public void setCategory(String category) {
		this.category = category;
	}

	// ��ȡ����Ҫ��
	public String getComment() {
		return comment;
	}

	// ��������Ҫ��
	public void setComment(String comment) {
		this.comment = comment;
	}

	// ��ȡ���ŷ�������
	public String getPubDate() {
		return pubDate;
	}

	// �������ŷ�������
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	// ��ȡ��������
	public String getDescription() {
		return description;
	}

	// ������������
	public void setDescription(String description) {
		this.description = description;
	}

}
