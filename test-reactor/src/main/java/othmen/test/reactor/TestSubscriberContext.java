package othmen.test.reactor;

import reactor.core.publisher.Mono;
import reactor.util.context.Context;

public class TestSubscriberContext {
	String name;

	public static void main(String[] args) {
		TestSubscriberContext t = new TestSubscriberContext();
		t.name = "aaa";
		Context.of(TestSubscriberContext.class, Mono.just(t));
		Mono.subscriberContext()
		.map(context -> context.get(TestSubscriberContext.class))
		.subscribe(System.out::println);
		
		t.name = "bbb";
		Mono.subscriberContext()
		.map(context -> context.get(TestSubscriberContext.class))
		.subscribe(System.out::println);
		
	}
	
	public String toString() {
		return "TestSubscriberContext[" + name + "]";
	}

}
