package com.io.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;


public class ReadAndWriteLog {
private long lasttimelog;
/*ScheduledExecutorService执行定时任务
 * 使用Java内部的定时任务接口 使用线程进行文件的读取。
 * 
 * 
 * */

public String  readTimeLog(File fileLog) throws Exception {
	/*指定文件可读可写*/
String result = null;
final RandomAccessFile randomAccessFile=new RandomAccessFile(fileLog, "rw");
ScheduledExecutorService scheduledExecutorService=Executors.newScheduledThreadPool(1);

Future<String> future=scheduledExecutorService.submit(new Callable<String>() {
	@Override
	public String call() throws Exception {
		try {
			//randomAccessFile.seek(lasttimelog);
	       StringBuffer sBuffer=new StringBuffer();
			String tempString="";
			while (null!=(tempString=randomAccessFile.readLine())) {
				sBuffer.append(tempString.getBytes("UTF-8"));			
			}
			System.out.print(sBuffer.toString());
			
			randomAccessFile.close();
			return sBuffer.toString();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
});
if(null!=future){
	result=future.toString();
}
/*线程每隔 十秒读取新增log
 * 第一个参数是执行任务
 * 第二个参数是每隔多久执行一次
 * 第三个参数是单位
 * 
 * 
 * */
ScheduledFuture<String> scf= scheduledExecutorService.schedule(new Callable<String>() {
@Override
public String call() throws Exception {
	try {
		randomAccessFile.seek(lasttimelog);
       StringBuffer sBuffer=new StringBuffer();
		String tempString="";
		while (null!=(tempString=randomAccessFile.readLine())) {
			sBuffer.append(tempString.getBytes("UTF-8"));			
		}
		//System.out.print(sBuffer.toString());
		
		randomAccessFile.close();
		return sBuffer.toString();
	}catch (Exception e) {
		// TODO: handle exception
	}
	return null;
}
}, 10,TimeUnit.SECONDS);
if(null!=scf){
	if(null==result){
	result=scf.toString();	
	}
	
}
/*scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
	
	@Override
	public void run() {
		try {
			randomAccessFile.seek(lasttimelog);
	       StringBuffer sBuffer=new StringBuffer();
			String tempString="";
			while (null!=(tempString=randomAccessFile.readLine())) {
				sBuffer.append(tempString.getBytes("UTF-8"));			
			}
			System.out.print(sBuffer.toString());
		
			randomAccessFile.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}, 0,10,TimeUnit.SECONDS);*/
return result;
}
/*实时写入*/
	public void  writeTimeLog(String path) {
		final File file=new File(path);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ScheduledExecutorService scheduledExecutorService=Executors.newScheduledThreadPool(1);
		scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {			
			@Override
			public void run() {
				try {
					if(null!=file){
					Writer writer=new FileWriter(file,true);
					writer.write("测试");
					writer.flush();
					writer.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
			
				}
				
			}
		}, 0,2,TimeUnit.SECONDS);
	}
	@Test
	public void  testIO() {
		String pathname="D:\\Tomcat7.0\\logs\\localhost_access_log.2017-04-20.txt";
		final File file =new File(pathname);
		ReadAndWriteLog readAndWriteLog=new ReadAndWriteLog();
		try {
			String readString=readAndWriteLog.readTimeLog(file);
			readAndWriteLog.writeTimeLog(pathname);
			System.out.print(readString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
