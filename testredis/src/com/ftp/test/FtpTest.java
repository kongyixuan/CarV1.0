package com.ftp.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.junit.Test;

public class FtpTest {
	/*public static final String hostname="127.0.0.1"; 
	//public static final String hostname="192.0.0.1"; 
	public static final int port=21;
	public static final String username="ftpadmin";
	public static final String password="123456";
	//String pathname="picture";
	public FtpTest(String hostname,int port,String username,String password){
		
		
	}*/
	public FtpTest(){
		
	}
	/**
	* �ϴ��ļ�
	* @param hostname ftp��������ַ
	* @param port ftp�������˿ں�
	* @param username ftp��¼�˺�
	* @param password ftp��¼����
	* @param pathname ftp���񱣴��ַ
	* @param fileName �ϴ���ftp���ļ���
	* @param inputStream �����ļ���
	* @return
	*/
	public static boolean uploadFile(String hostname, int port, String username, 
	String password, String pathname, String fileName, InputStream inputStream){
	boolean flag = false;
	FTPClient ftpClient = new FTPClient();
	ftpClient.setControlEncoding("utf-8");
	try{
	ftpClient.connect(hostname, port); //����ftp������
	ftpClient.login(username, password); //��¼ftp������
	int replyCode = ftpClient.getReplyCode(); //�Ƿ�ɹ���¼������
	if(!FTPReply.isPositiveCompletion(replyCode)){
	return flag;
	}
	ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
	ftpClient.makeDirectory(pathname);
	ftpClient.changeWorkingDirectory(pathname);
	ftpClient.storeFile(fileName, inputStream);
	inputStream.close();
	ftpClient.logout();
	flag = true;
	}catch (Exception e) {
	e.printStackTrace();
	}finally{
	if(ftpClient.isConnected()){
	try{
	ftpClient.disconnect();
	}catch(IOException e){
	e.printStackTrace();
	}
	}

	}
	return true;
	}
	/** * �ϴ��ļ����ɶ��ļ������������� * 
	* @param hostname FTP��������ַ * 
	* @param port FTP�������˿ں� * 
	* @param username FTP��¼�ʺ� * 
	* @param password FTP��¼���� * 
	* @param pathname FTP����������Ŀ¼ * 
	* @param filename �ϴ���FTP����������ļ����� * 
	* @param originfilename ���ϴ��ļ������ƣ����Ե�ַ�� * 
	* @return */ 
	public static boolean uploadFileFromProduction(String hostname, int port, String username, 
	String password, String pathname, String filename, String originfilename){ 
	boolean flag = false; 
	try { 
	InputStream inputStream = new FileInputStream(new File(originfilename)); 
	flag = uploadFile(hostname, port, username, password, pathname, filename, inputStream); 
	} catch (Exception e) { 
	e.printStackTrace(); 
	} 
	return flag; 
	} 
	/** �ϴ��ļ��������Խ����ļ��������������� *
	* @param hostname FTP��������ַ *
	* @param port FTP�������˿ں� *
	* @param username FTP��¼�ʺ� *
	* @param password FTP��¼���� * 
	* @param pathname FTP����������Ŀ¼ * 
	* @param originfilename ���ϴ��ļ������ƣ����Ե�ַ�� * 
	* @return */ 
	public static boolean uploadFileFromProduction(String hostname, int port, String username,
	String password, String pathname, String originfilename){ 
	boolean flag = false; 
	try { 
	String fileName = new File(originfilename).getName(); 
	InputStream inputStream = new FileInputStream(new File(originfilename)); 
	flag = uploadFile(hostname, port, username, password, pathname, fileName, inputStream); 
	} catch (Exception e) { 
	e.printStackTrace(); 
	} 
	return flag; 
	}
	@Test
	public void ftpTestUp(){
		String hostname="127.0.0.1"; 
		int port=21;
		String username="ftpadmin";
		String password="123456";
		String pathname="picture";
		String originfilename="D://timg.jpg";
		uploadFileFromProduction(hostname,port,username,password,pathname,originfilename);
		
	}
}
