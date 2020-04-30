package modle;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * ѧϰʹ��java.util.zipѹ���ļ������ļ���
 * 
 * @author lhm
 *
 */

public class Zip {

	/**
	 * @param args ������
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// ��һ����������Ҫѹ����Դ·�����ڶ���������ѹ���ļ���Ŀ��·���������Ҫ��ѹ�����ļ����ּ���ȥ
		compress("E:\\test", "E:\\test1.zip");
		System.out.println("�ļ�ѹ����ϣ�");
	}

	/**
	 * s ѹ���ļ�
	 * 
	 * @param srcFilePath  ѹ��Դ·��
	 * @param destFilePath ѹ��Ŀ��·��
	 */
	public static void compress(String srcFilePath, String destFilePath) {
		//
		File src = new File(srcFilePath);

		if (!src.exists()) {
			throw new RuntimeException(srcFilePath + "������");
		}
		File zipFile = new File(destFilePath);

		try {

			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);
			String baseDir = "";
			compressbyType(src, zos, baseDir);
			zos.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	/**
	 * ����ԭ·�������;���ѹ�����ļ�·��ֱ�Ӱ��ļ�ѹ����
	 * 
	 * @param src
	 * @param zos
	 * @param baseDir
	 */
	private static void compressbyType(File src, ZipOutputStream zos, String baseDir) {

		if (!src.exists())
			return;
		System.out.println("baseDir:"+baseDir);
		System.out.println("ѹ��·��" + baseDir + src.getName());
		// �ж��ļ��Ƿ����ļ���������ļ�����compressFile����,�����·���������compressDir������
		if (src.isFile()) {
			// src���ļ������ô˷���
			compressFile(src, zos, baseDir);

		} else if (src.isDirectory()) {
			// src���ļ��У����ô˷���
			compressDir(src, zos, baseDir);

		}

	}

	/**
	 * ѹ���ļ�
	 */
	private static void compressFile(File file, ZipOutputStream zos, String baseDir) {
		if (!file.exists())
			return;
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			ZipEntry entry = new ZipEntry(baseDir + file.getName());
			zos.putNextEntry(entry);
			int count;
			byte[] buf = new byte[1024];
			while ((count = bis.read(buf)) != -1) {
				zos.write(buf, 0, count);
			}
			bis.close();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error!");
		}
	}

	/**
	 * ѹ���ļ���
	 */
	private static void compressDir(File dir, ZipOutputStream zos, String baseDir) {
		if (!dir.exists())
			return;
		File[] files = dir.listFiles();
		if (files.length == 0) {
			try {
				zos.putNextEntry(new ZipEntry(baseDir + dir.getName() + File.separator));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (File file : files) {
			compressbyType(file, zos, baseDir + dir.getName() + File.separator);
		}
	}
}
