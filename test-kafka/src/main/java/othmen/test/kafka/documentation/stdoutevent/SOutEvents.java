package othmen.test.kafka.documentation.stdoutevent;

import java.util.Properties;
import java.util.stream.Stream;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

public class SOutEvents {

	public static void main(String[] args) {
    	Properties props = new Properties();
    	props.put(StreamsConfig.APPLICATION_ID_CONFIG, "SOutEventsApplication3");
    	props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");    // assuming that the Kafka broker this application is talking to runs on local machine with port 9092
    	props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    	props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

    	
    	final StreamsBuilder builder = new StreamsBuilder();
    	KTable<String, String> kstream = builder.table("test-partition3");
    	kstream.toStream().foreach((k, v) -> System.out.println("(" + k + ", " + v + ")"));
    	
    	final Topology topology = builder.build();
    	
    	final KafkaStreams streams = new KafkaStreams(topology, props);
    	streams.setUncaughtExceptionHandler(new StreamsUncaughtExceptionHandler() {
			
			@Override
			public StreamThreadExceptionResponse handle(Throwable exception) {
				exception.printStackTrace();
				return null;
			}
		});
    	streams.start();
    	
    	
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
            	streams.close();
            }
        });
    	
	}

}
