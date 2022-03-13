package com.othmen.testspring.webflux.controller;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/controller1")
public class Controller1 {
	private static Log log = LogFactory.getLog(Controller1.class);
	
	@GetMapping("/test-mono1")
	public Mono<String> testMono1() {
		return Mono.just("test-mono1");
	}
	
	@GetMapping("/test-mono2")
	public Mono<String> testMono2() {
		return Mono.just("test-mono2")
				.delayElement(Duration.ofSeconds(10));
	}

	
	@GetMapping("/test-flux1")
	public Flux<String> testFlux1() {
		List<String> list = Arrays.asList("flux1-value1", "flux1-value2", "flux1-value3", "flux1-value4");
		return Flux.fromIterable(list);
	}

	
	@GetMapping("/test-flux2")
	public Flux<String> testFlux2() {
		List<String> list = Arrays.asList("flux2-value1", "flux2-value2", "flux2-value3", "flux2-value4");
		return Flux.fromIterable(list).delayElements(Duration.ofSeconds(4));
	}

}
