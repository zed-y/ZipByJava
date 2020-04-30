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
	JFrame frame = new JFrame("Demo");// ��ܲ���
	JTabbedPane tabPane = new JTabbedPane();// ѡ�����
	Container con = new Container();//
	JLabel label1 = new JLabel("�ļ�Ŀ¼");
	JTextField text1 = new JTextField();// TextField Ŀ¼��·��
	JButton button1 = new JButton("...");// ѡ��
	JFileChooser jfc = new JFileChooser();// �ļ�ѡ����
	JButton button2 = new JButton("ȷ��");//
	
	//����Ŀ¼ѡ�����
	WindowShow() {
		jfc.setCurrentDirectory(new File("E://"));// �ļ�ѡ�����ĳ�ʼĿ¼��ΪE��
		
		double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		
		double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		
		frame.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150));// �趨���ڳ���λ��
		frame.setSize(280, 150);// �趨���ڴ�С
		frame.setContentPane(tabPane);// ���ò���
		label1.setBounds(10, 10, 70, 20);
		text1.setBounds(75, 10, 120, 20);
		button1.setBounds(210, 10, 50, 20);
		button2.setBounds(25, 35, 60, 20);
		button1.addActionListener(this); // ����¼�����
		button2.addActionListener(this); // ����¼�����
		con.add(label1);
		con.add(text1);
		con.add(button1);
		con.add(button2);
		frame.setVisible(true);// ���ڿɼ�
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// ʹ�ܹرմ��ڣ���������
		tabPane.add("Ŀ¼ѡ�����", con);// ��Ӳ���1
	}
	/*
	 * ����·��������Ŀ¼�µ������ļ��Լ�Ŀ¼������ȫ�ֱ���s_path��
	 */
	public static void readFileAndDirectory(String path) {
		File file = new File(path);
		// ·��������
		if(!file.exists()) {
			throw new RuntimeException(String.format("·�� %s ������",path));
		}
		//zipFile = new ZipFile();
		int ll = 2+flag*4;
		flag++;
		File[] childFiles = file.listFiles();	// ĳһ·�������ļ�(��)
		// ��ʾ����
		for(File childFile : childFiles) {
			for(int i = 0; i < ll; i++) {
				s_path += "-";
			}
			if(childFile.isFile()) {
				String temp_s = "[�ļ�]��" + childFile.getName() + "  ==>(����·��:\"" + childFile.getAbsolutePath() + "\")\n";
				s_path += temp_s;
			}
			else {
				String temp_s = "[Ŀ¼]��" + childFile.getName() + "\n";
				s_path += temp_s;
				// �ݹ�����ļ���
				readFileAndDirectory(childFile.getAbsolutePath());
			}
		}
	}
	/**
	 * ʱ������ķ���
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(button1)) {// �жϴ��������İ�ť���ĸ�
			jfc.setFileSelectionMode(1);// �趨ֻ��ѡ���ļ���
			int state = jfc.showOpenDialog(null);// �˾��Ǵ��ļ�ѡ��������Ĵ������
			// ��������ȡ��
			if (state == 1) {
				s_path = "";
				return;
			} else {	// ѡ�����ļ�Ŀ¼
				File f = jfc.getSelectedFile();// fΪѡ�񵽵�Ŀ¼
				text1.setText(f.getAbsolutePath());
				//���ú��������Ŀ¼�������ļ�
				readFileAndDirectory(f.getAbsolutePath());
			}
		}
		// ���"ȷ��"��ť
		if (e.getSource().equals(button2)) {
			//Start ��ӹ�����
			JTextArea textArea = new JTextArea(s_path); 
			 JScrollPane scrollPane = new JScrollPane(textArea); 
			 textArea.setLineWrap(true);
			 textArea.setWrapStyleWord(true);
			 scrollPane.setPreferredSize(new Dimension(1000,500));
			//End
			JOptionPane.showMessageDialog(null, scrollPane, "·��", JOptionPane.PLAIN_MESSAGE);
			s_path = "";
		}
	}
	public static void main(String[] args) {
		new WindowShow();
	}
}


