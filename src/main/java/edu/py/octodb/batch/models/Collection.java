package edu.py.octodb.batch.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import edu.py.octodb.batch.io.util.CollectionTransaction;

// Idem a collection mongodb
public class Collection implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private List<Document> documents = new CollectionTransaction<>();
	private Date date;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Document> getDocuments() {
		return documents;
	}
	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
