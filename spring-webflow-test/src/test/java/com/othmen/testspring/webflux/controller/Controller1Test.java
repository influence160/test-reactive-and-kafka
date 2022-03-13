package com.othmen.testspring.webflux.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class Controller1Test {
	
	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	public void testMono1Test() {
		webTestClient.get()
		.uri("/controller1/test-mono1")
		.exchange()
		.expectStatus().isOk()
		.expectBody(String.class).isEqualTo("test-mono1");
	}

}
