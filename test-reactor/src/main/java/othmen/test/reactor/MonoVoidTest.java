package othmen.test.reactor;

import java.time.Duration;

import reactor.core.publisher.Mono;
import reactor.util.retry.RetrySpec;

public class MonoVoidTest {

	public static void main(String[] args) {
		//testMapAfterMonoVoid();
//		testThenAfterMonoVoid();
//		testThenReturnAfterMonoVoid();
		testThenAfterMonoEmpty();
	}

	private static Mono<Void> method() {
		return Mono.just("rien du tout ")
				.then();
	}

	private static void testMapAfterMonoVoid() {
		method().map((v) -> "Result")
		.doOnNext(System.out::println)
		.subscribe();
	}

	private static void testMapAfterMonoEmpty() {
		Mono.empty().map((v) -> "Result")
		.doOnNext(System.out::println)
		.subscribe();
	}
	
	private static void testThenReturnAfterMonoVoid() {
		method().then(Mono.just("aaa"))
		.doOnNext(System.out::println)
		.subscribe();
	}

	private static void testThenAfterMonoVoid() {
		method().thenReturn("aaa")
		.doOnNext(System.out::println)
		.subscribe();
	}

	private static void testThenAfterMonoEmpty() {
		Mono.empty()
		.then()
		.then(Mono.just("aaa"))
		.doOnNext(System.out::println)
		.subscribe();
	}

}
