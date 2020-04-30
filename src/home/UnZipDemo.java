package home;

import java.io.*;
import java.util.zip.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.filechooser.*;

import Zip.UnZipFile;
import Zip.ZipFile;

/*
 * FileChooserDemo.java:
 */
public class UnZipDemo extends JPanel
                             implements ActionListener {
    static private final String newline = "\n";
    public String src = "";
    public String dst = "";
    JButton openButton, saveButton,startButton,b;
    JTextArea log;
    JFileChooser fc;
    JFrame jj;
	JLabel location = new JLabel("文件名:");
	JTextField locationText = new JTextField();
	JPanel jp = new JPanel();


    public UnZipDemo() {
        super(new BorderLayout());

        //Create the log first, because the action listeners
        log = new JTextArea(10,40);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        log.append("    解压缩文件！\n使用：先选择解压缩文件，在选择解压缩路径，最终点击Start即可！\n");
        JScrollPane logScrollPane = new JScrollPane(log);

        //Create a file chooser
        fc = new JFileChooser();
        fc.setCurrentDirectory(new File("E://"));// 文件选择器的初始目录定为E盘

        openButton = new JButton("选择文件");
        openButton.addActionListener(this);

        saveButton = new JButton("选择路径");
        saveButton.addActionListener(this);
        
        startButton = new JButton("S t a r t");
        startButton.addActionListener(this);
        
        b = new JButton("生成");
        b.addActionListener(this);
        
        //For layout purposes, put the buttons in a separate panel
        JPanel buttonPanel = new JPanel(); //use FlowLayout
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(startButton);

        //Add the buttons and the log to this panel.
        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
        
        // 新窗口设置 	
        jj = new JFrame("Chooser");
        jj.setSize(200, 200);
    	jj.setLocation(200, 200);
    	jj.setLayout(null);
    	jp.setBounds(10,10,150,100);
    	jp.setLayout(new GridLayout(2,1,10,10));
    }

    public void actionPerformed(ActionEvent e) {
    	
    	// 选择解压缩文件
        if (e.getSource() == openButton) {
        	this.src = "";
        	fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnVal = fc.showOpenDialog(UnZipDemo.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {// if 确定键
                File file = fc.getSelectedFile();
                this.src = file.getAbsolutePath();
                log.append("-->Open: " + this.src + newline);
            } else {
                log.append("-->Open command cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
        //选择解压缩目录
        } else if (e.getSource() == saveButton) {
        	this.dst = "";
        	fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = fc.showOpenDialog(UnZipDemo.this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
            	File file = fc.getSelectedFile();
            	this.dst = file.getAbsolutePath();
            	log.append("-->UnZip Directory: " + this.dst + newline);
            }
        }else if(e.getSource() == startButton) {
        	UnZipFile unzip = new UnZipFile(this.src,this.dst);
        	unzip.startDecompress();
        	String ss = unzip.getPath();
        	log.append(ss+newline);
        }
    }


    /*
     * Model Auto generate
     */
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("UnZipDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new UnZipDemo());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    /*
     * Model Auto generate
     */
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE); 
                createAndShowGUI();
            }
        });
    }
}

