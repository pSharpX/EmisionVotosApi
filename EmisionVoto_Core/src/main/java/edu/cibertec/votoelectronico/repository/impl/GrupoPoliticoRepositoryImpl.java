package edu.cibertec.votoelectronico.repository.impl;

import static com.arjuna.ats.jta.UserTransaction.userTransaction;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cibertec.votoelectronico.application.HibernateConfig;
import edu.cibertec.votoelectronico.domain.GrupoPolitico;
import edu.cibertec.votoelectronico.repository.GrupoPoliticoRepository;

public class GrupoPoliticoRepositoryImpl extends BaseRepository implements GrupoPoliticoRepository {

	private final Logger LOG = LoggerFactory.getLogger(GrupoPoliticoRepositoryImpl.class);

	private static final String Q_GET_ALL = "SELECT c FROM GrupoPolitico c";
	private static final String Q_GET_BY_NAME = "SELECT c FROM GrupoPolitico c WHERE nombre = :nombre";

	private static final String NQ_DELETE_ALL = "QN_GRUPO_POLITICO_DELETE_ALL";

	@Override
	public GrupoPolitico find(String id) {
		return this.em.find(GrupoPolitico.class, id);
	}

	@Override
	public List<GrupoPolitico> getAll() {
		TypedQuery<GrupoPolitico> query = this.em.createQuery(Q_GET_ALL, GrupoPolitico.class);
		return query.getResultList();
	}

	@Override
	public void create(GrupoPolitico object) throws Exception {
		UserTransaction tx = null;
		try {
			tx = userTransaction();
			tx.begin();
			HibernateConfig.getSessionFactory().getCurrentSession().persist(object);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw e;
		}
	}

	@Override
	public void update(GrupoPolitico object) {
		this.em.merge(object);

	}

	@Override
	public void delete(GrupoPolitico object) {
		this.em.remove(object);
	}

	@Override
	public void deleteAll() throws Exception {
		UserTransaction tx = null;
		try {
			tx = userTransaction();
			tx.begin();
			this.em.createNamedQuery(NQ_DELETE_ALL, GrupoPolitico.class).executeUpdate();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw e;
		}
	}

	@Override
	public GrupoPolitico findByName(String name) throws Exception {
		GrupoPolitico grupoPolitico = null;
		try {
			TypedQuery<GrupoPolitico> query = this.em.createQuery(Q_GET_BY_NAME, GrupoPolitico.class)
					.setParameter("nombre", name);
			grupoPolitico = query.getSingleResult();
			return grupoPolitico;
		} catch (NoResultException e) {
			LOG.error("No se encontro grupoPolitico con nombre " + name + ". " + e.getMessage());
			return grupoPolitico;
		} catch (NonUniqueResultException e) {
			LOG.error("Se encontraron varios grupoPolitico con el mismo nombre " + name + ". " + e.getMessage());
			return grupoPolitico;
		}
	}

}
