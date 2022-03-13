package com.othmen.testspring.kafka.test1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest
@EmbeddedKafka(partitions = 1)
public class DemoApplication1Test {

//    @Autowired
//    private KafkaConsumer<String, String> consumer;
//
//    @Autowired
//    private KafkaProducer<String, String> producer;
//    
	@Autowired
    KafkaTemplate<String, String> template;
	
	@MockBean
	LecteurDeMessages lecteurDeMessages;

    private String topic = "topic1";
    
    Future<String> getFuture(String s) {
    	return new FutureTask<>(() -> s);
    }
    
    static class MyCallable implements Callable<String> {
    	
    	String value;
    	CountDownLatch latch;
    	
    	public MyCallable() {
			this.latch = new CountDownLatch(1);
		}

		@Override
		public String call() throws Exception {
			latch.await();
			return value;
		}
		
		public synchronized void setValue(String value) {
			this.value = value;
			latch.countDown();
		}
    	
    }
    
	@Test
	@Timeout(2)
	void test() throws InterruptedException, ExecutionException {
		MyCallable callable = new MyCallable();
		Future<String> future = new FutureTask<>(callable);
		Mockito.doAnswer(invocation -> {
			callable.setValue(invocation.getArgument(0));
			return null;
		}).when(lecteurDeMessages).lire(anyString());
		
        template.send("topic1", "test1");
        assertEquals("test1", future.get());
	}

}
