package com.othmen.testSpring.kafkaStream.test.model;

import java.util.ArrayList;
import java.util.List;

public class Sending {
	
	private String id;
	private List<String> documentIds = new ArrayList<>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getDocumentIds() {
		return documentIds;
	}
	public void setDocumentIds(List<String> documentIds) {
		this.documentIds = documentIds;
	}
	
	

}
