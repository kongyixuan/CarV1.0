package com.neusoft.test;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSessionFactory {

	@Test
	public  void  testGet(){
		BeanFactory beanFactory=new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
		beanFactory.getBean("dataSource");
		System.out.print(beanFactory.getBean("dataSource"));
		beanFactory.getBean("sessionFactory");
		System.out.print(beanFactory.getBean("sessionFactory"));
	}
}
