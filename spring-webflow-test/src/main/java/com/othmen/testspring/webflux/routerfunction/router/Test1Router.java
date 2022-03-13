package com.othmen.testspring.webflux.routerfunction.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.othmen.testspring.webflux.routerfunction.handler.Test1Handler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration(proxyBeanMethods = false)
public class Test1Router {

	@Bean
	public RouterFunction<ServerResponse> route(Test1Handler greetingHandler) {

		return RouterFunctions
			.route(GET("/hello").and(accept(MediaType.APPLICATION_JSON)), greetingHandler::hello);
	}
}