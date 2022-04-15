package com.kessi.photovideomaker.activities.swap;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class EditImageUtil {
	public static final String FOLDER_NAME = "kessieditor";


	public static File createFolders() {
//		File baseDir;
//		if (android.os.Build.VERSION.SDK_INT < 8) {
//			baseDir = Environment.getExternalStorageDirectory();
//		} else {
//			baseDir = Environment
//					.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//		}
//		if (baseDir == null)
//			return Environment.getExternalStorageDirectory();
		File aviaryFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), FOLDER_NAME);
		if (aviaryFolder.exists())
			return aviaryFolder;
		if (aviaryFolder.isFile())
			aviaryFolder.delete();
		if (aviaryFolder.mkdirs())
			return aviaryFolder;
		return Environment.getExternalStorageDirectory();
	}

	public static File genEditFile(){
//		return EditImageUtil.getEmptyFile("temp.png");
		return EditImageUtil.getEmptyFile("temp"
				+ System.currentTimeMillis() + ".png");
	}

	public static File getEmptyFile(String name) {
		File folder = EditImageUtil.createFolders();
		if (folder != null) {
			if (folder.exists()) {
				File file = new File(folder, name);
				return file;
			}
		}
		return null;
	}

	public static boolean deleteFileNoThrow(String path) {
		File file;
		try {
			file = new File(path);
		} catch (NullPointerException e) {
			return false;
		}

		if (file.exists()) {
			return file.delete();
		}
		return false;
	}


	public static String saveBitmap(String bitName, Bitmap mBitmap) {
		File baseFolder = createFolders();
		File f = new File(baseFolder.getAbsolutePath(), bitName);
		FileOutputStream fOut = null;
		try {
			f.createNewFile();
			fOut = new FileOutputStream(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f.getAbsolutePath();
	}

	public static long getFolderSize(File file) throws Exception {
		long size = 0;
		try {
			File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].isDirectory()) {
					size = size + getFolderSize(fileList[i]);
				} else {
					size = size + fileList[i].length();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	/** *  * * @param size * @return */
	public static String getFormatSize(double size) {
		double kiloByte = size / 1024d;
		int megaByte = (int) (kiloByte / 1024d);
		return megaByte + "MB";
	}

}// end
