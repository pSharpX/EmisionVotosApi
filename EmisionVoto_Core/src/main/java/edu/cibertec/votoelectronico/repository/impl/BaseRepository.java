package edu.cibertec.votoelectronico.repository.impl;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public abstract class BaseRepository {

	@Inject
	protected EntityManager em;

	public BaseRepository() {
	}

}
