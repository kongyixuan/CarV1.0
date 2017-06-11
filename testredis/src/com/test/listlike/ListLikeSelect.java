package com.test.listlike;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ListLikeSelect { 
		public List list=new ArrayList();
		 
		//增加员工
		public List addList(String name,int age){
		Employee employee1 = new Employee();
		employee1.setName(name);
		employee1.setAge(age);
		list.add(employee1);
		return list;
		}
		 
		//显示所有员工
		public void ShowList(){
		for(int i=0;i<list.size();i++){
		System.out.println(((Employee)(list.get(i))).getName()+" "+((Employee)(list.get(i))).getAge());
		}
		}
		 
		//模糊查询
		public List likeString(String likename){
		for(int i=0;i<list.size();i++){
		if(((Employee)(list.get(i))).getName().indexOf(likename)<=-1)
		list.remove(i);
		}
		return list;
		 
		}
		@Test
		public void testLike(){
			ListLikeSelect ll=new ListLikeSelect();
			ll.addList("wuxiao",13);
			ll.addList("wangwang",11);
			ll.addList("wanghua",12);
			ll.addList("xiaowang",13);
			ll.addList("xiaoxiao",13);
			ll.list.size();
			List lis=ll.likeString("wang");
			lis.size();
			
			ll.ShowList();
		}
	}		 
