package com.plansolve;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BalanceTest {

	
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "application-develop.yml" });
		context.start();
	}

}