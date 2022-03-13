package othmen.test.reactor;

import reactor.core.publisher.Flux;

public class TestMergeAndConcat {

	public static void main(String[] args) {
		testConcatFluxWithEmpty();
	}

	private static void testConcatFluxWithEmpty() {
		Flux<String> f = Flux.create(sink -> {
			sink.next("aaa");
			sink.next("bbb");
			sink.complete();
		}
		);
		
		Flux.concat(f, Flux.empty())
		.subscribe(System.out::println);
	}

}
