package com.test.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class MapListTest {

public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("Hello");
		list.add("World");
		list.add("HAHAHAHA");

		// ��һ�ֱ�������ʹ��foreach����List
		for (String str : list) { // Ҳ���Ը�дfor(int i=0;i<list.size();i++)������ʽ
			System.out.println(str);
		}

		// �ڶ��ֱ������������Ϊ������ص����ݽ��б���
		String[] strArray = new String[list.size()];
		list.toArray(strArray);
		for (int i = 0; i < strArray.length; i++) // ����Ҳ���Ը�дΪ foreach(String
													// str:strArray)������ʽ
		{
			System.out.println(strArray[i]);
		}

		// �����ֱ��� ʹ�õ�����������ر���
		Iterator<String> ite = list.iterator();
		while (ite.hasNext())// �ж���һ��Ԫ��֮����ֵ
		{
			System.out.println(ite.next());
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "value1");
		map.put("2", "value2");
		map.put("3", "value3");
		// ��һ�֣��ձ�ʹ�ã�����ȡֵ
		System.out.println("ͨ��Map.keySet����key��value��");
		for (String key : map.keySet()) {
			System.out.println("key= " + key + " and value= " + map.get(key));
		}

		// �ڶ���
		System.out.println("ͨ��Map.entrySetʹ��iterator����key��value��");
		Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			System.out.println("key= " + entry.getKey() + " and value= "
					+ entry.getValue());
		}
		// �����֣��Ƽ���������������ʱ
		System.out.println("ͨ��Map.entrySet����key��value");
		for (Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println("key= " + entry.getKey() + " and value= "
					+ entry.getValue());
		}
		// ������
		System.out.println("ͨ��Map.values()�������е�value�������ܱ���key");
		for (String v : map.values()) {
			System.out.println("value= " + v);
		}
	}
@Test
public void TestListMap(){
	Map<String, String> map = new HashMap<String, String>();
	map.put("1", "value1");
	map.put("2", "value2");
	map.put("3", "value3");
	List<Map<String,String>> listMap=new ArrayList<Map<String,String>>();
	listMap.add(map);
	for(Map<String, String>  listMapValue:listMap){
		for(String mapKey:listMapValue.keySet()){
			System.out.println(mapKey);
		}
		for(String mapValue:listMapValue.values()){
			System.out.println(mapValue);
		}
	}
}
@Test
 public void TestIsEmty(){
     String s="";
     boolean isEmty=false;
	 if(s == null || s.length() <= 0){
		 isEmty=true; 
		 System.out.println(isEmty);
	}
}
}
