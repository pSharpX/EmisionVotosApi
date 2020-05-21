package edu.cibertec.votoelectronico.application;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.ogm.cfg.OgmConfiguration;
import org.hibernate.service.ServiceRegistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cibertec.votoelectronico.domain.GrupoPolitico;
import edu.cibertec.votoelectronico.domain.Voto;

/**
 * HibernateUtil class (no need of hibernate.cfg.xml)
 * 
 */
public class HibernateConfig {

	private static final Logger LOG = LoggerFactory.getLogger(HibernateConfig.class);
	private static final SessionFactory sessionFactory;
	private static final ServiceRegistry serviceRegistry;

	static {
		try {
			// create a new instance of OmgConfiguration
			OgmConfiguration cfgogm = new OgmConfiguration();

			// enable transaction strategy
			// enable JTA strategy
			cfgogm.setProperty(Environment.TRANSACTION_COORDINATOR_STRATEGY, "jta");
			cfgogm.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, "jta");

			// specify JTA platform
			cfgogm.setProperty(Environment.JTA_PLATFORM, "JBossTS");
//			cfgogm.setProperty(Environment.JTA_PLATFORM, "org.hibernate.service.jta.platform.internal.JBossStandAloneJtaPlatform");

			// in order to select the local JBoss JTA implementation it is necessary to
			// specify
//			cfgogm.setProperty("com.arjuna.ats.jta.jtaTMImplementation",
//					"com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionManagerImple");
//			cfgogm.setProperty("com.arjuna.ats.jta.jtaUTImplementation",
//					"com.arjuna.ats.internal.jta.transaction.arjunacore.UserTransactionImple");

			// configure MongoDB connection
			cfgogm.setProperty("hibernate.ogm.datastore.provider", "mongodb");
//			cfgogm.setProperty("hibernate.ogm.datastore.grid_dialect",
//					"org.hibernate.ogm.dialect.mongodb.MongoDBDialect");
			cfgogm.setProperty("hibernate.ogm.datastore.create_database", "true");
			cfgogm.setProperty("hibernate.ogm.datastore.database", "votoelectronico_db");
			cfgogm.setProperty("hibernate.ogm.datastore.username", "votoelectronico_admin");
			cfgogm.setProperty("hibernate.ogm.datastore.password", "$votoelectronico_admin123.a");
			cfgogm.setProperty("hibernate.ogm.mongodb.authentication_database", "votoelectronico_db");
			cfgogm.setProperty("hibernate.ogm.datastore.host", "127.0.0.1");
			cfgogm.setProperty("hibernate.ogm.datastore.port", "27017");

			// add our annotated class
			cfgogm.addAnnotatedClass(GrupoPolitico.class);
			cfgogm.addAnnotatedClass(Voto.class);

			// create the SessionFactory
			serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfgogm.getProperties()).build();
			sessionFactory = cfgogm.buildSessionFactory(serviceRegistry);

		} catch (Exception ex) {
			LOG.info("Initial SessionFactory creation failed !", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
