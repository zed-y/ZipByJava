package Zip;

import java.util.zip.*;
import java.io.*;

public class UnZipFile {
	private String src = null;
	private String dst = null;
	ZipInputStream zis = null;
	private String path_all = "";
	public UnZipFile(String s1,String s2) {
		this.src = s1;
		this.dst = s2;

	}
	public String getPath() {
		return path_all;
	}
	public void startDecompress() {
		File file = new File(this.src);
		try {
			zis = new ZipInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("初始化失败");
			e.printStackTrace();
		}
		if(!file.exists()) {
			System.out.println(src+" not exists!");
			System.exit(0);
		}
		ZipEntry entry = null;
		File outfile = new File(this.dst);
		if(!outfile.exists()) {
			outfile.mkdirs();
		}
		int c;
		try {
			while((entry = zis.getNextEntry()) != null) {
				//System.out.println("解压缩文件：" + entry.getName());
				String tmp = "解压缩文件：" + entry.getName()+ "\n";
				this.path_all += tmp ;
				outfile = new File(this.dst,entry.getName());
				if(!outfile.exists()) {
					(new File(outfile.getParent())).mkdirs(); 
				}
				FileOutputStream fos = new FileOutputStream(outfile);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				byte[] buff = new byte[1024];
				while((c=zis.read(buff)) != -1) {
					bos.write(buff,0,c);
				}
				bos.close();
				fos.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("解压缩完毕!");
		this.path_all += "解压缩完毕!";
	}
}
