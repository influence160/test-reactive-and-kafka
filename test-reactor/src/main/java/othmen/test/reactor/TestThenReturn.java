package othmen.test.reactor;

import java.util.function.Predicate;

import reactor.core.publisher.Mono;

public class TestThenReturn {

	public static void main(String[] args) {
//		testFilterThenReturn();
//		testMonoVoidErrorThen();
		testMonoVoidThen();
	}

	private static void testFilterThenReturn() {
		Mono.just("aaa")
		.filter(Predicate.isEqual("bbb"))
		.thenReturn("ccc")
		.log()
		.doOnNext(System.out::println).subscribe();
	}

	private static void testMonoVoidErrorThen() {
		Mono.just("aaa")
		.then()
		.then(Mono.just("bbb"))
		.log()
		.doOnNext(System.out::println).subscribe();
		
	}
	
	public static Mono<Void> getMonoVoidError() {
		return Mono.error(new RuntimeException("getMonoVoidError"));
	}
	
	public static Mono<String> createMono(String s) {
		System.out.println("create mono " + s);
		return Mono.just(s);
	}

	private static void testMonoVoidThen() {
		getMonoVoidError()
		.then(createMono("bbb"))
		.log()
		.doOnNext(System.out::println).subscribe();
	}

}
