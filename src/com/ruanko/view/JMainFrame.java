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

	// ���ڱ���
	private final static String TITLE = "RSS�Ķ���";// ���ڱ���

	// ���
	private List<Channel> channelList;// ����Ƶ���б�
	private JButton jbExport;// "����"��ť
	private JTextArea newsTextArea;// ���������ı���
	private JScrollPane jspContent;// �������������ı����Ĺ��������
	private DefaultTableModel dtmTableModel;// �������ģ��
	private JTable jtTable;// ���
	private JScrollPane jspTable;// ���ñ��Ĺ��������
	private JMenuBar menuBar;// �˵���
	private JMenu fileMenu;// �ļ��˵�
	private JMenu helpMenu;// �����˵�
	private JMenuItem channelMenuItem;// ����Ƶ���˵���
	private JMenuItem exportMenuItem;// "����"�˵���
	private JMenuItem exitMenuItem;// "�˳�"�˵���
	private JToolBar toolBar;// ������
	private JSplitPane verticalPane;// ��ֱ�ָ����
	private JPanel statusPane;// ״̬���
	private JLabel jLabel = new JLabel("www.ruanko.com");// "www.ruanko.com"��ǩ

	private List<News> newsList;// ʹ�÷��Ͷ�������������Ϣ�б�

	// RSSService����
	private RSSService rssService = null;

	// �޲ι��췽������ʼ������
	public JMainFrame() {
		// ��ʼ��RSSService���󣬲���ȡ����Ƶ���б�
		rssService = new RSSService();
		channelList = rssService.getChannelList();

		this.setTitle(TITLE);// ���ô��ڱ���
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// ���ô��ڿɹر�
		this.setLocation(400, 100);// ���ô���λ��
		this.setContentPane(getJMain());// ����getJMain()�������������ӵ�������

		this.setJMenuBar(getJMBar());// �ڴ�����Ӳ˵���

		this.pack();// �������ڴ�С
	}

	// ����һ�������
	private JPanel getJMain() {
		JPanel jpMain = new JPanel(new BorderLayout());// ����һ�������
		jpMain.add(getJTBar(), BorderLayout.NORTH);// ����getJTBar()������ӹ�����������山��
		jpMain.add(getClientArea(), BorderLayout.CENTER);// ����getClientArea()������ӿͻ���������в�
		jpMain.add(getStatusPane(), BorderLayout.SOUTH);// ����getStatusPane()�������״̬����������ϲ�

		return jpMain;
	}

	// ����һ��"����"��ť
	private JButton getJBExport() {
		// ����ͼ�괴��"����"��ť
		ImageIcon icon = new ImageIcon("images\\Export.jpg");
		jbExport = new JButton(icon);

		// ����ͼ������
		jbExport.setToolTipText("����");

		// ͨ���ڲ������Ϊ"����"��ťע���¼�������
		ExportActionListener listener = new ExportActionListener();
		jbExport.addActionListener(listener);

		return jbExport;
	}

	// ����һ������������������
	private JScrollPane getJSPContent() {
		// ���ù������Ϊ��
		jspContent = null;
		if (newsTextArea == null) {
			newsTextArea = new JTextArea(7, 30);// ���������ı���
			newsTextArea.setEditable(false);// ���ɱ��༭
			newsTextArea.setBackground(new Color(204, 238, 207));// �Զ��屳����ɫ
		}
		// �������ı�����ӵ����������
		jspContent = new JScrollPane(newsTextArea);

		// ����ˮƽ��������-������һֱ����
		jspContent
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		// ���ô�ֱ��������-������һֱ����
		jspContent
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		return jspContent;
	}

	// ����һ�������������
	private JScrollPane getJSPTable() {
		// ��������ʼ��һ���������
		jspTable = null;

		// ������Ϊ��
		if (jtTable == null) {
			// ����һ��760*200���صı��
			jtTable = new JTable();
			jtTable.setPreferredScrollableViewportSize(new Dimension(760, 200));

			// // ���ñ�񲻿ɱ��༭
			// jtTable.setEnabled(false);

			// ���ñ����ɫ
			jtTable.setBackground(new Color(200, 200, 250));

			// �����������ģ�ͣ�����Ӹ��б���
			dtmTableModel = new DefaultTableModel();
			dtmTableModel.addColumn("����");
			dtmTableModel.addColumn("����ʱ��");
			dtmTableModel.addColumn("����ʱ��");
			dtmTableModel.addColumn("����");

			// �Ը�����ģ��Ϊ�����������
			jtTable.setModel(dtmTableModel);

			// �������ڲ������ʽΪ��������굥���¼�
			jtTable.addMouseListener(new MouseAdapter() {
				// ��дmouseClicked()����
				public void mouseClicked(MouseEvent e) {
					// �ж��������Ƿ񵥻�
					if (e.getButton() == MouseEvent.BUTTON1) {
						int selectedRow = jtTable.getSelectedRow();// ���ص�һ��ѡ���е����������û��ѡ�����У��򷵻�
																	// -1��
						News selectedNews = newsList.get(selectedRow);// ���ѡ�е���������
						newsTextArea.setText(rssService
								.newsToString(selectedNews));// �������ı�����ʾѡ�е���������
					}
				}
			});

		}
		// �����Ž��������������
		jspTable = new JScrollPane(jtTable);
		return jspTable;
	}

	// �ڱ������ʾ������Ϣ
	private void showTable(List<News> newsList) {
		// ��ձ������
		dtmTableModel.setRowCount(0);

		// �������������б�����Ӧ������������ʾ�������
		for (int i = 0; i < newsList.size(); i++) {
			News news = newsList.get(i);// �������������б�

			// ָ��ʱ���ʽ����ȡ��ǰ����
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"Gyyyy��MM��dd�� HH:mm:ss");
			Date date = new Date();
			String currentDate = dateFormat.format(date);

			// �����ŵı��⡢��ǰ���ڡ��������ڡ�������ʾ�ڱ����
			String[] rowData = { news.getTitle(), currentDate,
					news.getPubDate(), news.getAuthor() };
			dtmTableModel.addRow(rowData);
		}
	}

	// ����һ���˵���
	private JMenuBar getJMBar() {
		menuBar = new JMenuBar();// �����˵���
		// ���ļ��˵��Ͱ����˵���ӵ��˵���
		menuBar.add(getFileMenu());
		menuBar.add(getHelpMenu());
				
		return menuBar;
	}

	// ����һ��������
	private JToolBar getJTBar() {
		// ����������
		toolBar = new JToolBar();

		// ��"����"��ť��ӵ�������
		toolBar.add(getJBExport());

		return toolBar;
	}

	// ����һ���ͻ����������������������ı���
	private JSplitPane getClientArea() {
		// ������ֱ�ָ����
		verticalPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

		// ���÷ָ�������
		verticalPane.setDividerLocation(225);// ���÷ָ���λ��
		verticalPane.setEnabled(false);// ���÷ָ������ܱ��϶�

		// �����������ı����ӵ��ָ�����ϲ�
		verticalPane.setLeftComponent(getJSPTable());

		// ���������������������ı�����ӵ��ָ�����²�
		verticalPane.setRightComponent(getJSPContent());

		return verticalPane;
	}

	// ����һ��״̬���
	private JPanel getStatusPane() {
		// ����һ��״̬���
		statusPane = new JPanel();
		statusPane.setLayout(new FlowLayout(FlowLayout.LEFT));// ��������벼��

		// ����ǩ��ӵ�״̬����
		statusPane.add(jLabel);

		return statusPane;
	}

	// ����һ���������ŵ��¼���������
	public class ExportActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// �������ɹ��򵯳��Ի���
			if (rssService.save(newsList)) {
				JOptionPane.showMessageDialog(null, "�������ݵ����ɹ�");
			} else {
				JOptionPane.showMessageDialog(null, "�������ݵ���ʧ��");
			}
		}
	}
	
	// ����һ���ļ��˵�
	private JMenu getFileMenu(){
		fileMenu = new JMenu("�ļ�(F)");// �����ļ��˵�
		
		exportMenuItem = new JMenuItem("����(E)");// ����"����"�˵���
		exitMenuItem = new JMenuItem("�˳�(X)");// ����"�˳�"�˵���



		// ����Ƶ���б�˵���
		for (int i = 0; i < channelList.size(); i++) {// ����Ƶ���б�
			final Channel channel = channelList.get(i);// ��ȡƵ��
			fileMenu.addSeparator();// �ָ���

			// ���Ƶ���б��ļ��˵�
			channelMenuItem = new JMenuItem(channel.toString());
			fileMenu.add(channelMenuItem);

			// ͨ�������ڲ���ΪƵ���б�˵���ע���¼�������
			channelMenuItem.addActionListener(new ActionListener() {
				// ��дactionPerformed()����
				public void actionPerformed(ActionEvent e) {
					// ��ȡƵ����Ϣ�ĵ�ַ
					String filePath = channel.getFilePath();

					// ����getNewsList()������ȡ������Ϣ�б�
					newsList = rssService.getNewsList(filePath);

					// ������������ʾ�ڱ����
					showTable(newsList);
				}

			});
		}

		// ͨ���ڲ������Ϊ"����"�˵���ע���¼�������
		ExportActionListener listener = new ExportActionListener();
		exportMenuItem.addActionListener(listener);

		// ͨ�������ڲ���Ϊ"�˳�"�˵���ע���¼�������
		exitMenuItem.addActionListener(new ActionListener() {
			// ��дactionPerformed()����
			public void actionPerformed(ActionEvent e) {
				System.exit(0);// �˳���������
			}
		});
		
		// ���"�˳�"�˵����ݼ�
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		
		// ���"����"�˵����ݼ�
		exportMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		
		// ���"����"��"�˳�"�˵���˵�
		fileMenu.add(exportMenuItem);
		fileMenu.add(exitMenuItem);
		
		return fileMenu;
	}
	
	// ����һ�������˵�
	private JMenu getHelpMenu(){
		helpMenu = new JMenu("����(H)");// ���������˵�
		
		return helpMenu;
	}
}
