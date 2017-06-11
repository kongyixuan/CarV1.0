package com.image.base64;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ImageBase64 {
	//ͼƬת����Base64�ַ���
	public static String imageToBase64(String path) {// ��ͼƬ�ļ�ת��Ϊ�ֽ������ַ��������������Base64���봦��
	    byte[] data = null;
	    // ��ȡͼƬ�ֽ�����
	    try {
	        InputStream in = new FileInputStream(path);
	        data = new byte[in.available()];
	        in.read(data);
	        in.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    // ���ֽ�����Base64����
	    BASE64Encoder encoder = new BASE64Encoder();
	    return encoder.encode(data);// ����Base64��������ֽ������ַ���
	}

	/**
	 * @Descriptionmap ���ֽ������ַ�������Base64���벢����ͼƬ
	 * @author temdy
	 * @Date 2015-01-26
	 * @param base64 ͼƬBase64����
	 * @param path ͼƬ·��
	 * @return
	 */
	public static boolean base64ToImage(String base64, String path) {// ���ֽ������ַ�������Base64���벢����ͼƬ
	    if (base64 == null){ // ͼ������Ϊ��
	        return false;
	    }
	    BASE64Decoder decoder = new BASE64Decoder();
	    try {
	        // Base64����
	        byte[] bytes = decoder.decodeBuffer(base64);
	        for (int i = 0; i < bytes.length; ++i) {
	            if (bytes[i] < 0) {// �����쳣����
	                bytes[i] += 256;
	            }
	        }
	        // ����jpegͼƬ
	        OutputStream out = new FileOutputStream(path);
	        out.write(bytes);
	        out.flush();
	        out.close();
	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}
	@Test
	public void testImageBase64(){
		String base64String=imageToBase64("D:/timg.jpg");
		System.out.print(base64String);
		
		boolean isok=base64ToImage(base64String,"F:/testpic/timg.jpg");
		System.out.print(isok);
	}
}
