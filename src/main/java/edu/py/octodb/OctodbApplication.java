package edu.py.octodb;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import edu.py.octodb.batch.io.impl.FileCrudOperation;
import edu.py.octodb.batch.models.Document;

@SpringBootApplication
public class OctodbApplication implements CommandLineRunner {
	private Log logger = LogFactory.getLog(getClass());
	@Autowired
	private FileCrudOperation fileCrudOperation;

	public static void main(String[] args) {
		SpringApplication.run(OctodbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		 * fileCrudOperation.createDataBase("octodb").doOnError(throwable -> {
		 * logger.error(throwable); }).subscribe(Database -> {
		 * logger.info("La base de datos fue creada");
		 * fileCrudOperation.createCollection(Database, "users",
		 * "user").doOnError(throwable -> { logger.error(throwable);
		 * }).subscribe(collection -> { for (int i = 0; i <= 100; i++) { Document
		 * document = new Document();
		 * 
		 * document.put("_name", "Bruno Ivan " + i); document.put("_last_name",
		 * "Soto Caballero " + i);
		 * 
		 * fileCrudOperation.createDocument(Database, collection,
		 * document).doOnError(throwable -> { logger.error(throwable);
		 * }).subscribe(document1 -> { logger.info("El documento esta creado _id=" +
		 * document1.get("_id") + "_collection=" + collection.getName()); }); } }); });
		 * 
		 * fileCrudOperation.createDataBase("octodb").doOnError(throwable -> {
		 * logger.error(throwable); }).subscribe(Database -> {
		 * logger.info("La base de datos fue creada");
		 * fileCrudOperation.createCollection(Database, "todo",
		 * "todo").doOnError(throwable -> { logger.error(throwable);
		 * }).subscribe(collection -> { for (int i = 0; i <= 100; i++) { Document
		 * document = new Document();
		 * 
		 * document.put("_name", "Todo " + i); document.put("_last_name", "Todo " + i);
		 * 
		 * fileCrudOperation.createDocument(Database, collection,
		 * document).doOnError(throwable -> { logger.error(throwable);
		 * }).subscribe(document1 -> { logger.info("El documento esta creado _id=" +
		 * document1.get("_id") + "_collection=" + collection.getName()); }); } }); });
		 */

		fileCrudOperation.findDataBase().subscribe(dataBase -> {
			logger.info("La base de datos se cargo " + dataBase.getName());

			dataBase.getCollections().forEach(collection -> {
				logger.info("La collecion se cargo " + collection.getName());
			});
		});
	}
}
