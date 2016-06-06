package com.ruanko.view;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;

import com.ruanko.model.Channel;
import com.ruanko.model.News;
import com.ruanko.service.RSSService;

public class JMainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 窗口标题
	private final static String TITLE = "RSS阅读器";// 窗口标题

	// 组件
	private List<Channel> channelList;// 新闻频道列表
	private JButton jbExport;// "导出"按钮
	private JTextArea newsTextArea;// 新闻内容文本区
	private JScrollPane jspContent;// 放置新闻内容文本区的滚动条面板
	private DefaultTableModel dtmTableModel;// 表格数据模型
	private JTable jtTable;// 表格
	private JScrollPane jspTable;// 放置表格的滚动条面板
	private JMenuBar menuBar;// 菜单栏
	private JMenu fileMenu;// 文件菜单
	private JMenu helpMenu;// 帮助菜单
	private JMenuItem channelMenuItem;// 新闻频道菜单项
	private JMenuItem exportMenuItem;// "导出"菜单项
	private JMenuItem exitMenuItem;// "退出"菜单项
	private JToolBar toolBar;// 工具栏
	private JSplitPane verticalPane;// 垂直分隔面板
	private JPanel statusPane;// 状态面板
	private JLabel jLabel = new JLabel("www.ruanko.com");// "www.ruanko.com"标签

	private List<News> newsList;// 使用泛型定义新闻内容信息列表

	// RSSService对象
	private RSSService rssService = null;

	// 无参构造方法，初始化窗口
	public JMainFrame() {
		// 初始化RSSService对象，并获取新闻频道列表
		rssService = new RSSService();
		channelList = rssService.getChannelList();

		this.setTitle(TITLE);// 设置窗口标题
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设置窗口可关闭
		this.setLocation(400, 100);// 设置窗口位置
		this.setContentPane(getJMain());// 调用getJMain()方法将主面板添加到窗口中

		this.setJMenuBar(getJMBar());// 在窗口添加菜单栏

		this.pack();// 调整窗口大小
	}

	// 返回一个主面板
	private JPanel getJMain() {
		JPanel jpMain = new JPanel(new BorderLayout());// 创建一个主面板
		jpMain.add(getJTBar(), BorderLayout.NORTH);// 调用getJTBar()方法添加工具栏到主面板北部
		jpMain.add(getClientArea(), BorderLayout.CENTER);// 调用getClientArea()方法添加客户区主面板中部
		jpMain.add(getStatusPane(), BorderLayout.SOUTH);// 调用getStatusPane()方法添加状态栏到主面板南部

		return jpMain;
	}

	// 返回一个"导出"按钮
	private JButton getJBExport() {
		// 根据图标创建"导出"按钮
		ImageIcon icon = new ImageIcon("images\\Export.jpg");
		jbExport = new JButton(icon);

		// 设置图标文字
		jbExport.setToolTipText("导出");

		// 通过内部类对象为"导出"按钮注册事件监听器
		ExportActionListener listener = new ExportActionListener();
		jbExport.addActionListener(listener);

		return jbExport;
	}

	// 返回一个带滚动条的新闻区
	private JScrollPane getJSPContent() {
		// 设置滚动面板为空
		jspContent = null;
		if (newsTextArea == null) {
			newsTextArea = new JTextArea(7, 30);// 设置新闻文本区
			newsTextArea.setEditable(false);// 不可被编辑
			newsTextArea.setBackground(new Color(204, 238, 207));// 自定义背景颜色
		}
		// 把新闻文本区添加到滚动条面板
		jspContent = new JScrollPane(newsTextArea);

		// 设置水平滚动策略-滚动条一直存在
		jspContent
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		// 设置垂直滚动策略-滚动条一直存在
		jspContent
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		return jspContent;
	}

	// 返回一个带滚动条表格
	private JScrollPane getJSPTable() {
		// 创建并初始化一个滚动面板
		jspTable = null;

		// 如果表格为空
		if (jtTable == null) {
			// 创建一个760*200像素的表格
			jtTable = new JTable();
			jtTable.setPreferredScrollableViewportSize(new Dimension(760, 200));

			// // 设置表格不可被编辑
			// jtTable.setEnabled(false);

			// 设置表格颜色
			jtTable.setBackground(new Color(200, 200, 250));

			// 创建表格数据模型，并添加格列标题
			dtmTableModel = new DefaultTableModel();
			dtmTableModel.addColumn("主题");
			dtmTableModel.addColumn("接收时间");
			dtmTableModel.addColumn("发布时间");
			dtmTableModel.addColumn("作者");

			// 以该数据模型为参数创建表格
			jtTable.setModel(dtmTableModel);

			// 以匿名内部类的形式为表格添加鼠标单击事件
			jtTable.addMouseListener(new MouseAdapter() {
				// 重写mouseClicked()方法
				public void mouseClicked(MouseEvent e) {
					// 判断鼠标左键是否单击
					if (e.getButton() == MouseEvent.BUTTON1) {
						int selectedRow = jtTable.getSelectedRow();// 返回第一个选定行的索引；如果没有选定的行，则返回
																	// -1。
						News selectedNews = newsList.get(selectedRow);// 获得选中的新闻内容
						newsTextArea.setText(rssService
								.newsToString(selectedNews));// 在新闻文本区显示选中的新闻内容
					}
				}
			});

		}
		// 将表格放进带滚动条的面板
		jspTable = new JScrollPane(jtTable);
		return jspTable;
	}

	// 在表格中显示新闻信息
	private void showTable(List<News> newsList) {
		// 清空表格内容
		dtmTableModel.setRowCount(0);

		// 遍历新闻内容列表，将相应的新闻内容显示到表格中
		for (int i = 0; i < newsList.size(); i++) {
			News news = newsList.get(i);// 遍历新闻内容列表

			// 指定时间格式，获取当前日期
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"Gyyyy年MM月dd日 HH:mm:ss");
			Date date = new Date();
			String currentDate = dateFormat.format(date);

			// 将新闻的标题、当前日期、发布日期、作者显示在表格中
			String[] rowData = { news.getTitle(), currentDate,
					news.getPubDate(), news.getAuthor() };
			dtmTableModel.addRow(rowData);
		}
	}

	// 返回一个菜单栏
	private JMenuBar getJMBar() {
		menuBar = new JMenuBar();// 创建菜单栏
		// 将文件菜单和帮助菜单添加到菜单栏
		menuBar.add(getFileMenu());
		menuBar.add(getHelpMenu());
				
		return menuBar;
	}

	// 返回一个工具栏
	private JToolBar getJTBar() {
		// 创建工具栏
		toolBar = new JToolBar();

		// 将"导出"按钮添加到工具栏
		toolBar.add(getJBExport());

		return toolBar;
	}

	// 返回一个客户区，包含表格和新闻内容文本区
	private JSplitPane getClientArea() {
		// 创建垂直分隔面板
		verticalPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

		// 设置分隔条属性
		verticalPane.setDividerLocation(225);// 设置分隔条位置
		verticalPane.setEnabled(false);// 设置分隔条不能被拖动

		// 将带滚动条的表格添加到分隔面板上部
		verticalPane.setLeftComponent(getJSPTable());

		// 将带滚动条的新闻内容文本区添加到分隔面板下部
		verticalPane.setRightComponent(getJSPContent());

		return verticalPane;
	}

	// 返回一个状态面板
	private JPanel getStatusPane() {
		// 创建一个状态面板
		statusPane = new JPanel();
		statusPane.setLayout(new FlowLayout(FlowLayout.LEFT));// 设置左对齐布局

		// 将标签添加到状态栏中
		statusPane.add(jLabel);

		return statusPane;
	}

	// 创建一个导出新闻的事件监听器的
	public class ExportActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// 如果保存成功则弹出对话框
			if (rssService.save(newsList)) {
				JOptionPane.showMessageDialog(null, "新闻内容导出成功");
			} else {
				JOptionPane.showMessageDialog(null, "新闻内容导出失败");
			}
		}
	}
	
	// 返回一个文件菜单
	private JMenu getFileMenu(){
		fileMenu = new JMenu("文件(F)");// 创建文件菜单
		
		exportMenuItem = new JMenuItem("导出(E)");// 创建"导出"菜单项
		exitMenuItem = new JMenuItem("退出(X)");// 创建"退出"菜单项



		// 创建频道列表菜单项
		for (int i = 0; i < channelList.size(); i++) {// 遍历频道列表
			final Channel channel = channelList.get(i);// 获取频道
			fileMenu.addSeparator();// 分隔符

			// 添加频道列表到文件菜单
			channelMenuItem = new JMenuItem(channel.toString());
			fileMenu.add(channelMenuItem);

			// 通过匿名内部类为频道列表菜单项注册事件监听器
			channelMenuItem.addActionListener(new ActionListener() {
				// 重写actionPerformed()方法
				public void actionPerformed(ActionEvent e) {
					// 获取频道信息的地址
					String filePath = channel.getFilePath();

					// 调用getNewsList()方法获取新闻信息列表
					newsList = rssService.getNewsList(filePath);

					// 将新闻内容显示在表格中
					showTable(newsList);
				}

			});
		}

		// 通过内部类对象为"导出"菜单项注册事件监听器
		ExportActionListener listener = new ExportActionListener();
		exportMenuItem.addActionListener(listener);

		// 通过匿名内部类为"退出"菜单项注册事件监听器
		exitMenuItem.addActionListener(new ActionListener() {
			// 重写actionPerformed()方法
			public void actionPerformed(ActionEvent e) {
				System.exit(0);// 退出整个程序
			}
		});
		
		// 添加"退出"菜单项快捷键
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		
		// 添加"导出"菜单项快捷键
		exportMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		
		// 添加"导出"和"退出"菜单项到菜单
		fileMenu.add(exportMenuItem);
		fileMenu.add(exitMenuItem);
		
		return fileMenu;
	}
	
	// 返回一个帮助菜单
	private JMenu getHelpMenu(){
		helpMenu = new JMenu("帮助(H)");// 创建帮助菜单
		
		return helpMenu;
	}
}
