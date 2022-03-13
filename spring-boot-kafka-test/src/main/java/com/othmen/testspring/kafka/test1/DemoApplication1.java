package com.othmen.testspring.kafka.test1;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class DemoApplication1 {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication1.class, args);
	}

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("topic1")
                .partitions(10)
                .replicas(1)
                .build();
    }

    @KafkaListener(id = "myId", topics = "topic1")
    public void listen(String in) {
        lecteurDeMessages().lire(in);
    }
    
    @Bean
    public LecteurDeMessages lecteurDeMessages() {
    	return new LecteurDeMessages();
    }
    

    //desactive pour le TU
    //@Bean
    public ApplicationRunner runner(KafkaTemplate<String, String> template) {
        return args -> {
            template.send("topic1", "test");
            template.send("topic1", "test1");
            template.send("topic1", "test2");
        };
    }
}
