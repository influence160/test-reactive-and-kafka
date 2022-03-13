package othmen.test.kafka;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.streams.KafkaStreams.State;
import org.apache.kafka.streams.KafkaStreams.StateListener;

public abstract class AbstractStringStringAutoTest extends AbstractStringStringTest{
	
	List<ProducerRecord<String, String>> toWriteBeforeStart;
	List<ProducerRecord<String, String>> toWriteAfterStart;
	long delay;
	ExecutorService executorService;
	

	public AbstractStringStringAutoTest(String applicationId, List<ProducerRecord<String, String>> toWriteBeforeStart, List<ProducerRecord<String, String>> toWriteAfterStart, long delay) {
		super(applicationId);
		this.toWriteBeforeStart = toWriteBeforeStart;
		this.toWriteAfterStart = toWriteAfterStart;
		this.delay = delay;
		addStateListener(new StateListener() {

			private boolean dataWriteStarted = false;

			public void onChange(State newState, State oldState) {
				if (newState == State.RUNNING) {
					if (dataWriteStarted) {
						return;
					}
					writeDataAfterStart();
					dataWriteStarted = true;
				}
			}
		});
		executorService = Executors.newSingleThreadExecutor();
	}

	
	public void test() {
		writeDataBeforeStart();
		doKafkaWork();
		start();
		//writeDataAfterStart();
		stop();
	}


	protected void writeDataBeforeStart() {
		System.out.println("writeDataBeforeStart");
        Producer<String, String> producer = new KafkaProducer<>(props);
		toWriteBeforeStart.forEach(producerRecord -> {
			System.out.println(MessageFormat.format("writing ProducerRecord(topic={0}, key={1}, value={2})", producerRecord.topic(), producerRecord.key(), producerRecord.value()));
	        producer.send(producerRecord);
		});
        producer.close();
	}


	protected void writeDataAfterStart() {
		System.out.println("writeDataBeforeStart");
		Callable<Void> callable = () -> {
			try {
	        Producer<String, String> producer = new KafkaProducer<>(props);
			toWriteAfterStart.forEach(producerRecord -> {
		        try {
			        TimeUnit.MILLISECONDS.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(MessageFormat.format("writing ProducerRecord(topic={0}, key={1}, value={2})", producerRecord.topic(), producerRecord.key(), producerRecord.value()));
		        producer.send(producerRecord);
			});
	        producer.close();
			} catch(Throwable th) {
				th.printStackTrace();
			}
	        return null;
		};

		executorService.submit(callable);
	}
	

	
	protected void stop() {
		super.stop();
		executorService.shutdownNow();
	}
}
