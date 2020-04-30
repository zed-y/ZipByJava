package home;

import java.io.*;
import java.util.zip.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.filechooser.*;

import Zip.ZipFile;

/*
 * FileChooserDemo.java:
 */
public class ZipDemo extends JPanel
                             implements ActionListener {
    static private final String newline = "\n";
    JButton openButton, saveButton,startButton,b;
    JTextArea log;
    JFileChooser fc;
    public String src = "";
    public String dst = "";
    public String filename = "1";
    JFrame jj;
	JLabel location = new JLabel("文件名:");
	JTextField locationText = new JTextField();
	JPanel jp = new JPanel();


    public ZipDemo() {
        super(new BorderLayout());

        //Create the log first, because the action listeners
        log = new JTextArea(10,40);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        log.append("    压缩文件！\n使用说明：先选择要生成的文件名，再选择路径，最终点击Start即可！\n注意：文件名不用加后缀.zip! 默认压缩包名称为'1.zip'\n");
        JScrollPane logScrollPane = new JScrollPane(log);

        //Create a file chooser
        fc = new JFileChooser();
        fc.setCurrentDirectory(new File("E://"));// 文件选择器的初始目录定为E盘

        openButton = new JButton("选 择 路 径");
        openButton.addActionListener(this);

        saveButton = new JButton("输入文件名");
        saveButton.addActionListener(this);
        
        startButton = new JButton("S t a r t");
        startButton.addActionListener(this);
        
        b = new JButton("确 定");
        b.addActionListener(this);
        
        //For layout purposes, put the buttons in a separate panel
        JPanel buttonPanel = new JPanel(); //use FlowLayout
        buttonPanel.add(saveButton);
        buttonPanel.add(openButton);
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
        //Handle open button action.
    	
        if (e.getSource() == openButton) {
        	this.src = "";
        	this.dst = "";
        	fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        	fc.setMultiSelectionEnabled(true);
            int returnVal = fc.showOpenDialog(ZipDemo.this);
            
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File[] files = fc.getSelectedFiles();
                //目的文件不需要输入.zip
                this.dst = files[0].getParent()+File.separator+filename+".zip";
                for(File file : files) {
                	String str = file.getAbsolutePath();
	                this.src += str +"*";
	                log.append("-->Open : "+ str + newline);
                }
            } else {
                log.append("-->Open command cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
        } else if (e.getSource() == saveButton) {
    		jp.add(location);
    		jp.add(locationText);
    		jp.add(b);
    		jj.add(jp);
    		jj.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    		jj.setVisible(true);
        }else if(e.getSource() == b) {
        	filename = locationText.getText();
        	jj.setVisible(false);
        	log.append("-->"+filename+".zip has been generated."+newline);
        }else if(e.getSource() == startButton) {
            ZipFile test = new ZipFile(src,dst);
            test.startCompress();
            String ss = test.getPath();
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
        JFrame frame = new JFrame("ZipDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new ZipDemo());

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

