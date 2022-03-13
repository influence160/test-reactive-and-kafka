package com.othmen.testSpring.kafkaStream.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams.State;
import org.apache.kafka.streams.KafkaStreams.StateListener;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.config.StreamsBuilderFactoryBeanConfigurer;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.othmen.testSpring.kafkaStream.test.model.Sending;

import static org.apache.kafka.streams.StreamsConfig.*;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(APPLICATION_ID_CONFIG, "spring-boot-kafka-stream-test");
        props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());


        return new KafkaStreamsConfiguration(props);
    }

//    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_BUILDER_BEAN_NAME)
//    public StreamsBuilderFactoryBean streamsBuilderFactoryBean(KafkaStreamsConfiguration kafkaStreamsConfiguration) {
//    	return new StreamsBuilderFactoryBean(kafkaStreamsConfiguration);
//    }
    
    @Bean Object stateStorexxx(StreamsBuilder builder) {
    	StoreBuilder<KeyValueStore<String, String>> countStoreSupplier =
    			  Stores.keyValueStoreBuilder(
    			    Stores.persistentKeyValueStore("persistent-counts"),
    			    Serdes.String(),
    			    Serdes.String());
    //	KeyValueStore<String, Long> countStore = countStoreSupplier.build();
    	builder.addStateStore(countStoreSupplier);
    	return new Object();
    }
    
    @Bean
    public StreamsBuilderFactoryBeanConfigurer streamsBuilderFactoryBeanConfigurer(MyProducer myProducer) {
    	List<StateListener> stateListeners = new ArrayList<>(List.of((newState, oldState) -> {
        	System.out.println("streams state change : new state = " + newState + ", oldState = " + oldState);
        }
        ));
    	
    	stateListeners.add(
    			new StateListener() {

			private boolean dataWriteStarted = false;

			public void onChange(State newState, State oldState) {
				if (newState == State.RUNNING) {
					if (dataWriteStarted) {
						return;
					}
					myProducer.sendDataAfterRun();
					dataWriteStarted = true;
				}
			}
		});
    	
    	return fb -> fb.setStateListener((newState, oldState) -> {
    		stateListeners.forEach(listener -> listener.onChange(newState, oldState));
    	});
    }
    
}