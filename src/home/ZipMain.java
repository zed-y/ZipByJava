package home;

import Zip.ZipFile;
import Zip.UnZipFile;

public class ZipMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * ѹ���ļ�
		 */
//		ZipFile test = new ZipFile("E:\\test","E:\\test.zip");
//		test.startCompress();
//		String s = test.getPath();
//		System.out.println("-------\n"+s);
		/*
		 * ��ѹ���ļ�
		 */
		UnZipFile t2 = new UnZipFile("E:\\Pic.zip","E:\\testout");
		t2.startDecompress();
		String s = t2.getPath();
		System.out.println("-----\n"+s);
	}

}
