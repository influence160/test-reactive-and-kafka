package othmen.test.reactor;

import java.util.UUID;
import java.util.function.Supplier;

import othmen.test.reactor.util.Utils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TestCreateSequence {

	public static void main(String[] args) {
//		testCreateFluxWithoutComplete();
		testDeferMono();
	}
	
	public static void testCreateFluxWithoutComplete() {
		Flux<String> f = Flux.create(sink -> {
			sink.next("aaa");
			sink.next("bbb");
		});
		f.subscribe(System.out::println);
	}
	
	public static void testCreateFluxWithComplete() {
		Flux<String> f = Flux.create(sink -> {
			sink.next("aaa");
			sink.next("bbb");
			sink.complete();
		});
		f.subscribe(System.out::println);
	}
	
	public static void testDeferMono() {
		 Supplier<String> s = () -> UUID.randomUUID().toString();
		 Mono<String> mono0 = Mono.just(s.get());
		 mono0.subscribe(System.out::println);
		 mono0.subscribe(System.out::println);

		 Mono<String> mono1 = Mono.create((sink) -> {sink.success(s.get());});
		 mono1.subscribe(System.out::println);
		 mono1.subscribe(System.out::println);
		 

		 Mono<String> mono2 = Mono.defer(() -> Mono.just(s.get()));
		 mono2.subscribe(System.out::println);
		 mono2.subscribe(System.out::println);
		 
		 
	}
	
	

}
