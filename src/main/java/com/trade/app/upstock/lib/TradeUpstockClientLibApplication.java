package com.trade.app.upstock.lib;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TradeUpstockClientLibApplication implements CommandLineRunner{

	@Autowired
	ApplicationContext context;
	
	public static void main(String[] args) {
		SpringApplication.run(TradeUpstockClientLibApplication.class, args);
	}
	
	@Override
	 public void run(String... args) {

	        String[] beanNames = context.getBeanDefinitionNames();

	        Arrays.sort(beanNames);

	        for (String beanName : beanNames) {
	            System.out.println(beanName);
	        }
	    }
}
