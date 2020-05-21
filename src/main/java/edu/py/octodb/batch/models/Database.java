package edu.py.octodb.batch.models;

import java.util.Date;
import java.util.List;

// Gestion de las bases de datos
// Lista de documentos y colecciones
public class Database {

	private String name;
	private String descripcion;
	private Date date;
	private List<Document> users;
	private List<Collection> collections;
	private static String path;

	public static String getPath() {
		return path;
	}

	public static void setPath(String path) {
		Database.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Document> getUsers() {
		return users;
	}

	public void setUsers(List<Document> users) {
		this.users = users;
	}

	public List<Collection> getCollections() {
		return collections;
	}

	public void setCollections(List<Collection> collections) {
		this.collections = collections;
	}

}
