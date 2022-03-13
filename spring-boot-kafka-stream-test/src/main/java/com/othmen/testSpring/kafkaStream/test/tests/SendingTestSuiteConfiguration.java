package com.othmen.testSpring.kafkaStream.test.tests;

import java.time.Duration;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorSupplier;
import org.apache.kafka.streams.processor.api.Record;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.othmen.testSpring.kafkaStream.test.model.Sending;

@Configuration
public class SendingTestSuiteConfiguration {

	@Bean
	public KTable<String, String> testKTableOuterJoinKTable(StreamsBuilder builder) {
		KTable<String, String> table1 = builder.<String, String>table("jointure-table1");

		KTable<String, String> table2 = builder.<String, String>table("jointure-table2", Consumed.with(Serdes.String(), Serdes.String()));
        
		KTable<String, String> jointure = table1
        		.outerJoin(table2, (v1, v2) -> "(" + v1 + " | " + v2 + ")")
        		.filter((k, v) -> v != null);

		//jointure.toStream().print(Printed.toSysOut());
		
		//jointure.toStream().peek((k,v) -> System.out.println("peek " + k + ", " + v));
		
		jointure.toStream().process(((ProcessorSupplier)() -> new Processor<String, String, String, String>() {

			@Override
			public void process(Record<String, String> record) {
				System.out.println("process " + record.key() + ", " + record.value());
			}
			
		}));
		
        return jointure;
	}
	
	//@Bean
	public KTable<String, String> testKStreamGroupByLeftJoinKStream(StreamsBuilder builder) {
		KStream<String, String> table1 = builder.<String, String>stream("jointure-table1");
		KTable<String, String> table1Grouped = table1.groupByKey().reduce((v1, v2) -> v2);

		KStream<String, String> table2 = builder.<String, String>stream("jointure-table2", Consumed.with(Serdes.String(), Serdes.String()));
		KTable<String, String> table2Grouped = table2.groupByKey().reduce((v1, v2) -> v2);
		KTable<String, String> jointure = table1Grouped
        		.outerJoin(table2Grouped, (v1, v2) -> v1)
        		.filter((k, v) -> v != null);

        jointure.toStream().print(Printed.toSysOut());
        return jointure;
	}

	
	//@Bean
	public KStream<String, String> kstreamLeftJoinKstream(StreamsBuilder builder) {
		KStream<String, String> table1 = builder.<String, String>stream("jointure-table1");

		KStream<String, String> table2 = builder.<String, String>stream("jointure-table2", Consumed.with(Serdes.String(), Serdes.String()));

		KStream<String, String> jointure = table1
        		.leftJoin(table2, (v1, v2) -> v1, JoinWindows.of(Duration.ofMinutes(1)));

        jointure.print(Printed.toSysOut());
        return jointure;
	}
	
	//@Bean
	public KStream<String, String> kstreamLeftJoinKtable(StreamsBuilder builder) {
		KStream<String, String> table1 = builder.<String, String>stream("jointure-table1");

		KTable<String, String> table2 = builder.<String, String>table("jointure-table2", Consumed.with(Serdes.String(), Serdes.String()));

		KStream<String, String> jointure = table1
        		.leftJoin(table2, (v1, v2) -> v1);

        jointure.print(Printed.toSysOut());
        return jointure;
	}
	
}
