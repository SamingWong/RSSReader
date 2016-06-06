package com.ruanko.model;

public class Channel {
	private String name;// 频道名称
	private String filePath;// 本地频道文件路径
	private String url;// 网络频道文件路径

	// 无参构造方法
	public Channel() {
		// 初始化属性
		this.setName("");
		this.setFilePath("");
		this.setUrl("");
	}

	// 获取频道名称
	public String getName() {
		return name;
	}

	// 设置频道名称
	public void setName(String name) {
		this.name = name;
	}

	// 获取本地频道文件路径
	public String getFilePath() {
		return filePath;
	}

	// 设置本地频道文件路径
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	// 获取网络频道文件路径
	public String getUrl() {
		return url;
	}

	// 设置网络频道文件路径
	public void setUrl(String url) {
		this.url = url;
	}

	// 重写toStirng()方法
	public String toString() {
		return this.name;
	}
}
