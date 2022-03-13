package othmen.test.kafka.stream.simple.jointure;

import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Printed;

import othmen.test.kafka.AbstractStringStringTest;

public class TestJointure2 extends AbstractStringStringTest {

	public TestJointure2() {
		super("TestJointure2");
	}


	@Override
	protected void doKafkaWork() {

        KTable<String, String> table1 = builder.<String, String>table("jointure-table1");

        KStream<String, String> table2 = builder.<String, String>stream("jointure-table2");
        
        KStream<String, String> jointure = table2
        		.leftJoin(table1, (v2, v1) -> "[" + v2 + " | " + v1 + "]");

        jointure.print(Printed.toSysOut());
		
	}
	
	public static void main(String[] args) {
		TestJointure2 test = new TestJointure2();
		test.test();
	}

}
