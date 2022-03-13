package com.othmen.testSpring.kafkaStream.test;

import java.text.MessageFormat;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

//@Component
public class MyProducer {
	
	KafkaTemplate<String, String> kafkaTemplate;
	ListenableFutureCallback callback;
	ExecutorService executorService;
	protected long initialDelay = 0l;
	protected long delay = 1000l;
	
	protected String[][] dataBeforeRun = 

			 new String[][] {
				{"jointure-table1", "1", "aaa"},
				{"jointure-table1", "2", "bbb"},
				{"jointure-table1", "4", "ddd"},
				{"jointure-table1", "1", "aaa2"},
				{"jointure-table2", "1", "AAA1"},
				{"jointure-table2", "2", "BBB1"},
				{"jointure-table2", "3", "CCC1"},
				{"jointure-table2", "4", "DDD1"},
				{"jointure-table2", "1", "AAA2"},
				{"jointure-table2", "2", "BBB2"}
			};
			
	protected String[][] dataAfterRun = 

			 new String[][] {{"jointure-table2", "2", "BBB3"},
				{"jointure-table2", "3", "CCC2"},
				{"jointure-table1", "3", "ccc"},
				{"jointure-table1", "4", "ddd2"}
			};
	
	public MyProducer(KafkaTemplate<String, String> kafkaTemplate
			) {
		this.kafkaTemplate = kafkaTemplate;
		callback = new ListenableFutureCallback<SendResult<String, String>>() {

		    @Override
		    public void onSuccess(SendResult<String, String> result) {
		        System.out.println("onSuccess " + result);
		    }

		    @Override
		    public void onFailure(Throwable ex) {
		        System.out.println("onFailure ");
		        ex.printStackTrace();
		    }

		};
	}
	
	@PostConstruct
	void init() {
		sendInitialData();
		executorService = Executors.newSingleThreadExecutor();
	}
	
	
	
	public void sendInitialData() {
			System.out.println("sendInitialData");
		Stream.of(dataBeforeRun).forEach(this::send);
	}

	protected void send(String[] data) {
		send(data[0], data[1], data[2]);
	}


	protected void send(String topic, String key, String value) {
		System.out.println(MessageFormat.format("writing ProducerRecord(topic={0}, key={1}, value={2})", topic, key, value));

		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, key, value);
		future.addCallback(callback);
	}

	
	public void sendDataAfterRun() {
			System.out.println("sendDataAfterRun");

			
			Callable<Void> callable = () -> {
				try {
			        TimeUnit.MILLISECONDS.sleep(initialDelay);
					Stream.of(dataAfterRun).forEach(topicKeyAndValue -> {
				        try {
					        TimeUnit.MILLISECONDS.sleep(delay);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				        send(topicKeyAndValue);
					});
				} catch(Throwable th) {
					th.printStackTrace();
				}
		        return null;
			};

			executorService.submit(callable);
	}
	
	@PreDestroy
	public void close() {
		System.out.println("executorService.shutdownNow();");
		executorService.shutdownNow();
	}

}
