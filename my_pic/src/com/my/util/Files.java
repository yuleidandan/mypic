package com.my.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.util.ByteArrayBuffer;

import com.nostra13.universalimageloader.utils.L;

import android.content.Context;
import android.graphics.Bitmap;

public class Files {

	public static String sd_card = "/sdcard/";
	public static String path = "Android/data/com.my.activity/";
	public static String photo_path = " Image/";

	// 判断sd卡 是否存在
	public static boolean ExistSDCard() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		} else
			return false;
	}

	/**
	 * 删除SD下的图片
	 * 
	 * @param path
	 * @return
	 */
	public static boolean deleteFile(String path) {
		if (Files.ExistSDCard()) {
			File file = new File(path);
			if (file.exists()) {
				file.delete();
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	// 判断是否存在文件
	public static boolean ExistFile(String paths) {
		// String name = MyHash.mixHashStr(url);
		if (Files.ExistSDCard()) {
			File file = new File(paths);
			if (file.exists()) {
				return true;
			}
		}
		return false;
	}

	public static byte[] getFileByte(String paths) throws IOException {
		// String name = MyHash.mixHashStr(url);
		ByteArrayBuffer buffer = null;
		File file = new File(paths);
		if (!file.exists()) {
			return null;
		}
		InputStream inputstream = new FileInputStream(file);
		buffer = new ByteArrayBuffer(1024);
		byte[] tmp = new byte[1024];
		int len;
		while (((len = inputstream.read(tmp)) != -1)) {
			buffer.append(tmp, 0, len);
		}
		inputstream.close();
		return buffer.toByteArray();
	}

	/**
	 * �����ļ���
	 * 
	 * @param context
	 */
	public static void mkdir(Context context) {
		File file;
		file = new File(sd_card + path);
		if (!file.exists()) {
			file.mkdir();
		}
	}

	// 晒单的图片上传文件
	public static void shb_mkdir(Context context) {
		File file;
		file = new File(sd_card + photo_path);
		if (!file.exists()) {
			file.mkdir();
		}
	}

	// 创建目录
	public static void mkdirByPath(String path) {
		File file;
		file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
	}

	/**
	 * ����ͼƬ��SD��
	 * 
	 * @param URL
	 * @param data
	 * @throws IOException
	 */
	public static void saveImage(String URL, byte[] data) throws IOException {
		String name = MyHash.mixHashStr(URL);
		saveData(sd_card + path, name, data);
	}

	/**
	 * ��ȡͼƬ
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static byte[] readImage(String filename) throws IOException {
		String name = MyHash.mixHashStr(filename);
		byte[] tmp = readData(sd_card + path, name);
		return tmp;
	}

	public static byte[] readImgData(String path) throws Exception {
		// String name = MyHash.mixHashStr(url);
		ByteArrayBuffer buffer = null;
		String paths = path;
		File file = new File(paths);
		if (!file.exists()) {
			return null;
		}
		InputStream inputstream = new FileInputStream(file);
		buffer = new ByteArrayBuffer(1024);
		byte[] tmp = new byte[1024];
		int len;
		while (((len = inputstream.read(tmp)) != -1)) {
			buffer.append(tmp, 0, len);
		}
		inputstream.close();
		return buffer.toByteArray();
	}

	/**
	 * ��ȡͼƬ����
	 * 
	 * @param path
	 * @param name
	 * @return
	 * @throws IOException
	 */
	private static byte[] readData(String path, String name) throws IOException {
		// String name = MyHash.mixHashStr(url);
		ByteArrayBuffer buffer = null;
		String paths = path + name;
		File file = new File(paths);
		if (!file.exists()) {
			return null;
		}
		InputStream inputstream = new FileInputStream(file);
		buffer = new ByteArrayBuffer(1024);
		byte[] tmp = new byte[1024];
		int len;
		while (((len = inputstream.read(tmp)) != -1)) {
			buffer.append(tmp, 0, len);
		}
		inputstream.close();
		return buffer.toByteArray();
	}

	/**
	 * ͼƬ���湤����
	 * 
	 * @param path
	 * @param fileName
	 * @param data
	 * @throws IOException
	 */
	private static void saveData(String path, String fileName, byte[] data)
			throws IOException {
		// String name = MyHash.mixHashStr(AdName);
		File file = new File(path + fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream outStream = new FileOutputStream(file);
		outStream.write(data);
		outStream.close();
	}

	/**
	 * �ж��ļ��Ƿ���� true���� false������
	 * 
	 * @param url
	 * @return
	 */
	public static boolean compare(String url) {
		String name = MyHash.mixHashStr(url);
		String paths = sd_card + path + name;
		File file = new File(paths);
		if (!file.exists()) {
			return false;
		}
		return true;
	}

	public static Boolean saveMyBitmap(String path, Bitmap mBitmap)
			throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;

		}
		return true;
	}
	
	
}
