package othmen.test.kafka.stream.autopopulated.jointure;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.state.KeyValueStore;

import othmen.test.kafka.AbstractStringStringAutoTest;

public class TestGroupBy extends AbstractStringStringAutoTest {

	public TestGroupBy() {
		super("TestJointure3", 
				List.of(
						new ProducerRecord<String, String>("jointure-table1", "1", "aaa"),
						new ProducerRecord<String, String>("jointure-table1", "2", "bbb"),
						new ProducerRecord<String, String>("jointure-table1", "3", "ccc"),
						new ProducerRecord<String, String>("jointure-table1", "4", "ddd"),
						new ProducerRecord<String, String>("jointure-table2", "1", "AAA1"),
						new ProducerRecord<String, String>("jointure-table2", "2", "BBB1"),
						new ProducerRecord<String, String>("jointure-table2", "3", "CCC1"),
						new ProducerRecord<String, String>("jointure-table2", "4", "DDD1"),
						new ProducerRecord<String, String>("jointure-table2", "1", "AAA2"),
						new ProducerRecord<String, String>("jointure-table2", "2", "BBB2")
						), 
				List.of(
						new ProducerRecord<String, String>("jointure-table2", "5", "EEE"),
						new ProducerRecord<String, String>("jointure-table2", "2", "BBB3"),
						new ProducerRecord<String, String>("jointure-table2", "3", "CCC2")
						), 
				1000l);
	}

	public static void main(String[] args) {

		TestGroupBy test = new TestGroupBy();
		test.test();
	}

	@Override
	protected void doKafkaWork() {
		KStream<String, String> table1 = builder.<String, String>stream("jointure-table2");
		
		KGroupedStream<String, String> groupedStream = table1.groupByKey();
//		KTable<String, String> ktable = groupedStream.reduce((v1, v2) -> {
//			System.out.println("reduce "+ v1 + v2);
//			return v1 + ", " + v2;
//		});

//		KTable<String, ArrayList<String>> ktable = groupedStream.aggregate(() -> new ArrayList<String>(),
//				(v1, v2, vr) ->  {
//					vr.add(v1);
//					vr.add(v2);
//					return vr;
//				},
//				Materialized.<String, String, KeyValueStore<Bytes, byte[]>>as("TestGroupBy-store")
//					.withValueSerde(Serdes.String())
//				);
		KTable<String, String> ktable = groupedStream.aggregate(() -> "",
				(k, v, vr) -> vr + ", " + v,
				Materialized.<String, String, KeyValueStore<Bytes, byte[]>>as("TestGroupBy-store")
					.withValueSerde(Serdes.String())
				);
		
		ktable.toStream().print(Printed.toSysOut());

//        KStream<String, String> table2 = builder.<String, String>stream("jointure-table1");
//        table2
//		.join(ktable, (v1, v2) -> v2)
//		.print(Printed.toSysOut());
	}

}
