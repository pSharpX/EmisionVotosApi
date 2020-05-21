//package edu.cibertec.votoelectronico.main;
//
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//
//import edu.cibertec.votoelectronico.application.PersistenceConfig;
//import edu.cibertec.votoelectronico.repository.GrupoPoliticoRepository;
//import edu.cibertec.votoelectronico.repository.impl.GrupoPoliticoRepositoryImpl;
//
//public class EntityManagerTest {
//	public static void main(String[] args) {
//		ApplicationContext ctx = new AnnotationConfigApplicationContext(PersistenceConfig.class);
//
//		GrupoPoliticoRepository grupoPoliticoRepositorio = ctx.getBean(GrupoPoliticoRepositoryImpl.class);
//		grupoPoliticoRepositorio.getAll().forEach(p -> {
//			System.out.printf("Grupo Politico: %s, Nombre: %s\n", p.getNombre());
//		});
//
//	}
//}
