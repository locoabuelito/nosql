package edu.py.octodb.batch.io;

import edu.py.octodb.batch.models.Collection;
import edu.py.octodb.batch.models.Database;
import edu.py.octodb.batch.models.Document;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CrudOperation {

	// Asincronas
	public Mono<Database> createDataBase(String name);
	public Mono<Boolean> dropDataBase(Database dataBase);
	public Mono<Collection> createCollection(Database dataBase, String name, String descripcion);
	public Mono<Boolean> dropCollection(Database dataBase, Collection collection);
	public Mono<Document> createDocument(Database dataBase, Collection collection, Document document);
	public Mono<Document> updateDocument(Database dataBase, Collection collection, Document document);
	public Mono<Boolean> dropDocument(Database dataBase, Collection collection, Document document);
	public Flux<Database> findDataBase();
	public Mono<Database> findByName(String name);
	
}
