package com.singleton.test;

public class SingletonLazy {
private static SingletonLazy singletonLazy; 
/*�����췽��˽�л����ṩ������ get���з�����*/
private  SingletonLazy() {
}
/*����ʽ���̲߳���ȫ*/
public static SingletonLazy getSingletonLazy() {
	if(singletonLazy==null){
		singletonLazy=new SingletonLazy();
	}
	return singletonLazy;
}
//�������̰߳�ȫ��
/*
 * 
 * �̰߳�ȫ�����ҽ���˶�ʵ�������⣬������������Ч��
 * ��Ϊ���κ�ʱ��ֻ����һ���̵߳���getSingletonLazySingletonLazy() ����������ͬ������ֻ��Ҫ�ڵ�һ�ε���ʱ�ű���Ҫ��*/
public static synchronized SingletonLazy getSingletonLazySingletonLazy() {
	if(singletonLazy==null){
		singletonLazy=new SingletonLazy();
	}
	return singletonLazy;
}
/*�ռ�
 * �� instance �����ڴ�
���� Singleton �Ĺ��캯������ʼ����Ա����
��instance����ָ�������ڴ�ռ䣨ִ�����ⲽ instance ��Ϊ�� null �ˣ�
������ JVM �ļ�ʱ�������д���ָ����������Ż���Ҳ����˵����ĵڶ����͵�������˳���ǲ��ܱ�֤�ģ����յ�ִ��˳������� 1-2-3 Ҳ������ 1-3-2������Ǻ��ߣ����� 3 ִ����ϡ�2 δִ��֮ǰ�����̶߳���ռ�ˣ���ʱ instance �Ѿ��Ƿ� null �ˣ���ȴû�г�ʼ�����������̶߳���ֱ�ӷ��� instance��Ȼ��ʹ�ã�Ȼ��˳����µر���

����ֻ��Ҫ�� instance ���������� volatile �Ϳ����� */
private volatile static SingletonLazy singletonLazyVolatile;
public static  SingletonLazy getLazy(){
	if(singletonLazyVolatile==null){
		synchronized (SingletonLazy.class) {
			if(singletonLazyVolatile==null){
				singletonLazyVolatile=new SingletonLazy();
			}
		}
	}
	
	return singletonLazyVolatile;
	
}
}
