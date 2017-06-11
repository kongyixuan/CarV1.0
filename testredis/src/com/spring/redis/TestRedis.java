package com.spring.redis;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import org.junit.Test;
//jedis-2.1.0.jar��commons-pool-1.5.4.jar
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
public class TestRedis {
	  public static void main(String[] args) {
		  JedisPoolConfig config = new JedisPoolConfig();  
          //����һ��pool�ɷ�����ٸ�jedisʵ����ͨ��pool.getResource()����ȡ��  
          //�����ֵΪ-1�����ʾ�����ƣ����pool�Ѿ�������maxActive��jedisʵ�������ʱpool��״̬Ϊexhausted(�ľ�)��  
          config.setMaxActive(500);  
          //����һ��pool����ж��ٸ�״̬Ϊidle(���е�)��jedisʵ����  
          config.setMaxIdle(5);  
          //��ʾ��borrow(����)һ��jedisʵ��ʱ�����ĵȴ�ʱ�䣬��������ȴ�ʱ�䣬��ֱ���׳�JedisConnectionException��  
          config.setMaxWait(1000 * 100);  
          //��borrowһ��jedisʵ��ʱ���Ƿ���ǰ����validate���������Ϊtrue����õ���jedisʵ�����ǿ��õģ�  
          config.setTestOnBorrow(true); 
	        JedisPool jedisPool=new JedisPool(config,"127.0.0.1",6379,3000);
	        JedisTemple jedisTemple = new JedisTemple(jedisPool);
	        RedisCacheClientImpl client = new RedisCacheClientImpl(jedisTemple);
	        TestBean dict = new TestBean();
	        dict.setName("qwer");
	        dict.setValue("֣��");
	        client.add("xufei", dict);
	        TestBean dict2 = (TestBean) client.get("xufei");
	        System.out.println(dict2.getName());
	        System.out.println(dict.getName());
	        System.out.println((TestBean)client.get("xufei"));
	        System.out.println(dict.equals(dict2));
	    }
	
	  @Test
	  public void PropertyTest(){
		  Properties prop = new Properties();     
	        try{
	            //��ȡ�����ļ�a.properties
	        	InputStream inputStream  = this.getClass().getClassLoader().getResourceAsStream("redis.properties"); 
	            InputStream in = new BufferedInputStream (inputStream/*new FileInputStream("classpath:redis.properties")*/);
	            prop.load(in);     ///���������б�
	            Iterator<String> it=prop.stringPropertyNames().iterator();
	            while(it.hasNext()){
	                String key=it.next();
	                System.out.println(key+":"+prop.getProperty(key));
	            }
	            in.close();
	            
	          /*  ///�������Ե�b.properties�ļ�
	            FileOutputStream oFile = new FileOutputStream("b.properties", true);//true��ʾ׷�Ӵ�
	            prop.setProperty("phone", "10086");
	            prop.store(oFile, "The New properties file");
	            oFile.close();*/
	        }
	        catch(Exception e){
	            System.out.println(e);
	        }
	    } 
	  
	  public void writeProperty(){
		  Properties prop = new Properties();
		  try{
			  ///�������Ե�b.properties�ļ�
			  FileOutputStream oFile = new FileOutputStream("b.properties", true);//true��ʾ׷�Ӵ�
	          prop.setProperty("phone", "10086");
	          prop.store(oFile, "The New properties file");
	          oFile.close();
		  }catch(Exception e){
			  
		  } 
	  }
	    private String driverName;
		private String dbURL;
		private String userPwd;
		private String userName;
		private String times;
		private String url;
	/*��ȡsrc���������properties��Ϣ*/
	public void readSrc(){
	  try{
		  Properties prop = new Properties();
		  //FileInputStream fileInputStream = new FileInputStream(new File(path()+"/Config.properties"));
		  //InputStream inputStream=this.getClass().getResourceAsStream("/com/haiqi/config/Config.properties");
		      	InputStream inputStream  = this.getClass().getClassLoader().getResourceAsStream("Config.properties"); 
		  prop.load(inputStream);
		      	this.driverName = prop.getProperty("driverName","");
		      	this.dbURL = prop.getProperty("dbURL","");
		      	this.userName = prop.getProperty("userName","");
		      	this.userPwd = prop.getProperty("userPwd","");
		      	this.times = prop.getProperty("times", "");
		      	this.url = prop.getProperty("url","");
		  }catch (Exception e) {
		  e.printStackTrace();
		  }
	  }
}
