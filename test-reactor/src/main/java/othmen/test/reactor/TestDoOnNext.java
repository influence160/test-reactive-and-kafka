package othmen.test.reactor;

import java.util.ArrayList;
import java.util.List;

import reactor.core.publisher.Mono;

public class TestDoOnNext {

	public static void main(String[] args) {
		List<String> l1 = new ArrayList<>(List.of("aaa", "bbb"));
		List<String> l2 = new ArrayList<>(List.of("ccc", "ddd"));
		Mono.just(l1)
		.doOnNext(list -> list.replaceAll(s -> s.concat("$")))
		.thenReturn(l2)
		.doOnNext(list -> list.add("zzz"))
		.block();
		
		System.out.println(l1);
		System.out.println(l2);
	}

}
