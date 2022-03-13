package othmen.test.reactor.test;

import reactor.core.publisher.Mono;

/**
 * test deux cas de switchIfEmpty avec doOnNext avec des ordres differents 
 * @param args
 */
public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test1();
		test2();
	}

	private static void test1() {
		Mono.<String>empty()
		.doOnNext(x -> System.out.println("doOnNext " + x))
		.switchIfEmpty(Mono.just("aaa"))
		.log()
		.subscribe();
	}

	private static void test2() {
		Mono.<String>empty()
		.switchIfEmpty(Mono.just("aaa"))
		.doOnNext(x -> System.out.println("doOnNext "+ x))
		.log()
		.subscribe();
	}

}
