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
	* 上传文件
	* @param hostname ftp服务器地址
	* @param port ftp服务器端口号
	* @param username ftp登录账号
	* @param password ftp登录密码
	* @param pathname ftp服务保存地址
	* @param fileName 上传到ftp的文件名
	* @param inputStream 输入文件流
	* @return
	*/
	public static boolean uploadFile(String hostname, int port, String username, 
	String password, String pathname, String fileName, InputStream inputStream){
	boolean flag = false;
	FTPClient ftpClient = new FTPClient();
	ftpClient.setControlEncoding("utf-8");
	try{
	ftpClient.connect(hostname, port); //连接ftp服务器
	ftpClient.login(username, password); //登录ftp服务器
	int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
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
	/** * 上传文件（可对文件进行重命名） * 
	* @param hostname FTP服务器地址 * 
	* @param port FTP服务器端口号 * 
	* @param username FTP登录帐号 * 
	* @param password FTP登录密码 * 
	* @param pathname FTP服务器保存目录 * 
	* @param filename 上传到FTP服务器后的文件名称 * 
	* @param originfilename 待上传文件的名称（绝对地址） * 
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
	/** 上传文件（不可以进行文件的重命名操作） *
	* @param hostname FTP服务器地址 *
	* @param port FTP服务器端口号 *
	* @param username FTP登录帐号 *
	* @param password FTP登录密码 * 
	* @param pathname FTP服务器保存目录 * 
	* @param originfilename 待上传文件的名称（绝对地址） * 
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
