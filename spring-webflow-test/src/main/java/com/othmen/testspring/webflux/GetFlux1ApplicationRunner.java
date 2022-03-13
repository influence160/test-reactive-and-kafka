package com.othmen.testspring.webflux;

import java.time.Duration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import reactor.core.publisher.Flux;

//@Component
public class GetFlux1ApplicationRunner implements ApplicationRunner {

	private static Log log = LogFactory.getLog(GetFlux1ApplicationRunner.class);

	private final WebClient webClient;

	public GetFlux1ApplicationRunner(WebClient.Builder webClientBuilder) {
		webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
	}

	public void run(ApplicationArguments args) throws Exception {
		log.info("GetFlux1.run");
		Flux<String> flux = webClient.get().uri("/controller1/test-flux1")
				.retrieve().bodyToFlux(String.class);
		flux.doOnNext(s -> log.info("GetFlux1 result : " + s))
		.subscribe();
		log.info("GetFlux1.run end");
	}
	
	public static void main (String ... args) {
		UriBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("http://wwww.google.com");
		System.out.println(uriBuilder.build());
		System.out.println(uriBuilder.build().toString());
	}

}
