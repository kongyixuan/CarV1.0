package com.ftp.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.junit.Test;

public class FtpDownLoadUtil {
	/**
	 * * �����ļ� *
	 * 
	 * @param hostname
	 *            FTP��������ַ *
	 * @param port
	 *            FTP�������˿ں� *
	 * @param username
	 *            FTP��¼�ʺ� *
	 * @param password
	 *            FTP��¼���� *
	 * @param pathname
	 *            FTP�������ļ�Ŀ¼ *
	 * @param filename
	 *            �ļ����� *
	 * @param localpath
	 *            ���غ���ļ�·�� *
	 * @return
	 */
	public static boolean downloadFile(String hostname, int port,
			String username, String password, String pathname, String filename,
			String localpath) {
		boolean flag = false;
		FTPClient ftpClient = new FTPClient();
		try {
			// ����FTP������
			ftpClient.connect(hostname, port);
			// ��¼FTP������
			ftpClient.login(username, password);
			// ��֤FTP�������Ƿ��¼�ɹ�
			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				return flag;
			}
			// �л�FTPĿ¼
			ftpClient.changeWorkingDirectory(pathname);
			FTPFile[] ftpFiles = ftpClient.listFiles();
			for (FTPFile file : ftpFiles) {
				if (filename.equalsIgnoreCase(file.getName())) {
					File localFile = new File(localpath + "/" + file.getName());
					OutputStream os = new FileOutputStream(localFile);
					ftpClient.retrieveFile(file.getName(), os);
					os.close();
				}
			}
			ftpClient.logout();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.logout();
				} catch (IOException e) {

				}
			}
		}
		return flag;
	}

	@Test
	public void testDownLoad() {
		//��Ҫjakarta-oro-2.0.8.jar
		String hostname = "192.168.91.181";
		int port = 21;
		String username = "ftpadmin";
		String password = "123456";
		String pathname = "/picture";
		String filename = "timg.jpg";
		String localpath = "D://test";
		downloadFile(hostname, port, username, password, pathname, filename,
				localpath);

	}
}
