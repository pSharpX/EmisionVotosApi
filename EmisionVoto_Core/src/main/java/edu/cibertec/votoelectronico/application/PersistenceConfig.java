package edu.cibertec.votoelectronico.application;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceConfig {

	@Produces
	public EntityManagerFactory createEntityManagerFactory() {
		return Persistence.createEntityManagerFactory("ogm-mongodb");
	}

	@Produces
	public EntityManager createEntityManager(EntityManagerFactory emf) {
		return emf.createEntityManager();
	}

}
