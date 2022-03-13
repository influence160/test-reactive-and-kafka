package com.othmen.test.spring.kafka.stream.test.tests;

import static org.mockito.ArgumentMatchers.anyString;

import java.io.File;
import java.nio.file.Path;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.TopologyTestDriver;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.othmen.testSpring.kafkaStream.test.tests.TestSuite1Confiuration;
import com.othmen.testSpring.kafkaStream.test.tests.TraiteurDeMessages;

@ExtendWith(SpringExtension.class)
public class TestSuite1ConfiurationTest {
	

	@org.springframework.context.annotation.Configuration
	@Import({TestSuite1Confiuration.class, TraiteurDeMessages.class})
	public static class Configurationn {

		@Bean
		public StreamsBuilder streamsBuilder() {
			StreamsBuilder streamsBuilder = new StreamsBuilder();
			Topology topology = streamsBuilder.build();
			topology.addStateStore(
					Stores.keyValueStoreBuilder(Stores.inMemoryKeyValueStore("table1-statestore"), 
							Serdes.String(), Serdes.String())
					.withLoggingDisabled() // need to disable logging to allow store pre-populating
					);
			topology.addStateStore(
					Stores.keyValueStoreBuilder(Stores.inMemoryKeyValueStore("table2-statestore"), 
							Serdes.String(), Serdes.String())
					.withLoggingDisabled() // need to disable logging to allow store pre-populating
					);
			return streamsBuilder;
		}
	}
	
	@Autowired StreamsBuilder streamsBuilder;
	@MockBean TraiteurDeMessages traiteurDeMessages;
	
	private TopologyTestDriver testDriver;
	private TestInputTopic<String, String> inputTopic1;
	private TestInputTopic<String, String> inputTopic2;
	private KeyValueStore<String, String> store;

	private Serde<String> stringSerde = new Serdes().String();
	

	@BeforeEach
	public void setup() {
		Topology topology = streamsBuilder.build();
		topology.addStateStore(
				Stores.keyValueStoreBuilder(Stores.inMemoryKeyValueStore("table1-statestore"), 
						Serdes.String(), Serdes.String())
				.withLoggingDisabled() // need to disable logging to allow store pre-populating
				);
		topology.addStateStore(
				Stores.keyValueStoreBuilder(Stores.inMemoryKeyValueStore("table2-statestore"), 
						Serdes.String(), Serdes.String())
				.withLoggingDisabled() // need to disable logging to allow store pre-populating
				);
		
		// setup test driver
		Properties props = new Properties();
		props.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		props.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
		props.put(StreamsConfig.STATE_DIR_CONFIG,
				System.getProperty("java.io.tmpdir") + File.separator + "TestSuite1ConfiurationTest");

		testDriver = new TopologyTestDriver(topology, props);

		// setup test topics
		inputTopic1 = testDriver.createInputTopic("jointure-table1", stringSerde.serializer(),
				stringSerde.serializer());
		inputTopic2 = testDriver.createInputTopic("jointure-table2", stringSerde.serializer(),
				stringSerde.serializer());

	}
	
	@AfterTestClass
	public void tearDown() {
	    try {
	    	testDriver.close();
	    } catch (Exception ex) {
	    	
	    }
	}
	
	@Test
	public void test() throws InterruptedException {
		System.out.println("test");
		Mockito.doCallRealMethod().when(traiteurDeMessages).traiter(anyString(), anyString());
	    inputTopic1.pipeInput("1", "aaa");
	    inputTopic2.pipeInput("1", "AAA");
	    Thread.sleep(1000l);
	}

	

}
