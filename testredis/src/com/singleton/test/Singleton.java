package com.singleton.test;

public class Singleton {
/*静态内部类实现饿汉式的懒加载*/
	private static class SingletonHolder{
		private static final Singleton singleton=new Singleton();
	}
	private Singleton(){
		
	}
	public static final Singleton getSingleton(){
		return SingletonHolder.singleton;
		
	}
}
