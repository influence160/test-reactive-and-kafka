package othmen.test.kafka.stream.autopopulated.jointure;

import java.util.List;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Printed;

import othmen.test.kafka.AbstractStringStringAutoTest;

public class TestJointure3 extends AbstractStringStringAutoTest {

	public TestJointure3() {
		super("TestJointure3", 
				List.of(
						new ProducerRecord<String, String>("jointure-table1", "1", "aaa"),
						new ProducerRecord<String, String>("jointure-table1", "2", "bbb"),
						new ProducerRecord<String, String>("jointure-table1", "4", "ddd"),
						new ProducerRecord<String, String>("jointure-table1", "1", "aaa2"),
						new ProducerRecord<String, String>("jointure-table2", "1", "AAA1"),
						new ProducerRecord<String, String>("jointure-table2", "2", "BBB1"),
						new ProducerRecord<String, String>("jointure-table2", "3", "CCC1"),
						new ProducerRecord<String, String>("jointure-table2", "4", "DDD1"),
						new ProducerRecord<String, String>("jointure-table2", "1", "AAA2"),
						new ProducerRecord<String, String>("jointure-table2", "2", "BBB2")
						), 
				List.of(
						new ProducerRecord<String, String>("jointure-table2", "2", "BBB3"),
						new ProducerRecord<String, String>("jointure-table2", "3", "CCC2"),
						new ProducerRecord<String, String>("jointure-table1", "3", "ccc"),
						new ProducerRecord<String, String>("jointure-table1", "4", "ddd2")
						), 
				1000l);
	}

	public static void main(String[] args) {

		TestJointure3 test = new TestJointure3();
		test.test();
	}

	@Override
	protected void doKafkaWork() {
        KTable<String, String> table1 = builder.<String, String>table("jointure-table1");

        KStream<String, String> table2 = builder.<String, String>stream("jointure-table2");
        
        KStream<String, String> jointure = table2
        		.leftJoin(table1, (v2, v1) -> "[" + v2 + " | " + v1 + "]");

        jointure.print(Printed.toSysOut());
	}

}
