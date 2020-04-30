package Zip;

import java.io.*;
import java.util.zip.*;

public class ZipFile {
	private String src = null;
	private String dst = null;
	private ZipOutputStream zos = null;
	private String path_all = "";
	
	public ZipFile(String srcFile,String dstFile) {
		this.src = srcFile;
		this.dst = dstFile;

		try {
			this.zos =  new ZipOutputStream(new FileOutputStream(new File(dstFile)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getDst() {
		return dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}
	public String getPath() {
		return path_all;
	}
	/*
	 * 压缩
	 */
	public void startCompress() {
		String[] strs = null;
		if(this.getSrc().contains("*")) {
			strs = this.getSrc().toString().split("\\*");
		for(String str : strs) {
			File file_s = new File(str);
			if(!file_s.exists()) {
				continue;
			}
			String rootDir = "";
			Compress(file_s,rootDir);
		}
		}else {
			File file_s = new File(this.getSrc());
			String rootDir = "";
			Compress(file_s,rootDir);
		}
		try {
			this.zos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("压缩文件结束!");
		this.path_all += "压缩文件结束!";
	}
	/*
	 * 压缩过程
	 */
	private void Compress(File src1,String root) {
		String s_path = "压缩路径:"+ root+src1.getName();
		this.path_all += s_path + "\n";
		//System.out.println(s_path);
		if(src1.isFile()) {
			CompressFile(src1,root);
		}
		if(src1.isDirectory()) {
			CompressDirectory(src1,root);
		}
		
	}
	/*
	 * 压缩文件
	 */
	private void CompressFile(File src1,String root) {
		// TODO Auto-generated method stub
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(src1));
			this.zos.putNextEntry(new ZipEntry(root+src1.getName()));
			int c = 0;
			byte[] buff = new byte[1024];
			while((c = bis.read(buff)) != -1) {
				zos.write(buff, 0, c);
			}
			bis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * 压缩目录
	 */
	private void CompressDirectory(File src1,String root) {
		// TODO Auto-generated method stub
		File[] files = src1.listFiles();
		if(files.length == 0) {
			try {
				this.zos.putNextEntry(new ZipEntry(root+src1.getName()+File.separator));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(File file : files) {
			Compress(file,root+src1.getName()+File.separator);
		}
	}
	


}
