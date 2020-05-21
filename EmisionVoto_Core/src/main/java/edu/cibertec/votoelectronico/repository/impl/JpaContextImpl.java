package edu.cibertec.votoelectronico.repository.impl;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import edu.cibertec.votoelectronico.repository.JpaContext;

public class JpaContextImpl implements JpaContext {

	protected final EntityManager em;

	@Inject
	public JpaContextImpl(EntityManager em) {
		this.em = em;
	}

	@Override
	public void flushAndClose() {
		this.em.flush();
		this.em.close();
	}

}
