package com.elkdeals.mobile.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import id.zelory.compressor.Compressor;

/**
 * Created by Mo'men Zakaria Zowayed on 8/18/2016.
 */
public class BitmapUtils {


	/**
	 * Store a bitmap to file.
	 *
	 * @param bitmap            The bitmap to store
	 * @param format            The image format
	 * @param quality           The image quality
	 * @param pathOfOutputImage Path to store to
	 */
	public static void storeBitmap(final Bitmap bitmap, final Bitmap.CompressFormat format, final int quality, final String pathOfOutputImage) {
		try {
			FileOutputStream out = new FileOutputStream(pathOfOutputImage);
			BufferedOutputStream bos = new BufferedOutputStream(out);
			bitmap.compress(format, quality, bos);
			bos.flush();
			bos.close();
		} catch (IOException e) {
			Log.e("ImageHelper.storeBitmap", e.getLocalizedMessage());
		}
	}

	public static File compressImage2File(Context context, File actualImage) {
        File compressedFile=null;
		Compressor compressedImageFile = new Compressor(context);
        try {
            compressedFile= compressedImageFile.compressToFile(actualImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

		if (compressedFile == null) {
			return actualImage;
		}
		return compressedFile;
	}

	public static String encodeTo64(final File file) {

		InputStream inputStream = null; //You can get an inputStream using any IO API
		String encodedString = null;
		try {
			inputStream = new FileInputStream(file);
			byte[] bytes;
			byte[] buffer = new byte[8192];
			int bytesRead;
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			try {
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					output.write(buffer, 0, bytesRead);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			bytes = output.toByteArray();
			encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);
			BitmapUtils.setEncodedString(encodedString);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return encodedString;
	}

	public static byte[] encodeTo64Byte(final File file) {

		InputStream inputStream = null; //You can get an inputStream using any IO API
		byte[] encodedString = new byte[0];
		try {
			inputStream = new FileInputStream(file);
			byte[] bytes;
			byte[] buffer = new byte[8192];
			int bytesRead;
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			try {
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					output.write(buffer, 0, bytesRead);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			bytes = output.toByteArray();
			encodedString = Base64.encode(bytes, Base64.DEFAULT);
			BitmapUtils.setEncodedByte(encodedString);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return encodedString;
	}

	private static void setEncodedString(String encodedString) {
		encodedString = encodedString;
	}
	private static void setEncodedByte(byte[] encodedString) {
		encodedString = encodedString;
	}

	public static byte[] readAll(File file){
		RandomAccessFile f = null;
		byte[] b = new byte[0];
		try {
			f = new RandomAccessFile(file, "r");
			b= new byte[(int)f.length()];
			f.readFully(b);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return b;
	}
	public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// CREATE A MATRIX FOR THE MANIPULATION
		Matrix matrix = new Matrix();
		// RESIZE THE BIT MAP
		matrix.postScale(scaleWidth, scaleHeight);

		// "RECREATE" THE NEW BITMAP
		Bitmap resizedBitmap = Bitmap.createBitmap(
				bm, 0, 0, width, height, matrix, false);
		bm.recycle();
		return resizedBitmap;
	}
}




