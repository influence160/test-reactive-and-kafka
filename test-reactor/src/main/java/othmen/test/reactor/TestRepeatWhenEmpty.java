package othmen.test.reactor;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import othmen.test.reactor.TestRetryWhen.Exception1;
import othmen.test.reactor.TestRetryWhen.Exception2;
import reactor.core.publisher.Mono;
import reactor.retry.Repeat;
import reactor.util.retry.Retry;

public class TestRepeatWhenEmpty {



	public static void main(String[] args) {
		testRepeatWhen2();
	}

	private static void testRepeatWhen1() {
		AtomicInteger integer = new AtomicInteger(0);
		System.out.println(Mono.defer(() -> Mono.just(integer))
				.delayElement(Duration.ofSeconds(2))
				.doOnNext(iii -> System.out.println(iii))
				.filter(iii -> iii.incrementAndGet() == 5)
		.repeatWhenEmpty(Repeat.times(6))
		.map(s -> {System.out.println("flatMap apres retryWhen");
		return s+"a";
		})
		.log()
		.block());
	}

	private static void testRepeatWhen2() {
		AtomicInteger integer = new AtomicInteger(0);
		System.out.println(Mono.defer(() -> Mono.just(integer))

				.doOnNext(iii -> System.out.println(iii))
				.filter(iii -> iii.incrementAndGet() == 115)
		.repeatWhenEmpty(Repeat.times(1000).fixedBackoff(Duration.ofSeconds(2))
				.timeout(Duration.ofSeconds(10))
				)
		.map(s -> {System.out.println("flatMap apres retryWhen");
		return s+"a";
		})
		.log()
		.block());
	}
}
