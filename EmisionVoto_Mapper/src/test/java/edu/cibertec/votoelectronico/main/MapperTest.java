//package edu.cibertec.votoelectronico.main;
//
//import java.util.Date;
//import java.util.List;
//
//import javax.transaction.SystemException;
//import javax.transaction.TransactionManager;
//
//import static com.arjuna.ats.jta.TransactionManager.transactionManager;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//
//import edu.cibertec.votoelectronico.application.MapperConfig;
//import edu.cibertec.votoelectronico.domain.GrupoPolitico;
//import edu.cibertec.votoelectronico.domain.Voto;
//import edu.cibertec.votoelectronico.repository.GrupoPoliticoRepository;
//import edu.cibertec.votoelectronico.repository.VotoRepository;
//import edu.cibertec.votoelectronico.repository.impl.GrupoPoliticoRepositoryImpl;
//import edu.cibertec.votoelectronico.repository.impl.VotoRepositoryImpl;
//
//public class MapperTest {
//	private static final Logger LOG = LoggerFactory.getLogger(MapperTest.class);
//
//	public static void main(String[] args) {
//		TransactionManager transactionManager = null;
//		try {
//			transactionManager = transactionManager();
//			transactionManager.begin();
//
//			ApplicationContext ctx = new AnnotationConfigApplicationContext(MapperConfig.class);
//
//			GrupoPoliticoRepository grupoPoliticoRepositorio = ctx.getBean(GrupoPoliticoRepositoryImpl.class);
//			VotoRepository votoRepositorio = ctx.getBean(VotoRepositoryImpl.class);
//
//			grupoPoliticoRepositorio.getAll().forEach(p -> {
//				LOG.info("Grupo Politico: " + p);
//			});
//
//			LOG.info("Deleting all records...");
//			votoRepositorio.deleteAll();
//			grupoPoliticoRepositorio.deleteAll();
//
//			LOG.info("Creating GrupoPolitico records...");
//			GrupoPolitico grupoPolitico1 = new GrupoPolitico();
//			grupoPolitico1.setNombre("P1");
//			grupoPolitico1.setDescripcion("Partido Politico Alianza");
//			grupoPoliticoRepositorio.create(grupoPolitico1);
//				
//			LOG.info("Partido Politico creado: " + grupoPolitico1.toString());
//			GrupoPolitico grupoPolitico2 = new GrupoPolitico();
//			grupoPolitico2.setNombre("P2");
//			grupoPolitico2.setDescripcion("Partido Politico Peru Posible");
//			grupoPoliticoRepositorio.create(grupoPolitico2);
//			LOG.info("Partido Politico creado: " + grupoPolitico2.toString());
//
//			LOG.info("Creating Voto records...");
//			Voto voto1 = new Voto();
//			voto1.setDni("48048360");
//			voto1.setFecha(new Date());
//			voto1.setGrupoPolitico(grupoPolitico1);
//			votoRepositorio.create(voto1);
//			LOG.info("Voto creado: " + voto1.toString());
//			Voto voto2 = new Voto();
//			voto2.setDni("48048361");
//			voto2.setFecha(new Date());
//			voto2.setGrupoPolitico(grupoPolitico2);
//			votoRepositorio.create(voto2);
//			LOG.info("Voto creado: " + voto2.toString());
//
//			LOG.info("Listing GrupoPolitico records...");
//			List<GrupoPolitico> grupoPoliticos = grupoPoliticoRepositorio.getAll();
//			if (grupoPoliticos != null && grupoPoliticos.size() > 0)
//				grupoPoliticos.forEach(p -> {
//					LOG.info(p.toString());
//				});
//			else
//				LOG.info("No existen grupos politicos");
//
//			LOG.info("Listing Voto records...");
//			List<Voto> votos = votoRepositorio.getAll();
//			if (votos != null && votos.size() > 0)
//				votos.forEach(p -> {
//					LOG.info(p.toString());
//				});
//			else
//				LOG.info("No existen votos");
//
//			throw new Exception("rollback");
//			//transactionManager.commit();
//		} catch (Exception e) {
//			try {
//				if (transactionManager != null)
//					transactionManager.rollback();
//			} catch (IllegalStateException | SecurityException | SystemException e1) {
//				e1.printStackTrace();
//			}
//			e.printStackTrace();
//		}
//
//	}
//}
