package othmen.test.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TestMapSequence {

	public static void main(String[] args) {
		Mono<String> mono = getMono();
		
//		testFlatMapMany3(mono);
		testFlatMapFlux(getFlux());
	}

	private static Mono<String> getMono() {
		return Mono.just("aaa");
	}
	
	private static Flux<String> getFlux() {

		Flux<String> flux = Flux.generate(() -> "a", (ch, sink) -> {
			String ch2 = new String(new char[] {(char)((int)ch.charAt(0) + 1)});
			sink.next(ch2);
			if (ch2.equals("l")) {
				sink.complete();
			}
			if (ch2.equals("m")) {
				throw new RuntimeException("ch2 == m");
			}
			return ch2;
		});
		return flux;
	}

	private static void testFlatMapMany1(Mono<String> mono) {
		
		mono.flatMapMany((s) -> getFlux().zipWith(Mono.just(s)))
		.subscribe(System.out::println);
	}


	//The bad way
	private static void testFlatMapMany2(Mono<String> mono) {
		
		mono.flatMapMany((s) -> getFlux().zipWith(
					Flux.create((sink) -> {while (true) {sink.next(s);}})
					.log()
				))
		.log()
		.subscribe(System.out::println);
	}


	//The good way
	private static void testFlatMapMany3(Mono<String> mono) {
		
		mono.flatMapMany((s) -> getFlux()
				.flatMap(s2 -> Mono.just(s2)
						.zipWith(Mono.just(s))
						)
				)
		.log()
		.subscribe(System.out::println);
	}
	
	private static void testFlatMapFlux(Flux<String> flux) {
		flux.flatMap(s -> Mono.just(">>" + s + "<<"))
		.subscribe(System.out::println);
	}
	
	private static void testMapFlux(Flux<String> flux) {
		flux.map(s -> ">>" + s + "<<")
		.subscribe(System.out::println);
	}
	

}
