package com.singleton.test;

public class Singleton {
/*��̬�ڲ���ʵ�ֶ���ʽ��������*/
	private static class SingletonHolder{
		private static final Singleton singleton=new Singleton();
	}
	private Singleton(){
		
	}
	public static final Singleton getSingleton(){
		return SingletonHolder.singleton;
		
	}
}
