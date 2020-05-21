//package edu.cibertec.votoelectronico.main;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//
//import edu.cibertec.votoelectronico.application.PersistenceConfig;
//import edu.cibertec.votoelectronico.helper.BeanHelper;
//import edu.cibertec.votoelectronico.helper.SecureHashingHelper;
//
//public class SpringTest {
//
//	private static final Logger LOG = LoggerFactory.getLogger(SpringTest.class);
//
//	public static void main(String[] args) {
//		try {
//
//			ApplicationContext ctx = new AnnotationConfigApplicationContext(PersistenceConfig.class);
////			GrupoPoliticoRepository grupoPoliticoRepositorio = ctx.getBean(GrupoPoliticoRepositoryImpl.class);
//			SecureHashingHelper hashingHelper = BeanHelper.services().getService(SecureHashingHelper.class);
//			LOG.info("After getting bean dependency");
//			String plainTextPassword = "holi123.a";
//			LOG.info("Password to be hashed: " + plainTextPassword);
//			String hashedPassword = hashingHelper.hashPassword(plainTextPassword);
//			LOG.info("Hashed Password: " + hashedPassword);
//			LOG.info("Check whether or not they are equals:"
//					+ hashingHelper.checkPass(plainTextPassword, hashedPassword));
//		} catch (Exception e) {
//			LOG.error(e.getMessage());
//		}
//	}
//
//}
