package edu.cibertec.votoelectronico.application;

import java.util.Properties;

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

	private final static String fileName = "database.properties";
	private static Properties props;

	private static final String HBN_DATASTORE_PROVIDER = "hibernate.ogm.datastore.provider";
	private static final String HBN_DATASTORE_CREATE_DATABASE = "hibernate.ogm.datastore.create_database";
	private static final String HBN_DATASTORE_DATABASE = "hibernate.ogm.datastore.database";
	private static final String HBN_DATASTORE_USERNAME = "hibernate.ogm.datastore.username";
	private static final String HBN_DATASTORE_PASSWORD = "hibernate.ogm.datastore.password";
	private static final String HBN_DATASTORE_HOST = "hibernate.ogm.datastore.host";
	private static final String HBN_DATASTORE_PORT = "hibernate.ogm.datastore.port";
	private static final String HBN_MONGODB_AUTHENTICATION_DATABASE = "hibernate.ogm.mongodb.authentication_database";

	static {
		try {
			props = new Properties();
			props.load(HibernateConfig.class.getClassLoader().getResourceAsStream(fileName));

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
			cfgogm.setProperty(HBN_DATASTORE_PROVIDER, props.getProperty(HBN_DATASTORE_PROVIDER));
//			cfgogm.setProperty("hibernate.ogm.datastore.grid_dialect",
//					"org.hibernate.ogm.dialect.mongodb.MongoDBDialect");
			cfgogm.setProperty(HBN_DATASTORE_CREATE_DATABASE, props.getProperty(HBN_DATASTORE_CREATE_DATABASE));
			cfgogm.setProperty(HBN_DATASTORE_DATABASE, props.getProperty(HBN_DATASTORE_DATABASE));
			cfgogm.setProperty(HBN_DATASTORE_USERNAME, props.getProperty(HBN_DATASTORE_USERNAME));
			cfgogm.setProperty(HBN_DATASTORE_PASSWORD, props.getProperty(HBN_DATASTORE_PASSWORD));
			cfgogm.setProperty(HBN_MONGODB_AUTHENTICATION_DATABASE,
					props.getProperty(HBN_MONGODB_AUTHENTICATION_DATABASE));
			cfgogm.setProperty(HBN_DATASTORE_HOST, props.getProperty(HBN_DATASTORE_HOST));
			cfgogm.setProperty(HBN_DATASTORE_PORT, props.getProperty(HBN_DATASTORE_PORT));

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
