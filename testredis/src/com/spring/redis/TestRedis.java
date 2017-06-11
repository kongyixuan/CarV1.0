package com.spring.redis;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import org.junit.Test;
//jedis-2.1.0.jar和commons-pool-1.5.4.jar
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
public class TestRedis {
	  public static void main(String[] args) {
		  JedisPoolConfig config = new JedisPoolConfig();  
          //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；  
          //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。  
          config.setMaxActive(500);  
          //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。  
          config.setMaxIdle(5);  
          //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；  
          config.setMaxWait(1000 * 100);  
          //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；  
          config.setTestOnBorrow(true); 
	        JedisPool jedisPool=new JedisPool(config,"127.0.0.1",6379,3000);
	        JedisTemple jedisTemple = new JedisTemple(jedisPool);
	        RedisCacheClientImpl client = new RedisCacheClientImpl(jedisTemple);
	        TestBean dict = new TestBean();
	        dict.setName("qwer");
	        dict.setValue("郑州");
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
	            //读取属性文件a.properties
	        	InputStream inputStream  = this.getClass().getClassLoader().getResourceAsStream("redis.properties"); 
	            InputStream in = new BufferedInputStream (inputStream/*new FileInputStream("classpath:redis.properties")*/);
	            prop.load(in);     ///加载属性列表
	            Iterator<String> it=prop.stringPropertyNames().iterator();
	            while(it.hasNext()){
	                String key=it.next();
	                System.out.println(key+":"+prop.getProperty(key));
	            }
	            in.close();
	            
	          /*  ///保存属性到b.properties文件
	            FileOutputStream oFile = new FileOutputStream("b.properties", true);//true表示追加打开
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
			  ///保存属性到b.properties文件
			  FileOutputStream oFile = new FileOutputStream("b.properties", true);//true表示追加打开
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
	/*读取src下面的配置properties信息*/
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
