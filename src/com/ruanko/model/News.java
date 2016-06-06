package com.ruanko.model;

public class News {

	private String title;// 新闻标题
	private String link;// 新闻链接
	private String author;// 新闻作者
	private String guid;// 新闻网址
	private String category;// 新闻版块
	private String pubDate;// 新闻发布日期
	private String comment;// 新闻要闻
	private String description;// 新闻描述

	// 无参构造方法
	public News() {
		// 初始化属性
		this.setTitle("");
		this.setLink("");
		this.setAuthor("");
		this.setGuid("");
		this.setCategory("");
		this.setPubDate("");
		this.setComment("");
		this.setDescription("");
	}

	// 获取新闻标题
	public String getTitle() {
		return title;
	}

	// 设置新闻标题
	public void setTitle(String title) {
		this.title = title;
	}

	// 获取新闻链接
	public String getLink() {
		return link;
	}

	// 设置新闻链接
	public void setLink(String link) {
		this.link = link;
	}

	// 获取新闻作者
	public String getAuthor() {
		return author;
	}

	// 设置新闻作者
	public void setAuthor(String author) {
		this.author = author;
	}

	// 获取新闻网址
	public String getGuid() {
		return guid;
	}

	// 设置新闻网址
	public void setGuid(String guid) {
		this.guid = guid;
	}

	// 获取新闻版块
	public String getCategory() {
		return category;
	}

	// 设置新闻版块
	public void setCategory(String category) {
		this.category = category;
	}

	// 获取新闻要闻
	public String getComment() {
		return comment;
	}

	// 设置新闻要闻
	public void setComment(String comment) {
		this.comment = comment;
	}

	// 获取新闻发布日期
	public String getPubDate() {
		return pubDate;
	}

	// 设置新闻发布日期
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	// 获取新闻描述
	public String getDescription() {
		return description;
	}

	// 设置新闻描述
	public void setDescription(String description) {
		this.description = description;
	}

}
