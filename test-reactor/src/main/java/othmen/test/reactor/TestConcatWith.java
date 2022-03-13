package othmen.test.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TestConcatWith {

	public static void main(String[] args) {
		testConcatWithEmpty();
	}

	private static void testConcatWithEmpty() {
		Flux.fromArray(new String[] {"aaa", "bbb", "ccc"})
		.concatWith(Mono.empty())
		.log()
		.subscribe();
	}

}
