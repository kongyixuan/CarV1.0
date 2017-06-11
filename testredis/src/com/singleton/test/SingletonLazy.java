package com.singleton.test;

public class SingletonLazy {
private static SingletonLazy singletonLazy; 
/*将构造方法私有化，提供变量的 get共有方法。*/
private  SingletonLazy() {
}
/*懒汉式，线程不安全*/
public static SingletonLazy getSingletonLazy() {
	if(singletonLazy==null){
		singletonLazy=new SingletonLazy();
	}
	return singletonLazy;
}
//懒加载线程安全的
/*
 * 
 * 线程安全，并且解决了多实例的问题，但是它并不高效。
 * 因为在任何时候只能有一个线程调用getSingletonLazySingletonLazy() 方法。但是同步操作只需要在第一次调用时才被需要，*/
public static synchronized SingletonLazy getSingletonLazySingletonLazy() {
	if(singletonLazy==null){
		singletonLazy=new SingletonLazy();
	}
	return singletonLazy;
}
/*终极
 * 给 instance 分配内存
调用 Singleton 的构造函数来初始化成员变量
将instance对象指向分配的内存空间（执行完这步 instance 就为非 null 了）
但是在 JVM 的即时编译器中存在指令重排序的优化。也就是说上面的第二步和第三步的顺序是不能保证的，最终的执行顺序可能是 1-2-3 也可能是 1-3-2。如果是后者，则在 3 执行完毕、2 未执行之前，被线程二抢占了，这时 instance 已经是非 null 了（但却没有初始化），所以线程二会直接返回 instance，然后使用，然后顺理成章地报错。

我们只需要将 instance 变量声明成 volatile 就可以了 */
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
