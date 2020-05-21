package edu.cibertec.votoelectronico.repository;

import java.io.Serializable;
import java.util.List;

public interface CommonRepository<T, ID extends Serializable> {

	T find(ID id);

	List<T> getAll();

	void create(T object) throws Exception;

	void update(T object);
	
	void deleteAll() throws Exception;

	void delete(T object);

	default void delete(ID id) {
		delete(find(id));
	}

}
