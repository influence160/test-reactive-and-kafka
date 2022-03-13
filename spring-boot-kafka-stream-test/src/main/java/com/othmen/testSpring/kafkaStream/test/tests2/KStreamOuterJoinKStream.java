package com.othmen.testSpring.kafkaStream.test.tests2;
//package com.othmen.testSpring.kafkaStream.test.tests;
//
//import java.time.Duration;
//
//import org.apache.kafka.streams.StreamsBuilder;
//import org.apache.kafka.streams.kstream.JoinWindows;
//import org.apache.kafka.streams.kstream.KStream;
//import org.apache.kafka.streams.kstream.Printed;
//import org.springframework.stereotype.Component;
//
//@Component
//public class KStreamOuterJoinKStream implements StreamTest {
//	
//	StreamsBuilder builder;
//	
//	public KStreamOuterJoinKStream(StreamsBuilder streamsBuilder) {
//		this.builder = streamsBuilder;
//	}
//
//	@Override
//	public void test() {
//		KStream<String, String> table1 = builder.<String, String>stream("jointure-table1");
//
//		KStream<String, String> table2 = builder.<String, String>stream("jointure-table2");
//        
//		KStream<String, String> jointure = table1
//        		.outerJoin(table2, (v2, v1) -> "[" + v2 + " | " + v1 + "]",
//        				JoinWindows.of(Duration.ofHours(1)));
//
//        jointure.print(Printed.toSysOut());
//		
//	}
//
//}
