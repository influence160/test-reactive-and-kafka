package othmen.test.kafka.stream.simple.jointure;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.ValueJoiner;

public class TestJointure {


	public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "TestJointure4");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final StreamsBuilder builder = new StreamsBuilder();

        KTable<String, String> table1 = builder.<String, String>table("jointure-table1");

        KStream<String, String> table2 = builder.<String, String>stream("jointure-table2");
        
        KStream<String, String> jointure = table2
        		.leftJoin(table1, (v2, v1) -> "[" + v2 + " | " + v1 + "]");

        jointure.print(Printed.toSysOut());
        		

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
