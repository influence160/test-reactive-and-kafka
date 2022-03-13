package com.othmen.testSpring.kafkaStream.test.tests2;

import java.time.Duration;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.stereotype.Component;

@Component
public class KStreamTest implements StreamTest {
	
	StreamsBuilder builder;
	
	public KStreamTest(StreamsBuilder streamsBuilder) {
		this.builder = streamsBuilder;
	}

	@Override
	public void test() {
		KStream<String, String> table1 = builder.<String, String>stream("jointure-table1", Consumed.with(Serdes.String(), Serdes.String()));
		table1.print(Printed.toSysOut());
		
	}

}
