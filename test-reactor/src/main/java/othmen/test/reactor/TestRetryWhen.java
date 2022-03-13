package othmen.test.reactor;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetrySpec;

public class TestRetryWhen {
	
	static class Exception1 extends RuntimeException {
		public Exception1() {
			super("Exception1");
		}
	}
	
	static class Exception2 extends RuntimeException {
		public Exception2() {
			super("Exception2");
		}
	}
	
	static Mono<String> method() {
		double i = Math.random() * 10;
		System.out.println("i="+ i);			
		return i >= 6 ? Mono.error(Exception1::new) : (i <= 3 ? Mono.error(Exception2::new) : Mono.just("success"));
	}

	public static void main(String[] args) {
		//testRetryWhen1();
		testRetryWhen2();
	}

	private static void testRetryWhen1() {
		System.out.println(Mono.defer(() -> method())
		//.flatMap(x -> {System.out.println("flatMap"); return Mono.just(x);})
		.retryWhen(
				Retry.fixedDelay(3, Duration.ofSeconds(1))
				.filter(ex -> ex instanceof Exception1 )
				
				)
		.map(s -> {System.out.println("flatMap apres retryWhen");
		return s+"a";
		})
		.log()
		.block());
	}

	private static void testRetryWhen2() {
		AtomicInteger ai = new AtomicInteger(0);
		System.out.println(Mono.defer(() -> Mono.just(ai))
		.doOnNext(ai2 ->  {
			int i = ai2.incrementAndGet();
			System.out.println(i);
			if (i < 5) {
				throw new Exception1();
			}
			
		})
		.retryWhen(
				Retry.fixedDelay(10, Duration.ofSeconds(1))
				)
		.map(s -> {System.out.println("flatMap apres retryWhen");
		return s.get()+"a";
		})
		.log()
		.block());
	}

}
