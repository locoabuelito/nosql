package edu.py.octodb.batch.io.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.py.octodb.batch.io.CrudOperation;
import edu.py.octodb.batch.io.util.CollectionTransaction;
import edu.py.octodb.batch.io.util.exception.NotCreatedException;
import edu.py.octodb.batch.models.Collection;
import edu.py.octodb.batch.models.Database;
import edu.py.octodb.batch.models.Document;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class FileCrudOperation implements CrudOperation {

	@Value("${nosql.path.name}")
	private String path_name;

	// Funcion para la creacion de la "base de datos"
	@Override
	public Mono<Database> createDataBase(String name) {
		// TODO Auto-generated method stub
		// Tecnica de promesas - Creacion
		return Mono.create(dataBaseMonoSink -> {

			// Guardamos el destino
			File file = new File(path_name + name);

			try {
				// Se crea la carpeta de destino
				file.mkdir();

				// Pregunta si es ya existe
				if (file.exists()) {
					Database dataBase = new Database();
					//Database.setPath(path_name);
					dataBase.setName(name);
					dataBase.setDate(new Date());
					dataBase.setCollections(new CollectionTransaction<Collection>());
					dataBaseMonoSink.success(dataBase);
				} else {
					// En caso de que no exista
					dataBaseMonoSink.error(new NotCreatedException("La base de datos no existe"));
				}
			} catch (Exception e) {
				// Si es que no puede crear por falta de permisos
				dataBaseMonoSink.error(new NotCreatedException("La base de datos no se creo porque no tiene permisos"));
			}
		});
	}

	// Funcion para la eliminacion de la "base de datos"
	@Override
	public Mono<Boolean> dropDataBase(Database dataBase) {
		// TODO Auto-generated method stub
		// Tecnica de promesas - Creacion
		return Mono.create(dataBaseMonoSink -> {

			// Obtenemos el destino a eliminar
			File file = new File(path_name + dataBase.getName());

			try {
				// Se crea la carpeta de destino
				file.mkdir();

				// Pregunta si es ya existe
				if (file.exists()) {

					// Eliminamos la carpeta
					FileUtils.deleteDirectory(file);
					dataBaseMonoSink.success(true);
				} else {
					// En caso de que no exista
					dataBaseMonoSink.error(new NotCreatedException("La base de datos no existe"));
				}
			} catch (Exception e) {
				// Si es que no puede crear por falta de permisos
				dataBaseMonoSink
						.error(new NotCreatedException("La base de datos no se elimino porque no tiene permisos"));
			}
		});
	}

	@Override
	public Mono<Collection> createCollection(Database dataBase, String name, String descripcion) {
		// TODO Auto-generated method stub
		return Mono.create(dataBaseMonoSink -> {

			try {
				// Creamos el destino para la colección - carpeta
				new File(path_name + dataBase.getName() + "/" + name).mkdir();

				// Se crea la colección
				Collection collection = new Collection();
				collection.setDate(new Date());
				collection.setName(name);
				collection.setDescription(descripcion);
				collection.setDocuments(new ArrayList<>());

				// Tratamiento para escribir JSON
				ObjectMapper objectMapper = new ObjectMapper();

				// Obtiene la direccion de la colección y escribe los valores
				objectMapper.writeValue(
						new File(path_name + dataBase.getName() + "/" + name + "/collection_descriptor.json"),
						collection);
				dataBaseMonoSink.success(collection);
			} catch (Exception e) {
				e.printStackTrace();
				// Si es que no puede crear por falta de permisos
				dataBaseMonoSink
						.error(new NotCreatedException("La coleccion de datos no se creo porque no tiene permisos"));
			}
		});
	}

	@Override
	public Mono<Boolean> dropCollection(Database dataBase, Collection collection) {
		// TODO Auto-generated method stub
		return Mono.create(dataBaseMonoSink -> {
			try {
				// Obtenemos el destino de la colección a eliminar - carpeta
				File file = new File(path_name + dataBase.getName() + "/" + collection.getName() + "/");
				// Eliminamos la colecion
				FileUtils.deleteDirectory(file);
				dataBaseMonoSink.success(true);
			} catch (Exception e) {
				// Si es que no puede eliminar por falta de permisos
				dataBaseMonoSink
						.error(new NotCreatedException("La base de datos no se elimino porque no tiene permisos"));
			}
		});
	}

	@Override
	public Mono<Document> createDocument(Database dataBase, Collection collection, Document document) {
		// TODO Auto-generated method stub
		return Mono.create(documentMonoSink -> {

			// Obtenemos un id para el document y eliminamos caracteres "-"
			String _id = UUID.randomUUID().toString().replace("-", "");

			// Obtenemos el destino para el document en formato .json
			File file = new File(path_name + dataBase.getName() + "/" + collection.getName() + "/" + _id + ".json");
			document.put("_id", _id);
			try {
				// Escribimos en el document 
				new ObjectMapper().writeValue(file, document);
				documentMonoSink.success(document);
			} catch (IOException e) {
				// Si es que no puede guardar el document por falta de permisos
				documentMonoSink.error(e);
			}
		});
	}

	@Override
	public Mono<Document> updateDocument(Database dataBase, Collection collection, Document document) {
		// TODO Auto-generated method stub
		return Mono.create(documentMonoSink -> {
			// Obtenemos el destino del document para actualizar
			File file = new File(path_name + dataBase.getName() + "/" + collection.getName() + "/"
					+ document.get("_id").toString() + ".json");
			try {
				// Actualizamos el document 
				new ObjectMapper().writeValue(file, document);
				documentMonoSink.success(document);
			} catch (IOException e) {
				// Si es que no puede guardar el document por falta de permisos
				documentMonoSink.error(e);
			}
		});
	}

	@Override
	public Mono<Boolean> dropDocument(Database dataBase, Collection collection, Document document) {
		// TODO Auto-generated method stub
		return Mono.create(documentMonoSink -> {
			// Obtenemos el destino del document a eliminar
			File file = new File(
					path_name + dataBase.getName() + "/" + collection.getName() + "/" + document.get("_id") + ".json");
			
			// Pregunta si es ya existe
			if (file.exists()) {
				// Eliminamos el document
				file.delete();
				documentMonoSink.success(true);
			} else {
				// Si es que no puede eliminar el document por falta de permisos
				documentMonoSink.error(new Exception());
			}
		});
	}

	@Override
	public Flux<Database> findDataBase() {
		// TODO Auto-generated method stub
		return Flux.create(dataBaseFluxSink -> {
            File file = new File(path_name);
            if (file.isDirectory()){
                for (String name: file.list()) {
                    Database dataBase = new Database();
                    Database.setPath(path_name);
                    dataBase.setName(name);
                    dataBase.setCollections(new CollectionTransaction<Collection>());
                    dataBase.setDate(new Date());

                    dataBaseFluxSink.next(dataBase);
                }
                dataBaseFluxSink.complete();
            }else {
                dataBaseFluxSink.error(new Exception("El directorio que especifico no es un directorio"));
            }
        } );
	}

	@Override
	public Mono<Database> findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
