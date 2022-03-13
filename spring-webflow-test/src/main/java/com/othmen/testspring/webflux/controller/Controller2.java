package com.othmen.testspring.webflux.controller;

import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/controller2")
public class Controller2 {
	private static Log log = LogFactory.getLog(Controller1.class);
	
	/**
	 * consumer 
	 * @return
	 */
	@GetMapping("/test-mono1")
	public Mono<String> testMono1() {
		return Mono.just("test-mono1")
				.doOnNext((s) -> {
					log.info("debut consumer");
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
					log.info("fin consumer");
				});
	}
	
	/**
	 * consumer 
	 * @return
	 */
	@GetMapping("/test-mono2")
	public Mono<Void> testMono2() {
		return Mono.just("test-mono1")
				.filter((s) -> {
					log.info("debut filter");
					try {
						Thread.sleep(5000);
						log.info("fin filter");
						return true;
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				})
				.switchIfEmpty(Mono.error(RuntimeException::new))
				.flatMap((s) -> {
					log.info("debut flatMap");
					try {
						Thread.sleep(5000);
						log.info("fin flatMap");
						return Mono.just("bbb");
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				})
				.publishOn(Schedulers.boundedElastic())
				.doOnNext((s) -> {
					log.info("debut doOnNext");
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
					log.info("fin doOnNext");
				})
				.doOnSuccess((s) -> {
					log.info("debut doOnSuccess");
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
					log.info("fin doOnSuccess");
				})
				.doOnSubscribe((s) -> {
					log.info("debut doOnSubscribe");
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
					log.info("fin doOnSubscribe");
				})
				.then()
				;
	}

	
	/**
	 * consumer 
	 * @return
	 */
	@GetMapping("/test-mono3")
	public Mono<String> testMono3() {
		return Mono.just(UUID.randomUUID().toString())
				.map((x) -> {Mono.just(x)
				.filter((s) -> {
					log.info("debut filter");
					try {
						Thread.sleep(5000);
						log.info("fin filter");
						return true;
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				})
				.switchIfEmpty(Mono.error(RuntimeException::new))
				.flatMap((s) -> {
					log.info("debut flatMap");
					try {
						Thread.sleep(5000);
						log.info("fin flatMap");
						return Mono.just("bbb");
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				})
//				.publishOn(Schedulers.boundedElastic())
				.doOnNext((s) -> {
					log.info("debut doOnNext");
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
					log.info("fin doOnNext");
				})
				.doOnSubscribe((s) -> {
					log.info("debut doOnSubscribe");
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
					log.info("fin doOnSubscribe");
				})
				.doOnSuccess((s) -> {
					log.info("debut doOnSuccess");
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
					log.info("fin doOnSuccess");
				})
				.subscribeOn(Schedulers.boundedElastic())
				.subscribe();
				return x;}
				);
	}

}
