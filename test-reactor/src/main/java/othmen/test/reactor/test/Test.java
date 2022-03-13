package othmen.test.reactor.test;

import othmen.test.reactor.util.Utils;
import reactor.core.publisher.Mono;

public class Test {

	public static void main(String[] args) {
		test3();
	}

	private static void test3() {
		Mono.just("aaa")
		.doOnNext(Utils.sysout)
		.flatMap(s -> {Utils.sysout.accept(s); return Mono.just("bbb");})
		.doOnNext(Utils.sysout)
		.map(s -> {Utils.sysout.accept(s); return "ccc";})
		.doOnNext(Utils.sysout)
		.block();
	}

	private static void test2() {
		// consommer puis mapper et retourner
		System.out.println(Mono.just("aaa")
		.doOnNext(System.out::println)
		.flatMap(x -> Mono.just(x + " :)"))
		.block());
		
	}

	private static void test1() {
		System.out.println(Mono.just("aaa")
		.switchIfEmpty(Mono.just("bb"))
		.block());
	}

}
