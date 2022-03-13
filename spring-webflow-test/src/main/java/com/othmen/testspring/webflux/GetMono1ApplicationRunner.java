package com.othmen.testspring.webflux;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

//@Component
public class GetMono1ApplicationRunner implements ApplicationRunner {
	
	private static Log log = LogFactory.getLog(GetMono1ApplicationRunner.class);
	
	private final WebClient webClient;
	

	public GetMono1ApplicationRunner(WebClient.Builder webClientBuilder) {
		webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
	}



	public void run(ApplicationArguments args) throws Exception {
		log.info("GetMono1ApplicationRunner.run");
		Mono<String> mono = webClient.get().uri("/controller1/test-mono1")
		.retrieve()
		.bodyToMono(String.class);
		log.info("GetMono1 result : " + mono.block());
	}

}
