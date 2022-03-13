package othmen.test.kafka;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KafkaStreams.StateListener;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Printed;

public abstract class AbstractStringStringTest {
	
	protected Properties props;
	final protected StreamsBuilder builder;
	protected Topology topology;
	KafkaStreams streams;
	List<StateListener> stateListeners = new ArrayList<>(List.of((newState, oldState) -> {
    	System.out.println("streams state change : new state = " + newState + ", oldState = " + oldState);
    }));
	StateListener allStateListeners = (newState, oldState) -> {
		stateListeners.forEach(listener -> listener.onChange(newState, oldState));
	};
	boolean started = false;
	
	public AbstractStringStringTest(String applicationId) {
        props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        builder = new StreamsBuilder();
        		
	}
	
	public void test() {
		doKafkaWork();
		start();
		stop();
	}
	
	protected abstract void doKafkaWork();

	protected void start() {
        topology = builder.build();
        streams = new KafkaStreams(topology, props);
    	streams.setUncaughtExceptionHandler(new StreamsUncaughtExceptionHandler() {
			
			@Override
			public StreamThreadExceptionResponse handle(Throwable exception) {
				exception.printStackTrace();
				return null;
			}
		});
    	streams.setStateListener(allStateListeners);
       // cleanUp();
        streams.start();
        streams.addStreamThread();

        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
            	System.out.println("CLOSING STREAMS");
            	//streams.close();
            	try {
					Files.writeString(Path.of("D:\\othmen\\workspaces\\test-reactive\\test-kafka\\shutdown.log"), "\n" + new Date(), StandardOpenOption.CREATE);
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
        
        System.out.println("READY");
        started = true;
	}
	
	protected void addStateListener(StateListener stateListener) {
		if (started) {
			throw new IllegalStateException("already started");
		}
		stateListeners.add(stateListener);
	}
	
	protected void stop() {

        try {
			System.out.println(System.in.read());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        streams.close();
	}
	
	protected void cleanUp() {
		streams.cleanUp();
	}

}
