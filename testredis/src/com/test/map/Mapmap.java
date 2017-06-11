package com.test.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Mapmap {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String,Map<String,String>> map=new HashMap<String,Map<String,String>>();
		Map<String,String> yure=new HashMap<String,String>();
		Map<String,String> jiuye=new HashMap<String,String>();
		map.put("yureban", yure);
		map.put("jiuyeban", jiuye);
		yure.put("01", "zhangsan");
		yure.put("02", "lisi");
		jiuye.put("01"," wangwu");
		jiuye.put("02", "zhaoliu");
		Iterator<String> it=map.keySet().iterator();
		while(it.hasNext()){
		String nameRoom=it.next();
		Map<String,String> nameMap=map.get(nameRoom);
		Iterator<Map.Entry<String, String>> iter=nameMap.entrySet().iterator();
		while(iter.hasNext()){
		Map.Entry<String, String> me=iter.next();
		String id=me.getKey();
		String name=me.getValue();
		System.out.println(nameRoom+"id: "+id+" name:"+name);
		}
		}
	}
  public  void listMap(){
	  Map<String,String> jiuye=new HashMap<String,String>();
	  List<Map<String,String>> listMap=new ArrayList<Map<String,String>>();
  }
}
