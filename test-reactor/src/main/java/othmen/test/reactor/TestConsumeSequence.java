package othmen.test.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TestConsumeSequence {

	public static void main(String[] args) {
		Mono<String> mono = Mono.just("aaa");
		Flux<String> flux = Flux.generate(() -> "a", (ch, sink) -> {
			String ch2 = new String(new char[] { (char) ((int) ch.charAt(0) + 1) });
			sink.next(ch2);
			if (ch2.equals("l")) {
				sink.complete();
			}
			if (ch2.equals("m")) {
				throw new RuntimeException("ch2 == m");
			}
			return ch2;
		});

		testMultiConsume2(mono);
	}

	private static void testConsume1(Mono<String> mono) {
		mono.doOnNext(System.out::println).subscribe();
	}

	private static void testConsume1(Flux<String> flux) {
		flux.doOnNext(System.out::println).subscribe();
	}

	private static void testConsume2(Mono<String> mono) {
		mono.subscribe(System.out::println);
	}

	private static void testConsume2(Flux<String> flux) {
		flux.subscribe(System.out::println);
	}

	private static void testMultiConsume(Mono<String> mono) {
		mono.doOnNext(System.out::println).doOnNext(System.out::println).subscribe();
	}

	private static void testMultiConsume(Flux<String> flux) {
		flux.doOnNext(System.out::println).doOnNext(System.out::println).subscribe();
	}

	private static void testMultiConsume2(Mono<String> mono) {
		mono.doOnNext(System.out::println).flatMap(x -> Mono.error(RuntimeException::new))
		.doOnSuccess(x -> System.out.println("Success")).subscribe();
	}
}
