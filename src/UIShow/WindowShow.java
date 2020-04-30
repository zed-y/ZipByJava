package UIShow;

import java.lang.String;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import Zip.*;

public class WindowShow implements ActionListener {
	public ZipFile zipFile = null;
	public static int flag = 0;
	public static String s_path = "";
	JFrame frame = new JFrame("Demo");// 框架布局
	JTabbedPane tabPane = new JTabbedPane();// 选项卡布局
	Container con = new Container();//
	JLabel label1 = new JLabel("文件目录");
	JTextField text1 = new JTextField();// TextField 目录的路径
	JButton button1 = new JButton("...");// 选择
	JFileChooser jfc = new JFileChooser();// 文件选择器
	JButton button2 = new JButton("确定");//
	
	//绘制目录选择界面
	WindowShow() {
		jfc.setCurrentDirectory(new File("E://"));// 文件选择器的初始目录定为E盘
		
		double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		
		double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		
		frame.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150));// 设定窗口出现位置
		frame.setSize(280, 150);// 设定窗口大小
		frame.setContentPane(tabPane);// 设置布局
		label1.setBounds(10, 10, 70, 20);
		text1.setBounds(75, 10, 120, 20);
		button1.setBounds(210, 10, 50, 20);
		button2.setBounds(25, 35, 60, 20);
		button1.addActionListener(this); // 添加事件处理
		button2.addActionListener(this); // 添加事件处理
		con.add(label1);
		con.add(text1);
		con.add(button1);
		con.add(button2);
		frame.setVisible(true);// 窗口可见
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 使能关闭窗口，结束程序
		tabPane.add("目录选择面板", con);// 添加布局1
	}
	/*
	 * 输入路径，将该目录下的所有文件以及目录保存在全局变量s_path下
	 */
	public static void readFileAndDirectory(String path) {
		File file = new File(path);
		// 路径不存在
		if(!file.exists()) {
			throw new RuntimeException(String.format("路径 %s 不存在",path));
		}
		//zipFile = new ZipFile();
		int ll = 2+flag*4;
		flag++;
		File[] childFiles = file.listFiles();	// 某一路径的子文件(夹)
		// 显示缩进
		for(File childFile : childFiles) {
			for(int i = 0; i < ll; i++) {
				s_path += "-";
			}
			if(childFile.isFile()) {
				String temp_s = "[文件]：" + childFile.getName() + "  ==>(绝对路径:\"" + childFile.getAbsolutePath() + "\")\n";
				s_path += temp_s;
			}
			else {
				String temp_s = "[目录]：" + childFile.getName() + "\n";
				s_path += temp_s;
				// 递归遍历文件夹
				readFileAndDirectory(childFile.getAbsolutePath());
			}
		}
	}
	/**
	 * 时间监听的方法
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(button1)) {// 判断触发方法的按钮是哪个
			jfc.setFileSelectionMode(1);// 设定只能选择到文件夹
			int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
			// 如果点击了取消
			if (state == 1) {
				s_path = "";
				return;
			} else {	// 选定了文件目录
				File f = jfc.getSelectedFile();// f为选择到的目录
				text1.setText(f.getAbsolutePath());
				//调用函数，获得目录下所有文件
				readFileAndDirectory(f.getAbsolutePath());
			}
		}
		// 点击"确定"按钮
		if (e.getSource().equals(button2)) {
			//Start 添加滚动条
			JTextArea textArea = new JTextArea(s_path); 
			 JScrollPane scrollPane = new JScrollPane(textArea); 
			 textArea.setLineWrap(true);
			 textArea.setWrapStyleWord(true);
			 scrollPane.setPreferredSize(new Dimension(1000,500));
			//End
			JOptionPane.showMessageDialog(null, scrollPane, "路径", JOptionPane.PLAIN_MESSAGE);
			s_path = "";
		}
	}
	public static void main(String[] args) {
		new WindowShow();
	}
}


