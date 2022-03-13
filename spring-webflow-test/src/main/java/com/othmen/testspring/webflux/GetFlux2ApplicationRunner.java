package com.othmen.testspring.webflux;

import java.time.Duration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@Component
public class GetFlux2ApplicationRunner implements ApplicationRunner {
	
	private static Log log = LogFactory.getLog(GetFlux2ApplicationRunner.class);
	
	private final WebClient webClient;
	

	public GetFlux2ApplicationRunner(WebClient.Builder webClientBuilder) {
		webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
	}



	public void run(ApplicationArguments args) throws Exception {
		log.info("GetFlux2.run");
		Flux<String> flux = webClient.get().uri("/controller1/test-flux2")
		.retrieve()
		.bodyToFlux(String.class);

//		flux.doOnNext(s -> log.info("GetFlux2 result : " + s))
//		.buffer(1)
//		.subscribe();
//		flux.toStream().forEach(s -> log.info("GetFlux2 result : " + s));

		flux.subscribe(new StringOneByOneSubscriber(s -> log.info("GetFlux2 result : " + s)));
		
		log.info("GetFlux2.run end");
	}

}
