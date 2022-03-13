package com.othmen.testspring.webflux;

import java.util.function.Consumer;

import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;

public class StringOneByOneSubscriber extends BaseSubscriber<String>{
	
	private Consumer<String> consumer;
	
	public StringOneByOneSubscriber(Consumer<String> consumer) {
		this.consumer = consumer;
	}

	protected void hookOnSubscribe(Subscription subscription){
		subscription.request(1);
	}
	
	protected void hookOnNext(String value) {
		consumer.accept(value);
		request(1);
	}
}
