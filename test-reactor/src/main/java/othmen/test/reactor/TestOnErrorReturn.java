package othmen.test.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TestOnErrorReturn {

	public static void main(String[] args) {
		// testOnErrorReturn().subscribe();
		//testOnErrorResume().subscribe();
		testOnErrorContinue2().subscribe(System.out::println);
	}

	private static Mono<String> testOnErrorReturn() {
		return Mono.just("a").<String>flatMap(x -> {
			throw new RuntimeException("aaa");
		}).onErrorReturn(RuntimeException.class, "bbb").map(s -> "ccc").log();
	}

	private static Mono<String> testOnErrorResume() {
		return Mono.just("a").<String>flatMap(x -> {
			throw new RuntimeException("aaa");
		}).onErrorResume(RuntimeException.class, ex -> Mono.just("bbb")).map(s -> "ccc").log();
	}



	private static Flux<String> testOnErrorReturn2() {
		return Flux.range(1,3)
				.map(i -> i != 2 ? i : i/0)
				.map(String::valueOf)
				.map(s -> s + "i")
				.onErrorReturn("x")
				.map(s -> "i" + s)
				.log();
	}


	private static Flux<String> testOnErrorResume2() {
		return Flux.range(1,3)
				.map(i -> i != 2 ? i : i/0)
				.map(String::valueOf)
				.map(s -> s + "i")
				.onErrorResume(Throwable.class, ex -> Mono.just("tt"))
				.map(s -> "i" + s)
				.log();
	}


	private static Flux<String> testOnErrorContinue2() {
		return Flux.range(1,3)
				.map(i -> i != 2 ? i : i/0)
				.map(String::valueOf)
				.map(s -> s + "i")
				.onErrorContinue(Throwable.class, (s, ex) -> Mono.just("e"+s+"e"))
				.map(s -> "i" + s)
				.log();
	}

}
