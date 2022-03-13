package com.othmen.testSpring.kafkaStream.test;

import javax.annotation.PostConstruct;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MyProducer1 extends MyProducer {

	public MyProducer1(KafkaTemplate<String, String> kafkaTemplate) {
		super(kafkaTemplate);


		this.initialDelay = 1000l;
		this.delay = 1000l;
		
		this.dataBeforeRun = 

				 new String[][] {
				};
				
		this.dataAfterRun = 

				 new String[][] {
					{"jointure-table1", "1", "{\"id\": \"1\", \"documentIds\": [\"11\", \"12\", \"13\"]}"},
					{"jointure-table2", "1", "11"},
					{"jointure-table2", "1", "13"},
					{"jointure-table2", "2", "21"},
					{"jointure-table2", "2", "23"},
					{"jointure-table2", "3", "31"},
					{"jointure-table1", "2", "{\"id\": \"2\", \"documentIds\": [\"21\", \"22\", \"23\"]}"},
					{"jointure-table2", "1", "12"},
					{"jointure-table2", "2", "22"},
					{"jointure-table1", "1", "{\"id\": \"1\", \"documentIds\": [\"11\", \"12\", \"13\"]}"}
				};
	}
	
	@PostConstruct
	public void init() {
		super.init();
	}
}
