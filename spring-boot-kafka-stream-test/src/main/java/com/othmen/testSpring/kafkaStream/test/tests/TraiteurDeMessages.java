package com.othmen.testSpring.kafkaStream.test.tests;

import org.springframework.stereotype.Component;

@Component
public class TraiteurDeMessages {
	
	public void traiter(String key, String value) {
		System.out.println("message traité : (" + key + ", " + value + ")");
	}

}
