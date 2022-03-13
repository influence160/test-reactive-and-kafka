package othmen.test.reactor;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

public class TestCreateSequenceTest {
	
	@Test
	public void testGenerate1Test() {
		StepVerifier
			.create(TestCreateSequence.testGenerate1())
			.expectNext(
						Stream.iterate(0, n -> n+1)
						.limit(11)
						.map(n -> "3 x " + n + " = " + 3*n)
						.collect(Collectors.toList())
						.toArray(new String[10])
					)
			.as("testGenerate1Test :)")
			.verifyComplete();
	}

}
