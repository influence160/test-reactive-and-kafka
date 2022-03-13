package com.othmen.testSpring.kafkaStream.test.tests;

import java.time.Duration;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KGroupedTable;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestSuite1Confiuration {
	
	@Autowired
	TraiteurDeMessages traiteurDeMessages;

	//@Bean 
	public KStream<String, String> testKStreamOuterJoinKStream(StreamsBuilder builder) {

		KStream<String, String> table1 = builder.<String, String>stream("jointure-table1");

        KStream<String, String> table2 = builder.<String, String>stream("jointure-table2");
        
        KStream<String, String> jointure = table1
        		.outerJoin(table2, (v2, v1) -> "[" + v2 + " | " + v1 + "]", JoinWindows.of(Duration.ofHours(1)));

        jointure.print(Printed.toSysOut());
        return jointure;
	}

	//@Bean 
	public KTable<String, String> testKTableOuterJoinKTable(StreamsBuilder builder) {
		KTable<String, String> table1 = builder.<String, String>table("jointure-table1",
				Materialized.as("table1-statestore"));

		KTable<String, String> table2 = builder.<String, String>table("jointure-table2",
				Materialized.as("table2-statestore"));
        
		KTable<String, String> jointure = table1
        		.outerJoin(table2, (v2, v1) -> "[" + v2 + " | " + v1 + "]");

        //jointure.toStream().print(Printed.toSysOut());
        jointure.toStream().foreach(traiteurDeMessages::traiter);
        return jointure;
	}
	

	//@Bean 
	public KStream<String, String> testKStreamLeftJoinKTable(StreamsBuilder builder) {
	
	    KTable<String, String> table1 = builder.<String, String>table("jointure-table1");
	
	    KStream<String, String> table2 = builder.<String, String>stream("jointure-table2");
	    
	    KStream<String, String> jointure = table2
	    		.leftJoin(table1, (v2, v1) -> "[" + v2 + " | " + v1 + "]");
	
	    jointure.print(Printed.toSysOut());
	    return jointure;
	}
	
//	@Bean 
//	public KTable<String, String>testGroupBy(StreamsBuilder builder) {
//		//group 1, 2 and 5 together and  3 and 4 together
//	    KTable<String, String> table2 = builder.<String, String>table("jointure-table2");
//	    
//	    KGroupedTable<String, String> grouped = table2.groupBy(
//	    		(k,  v) -> new KeyValue(k == null ? null : "3".equals(k) || "4".equals(k) ? "2" : 1, "k/"+v),
//	    		Grouped.with(Serdes.String(), Serdes.String()));
//	   // grouped.
//	    
//		
//	}
}
