package othmen.test.reactor.util;

import java.util.function.Consumer;

import reactor.core.publisher.Flux;

public abstract class Utils {
	public static Consumer<String> sysout = s -> System.out.println(Thread.currentThread().getName() + " " + s);

	public static <T> void sysout(Flux<T> flux) {
		flux.subscribe(s -> System.out.println(Thread.currentThread().getName() + " " + s));
	}
	
}
