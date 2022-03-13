package com.othmen.testspring.webflux.routerfunction.handler;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class Test1Handler {

	public Mono<ServerResponse> hello(ServerRequest request) {
		System.out.println("hello()");
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
			.body(Mono.just("Hello, test1!").delayElement(Duration.ofSeconds(10)), String.class);
	}
}