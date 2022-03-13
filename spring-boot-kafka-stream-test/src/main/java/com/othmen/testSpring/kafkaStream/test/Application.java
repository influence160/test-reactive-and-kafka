package com.othmen.testSpring.kafkaStream.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.othmen.testSpring.kafkaStream.test.tests2.KStreamTest;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
		ctx.registerShutdownHook();
		//ktableOuterJoinKtable(ctx);
	}
	


//	private static void ktableOuterJoinKtable(ConfigurableApplicationContext ctx) {
//		ctx.getBean(KStreamOuterJoinKStream.class).test();
//	}

}
