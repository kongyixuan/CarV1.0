package com.baidu.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class BaiduMap {
	 /** 
	    * @param addr 
	    * 查询的地址 
	    * @return 
	    * @throws IOException 
	    */ 
	    public Object[] getCoordinate(String addr) throws IOException { 
	        String lng = null;//经度
	        String lat = null;//纬度
	        String address = null; 
	        try { 
	            address = java.net.URLEncoder.encode(addr, "UTF-8"); 
	        }catch (UnsupportedEncodingException e1) { 
	            e1.printStackTrace(); 
	        } 
	        String key = "LLKNzuuiENqHlx5BS9QrP2evwcFr2CY1";
	        //调用以下路径根据经纬度获取地址，返回数据为JSON格式
	       // http://api.map.baidu.com/geocoder/v2/?callback=renderReverse&location=39.983424,116.322987&output=json&pois=1&ak=您的ak，
	        //http://api.map.baidu.com/geocoder/v2/?ak=pmCgmADsAsD9rEXkqWNcTzjd&location=30.548397,104.04701&output=json&pois=1	        
	       //有地址获取经纬度
	        //http://api.map.baidu.com/geocoder/v2/?address=北京市海淀区上地十街10号&output=json&ak=E4805d16520de693a3fe707cdc962045&callback=showLocation
	        String url = String .format("http://api.map.baidu.com/geocoder/v2/?address=%s&output=json&ak=%s", address, key); 
	       
	        //String  url="http://api.map.baidu.com/geocoder/v2/?address=北京市海淀区上地十街10号&output=json&ak=LLKNzuuiENqHlx5BS9QrP2evwcFr2CY1&callback=showLocation";
	        URL myURL = null; 
	        URLConnection httpsConn = null; 
	        try { 
	            myURL = new URL(url); 
	        } catch (MalformedURLException e) { 
	            e.printStackTrace(); 
	        } 
	        InputStreamReader insr = null;
	        BufferedReader br = null;
	        try { 
	            httpsConn = (URLConnection) myURL.openConnection();// 不使用代理 
	            if (httpsConn != null) { 
	                insr = new InputStreamReader( httpsConn.getInputStream(), "UTF-8"); 
	                br = new BufferedReader(insr); 
	                String data = null; //{"status":0,"result":{"location":{"lng":116.30815063007148,"lat":40.056890127931279},"precise":1,"confidence":80,"level":"道路"}}
	                //int count = 1;
	                data= br.readLine();
	                JSONObject jsonObject = JSONObject.parseObject(data);
	                Map<String,String> mappase=new HashMap<String,String>();
	                Map<String,String> map=new HashMap<String,String>();
	                Map<String,String> maplaction=new HashMap<String,String>();
	                for(Map.Entry<String, Object> m:jsonObject.entrySet()){	                	
	                	mappase.put(m.getKey(), m.getValue().toString());
	                }
	                String resultString= mappase.get("result");
	                if(resultString!=null&&resultString.length()>0){
	                	for(Map.Entry<String, Object> aEntry:JSONObject.parseObject(resultString).entrySet()){
	                		map.put(aEntry.getKey(), aEntry.getValue().toString());
	                	}
	                }
	               String lactionString= map.get("location");
	                if(lactionString!=null&&lactionString.length()>0){
	                	for(Map.Entry<String, Object> aEntry:JSONObject.parseObject(lactionString).entrySet()){
	                		maplaction.put(aEntry.getKey(), aEntry.getValue().toString());
	        
	                	}
	                }
	               lng =maplaction.get("lng");
	               lat =maplaction.get("lat");
	              /*  while(data!=null){ 
	                    if(count==5){
	                        lng = (String)data.subSequence(data.indexOf(":")+1, data.indexOf(","));//经度
	                        count++;
	                    }else if(count==6){
	                        lat = data.substring(data.indexOf(":")+1);//纬度
	                        count++;
	                    }else{
	                        count++;
	                    }
	                } */
	            } 
	        } catch (IOException e) { 
	            e.printStackTrace(); 
	        } finally {
	            if(insr!=null){
	                insr.close();
	            }
	            if(br!=null){
	                br.close();
	            }
	        }
	        return new Object[]{lng,lat}; 
	    } 
	 
	 
	    public static void main(String[] args) throws IOException {
	    	BaiduMap getLatAndLngByBaidu = new BaiduMap();
	        Object[] o = getLatAndLngByBaidu.getCoordinate("成都市天府四街");
	        System.out.println(o[0]);//经度
	        System.out.println(o[1]);//纬度
	    }
	    @Test
	 public void testMap(){
	    	BaiduMap getLatAndLngByBaidu = new BaiduMap();
	        Object[] o;
			try {
				o = getLatAndLngByBaidu.getCoordinate("北京市海淀区上地十街10号");
			    System.out.println(o[0]);//经度
		        System.out.println(o[1]);//纬度
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    
	 }
	}

