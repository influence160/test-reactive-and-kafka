package com.othmen.testspring.webflux.test;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Mono;

public class Test {

	public static void main(String[] args) {


		Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "aaa"))
		.onErrorResume(ResponseStatusException.class, ex -> {
			if (ex.getStatus() == HttpStatus.BAD_REQUEST ) {
				return Mono.error(new RuntimeException("bbb"));
			}
			return Mono.error(ex);
		})
		.subscribe();
	}

}
