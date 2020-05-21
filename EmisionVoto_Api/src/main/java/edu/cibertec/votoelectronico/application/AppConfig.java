//package edu.cibertec.votoelectronico.application;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
//import org.springframework.core.env.Environment;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//
//@Configuration
//@ComponentScan("edu.cibertec.votoelectronico")
//@PropertySource("classpath:application.properties")
//public class AppConfig {
//
//	@Autowired
//	private Environment env;
//
//	@Bean
//	public static PropertySourcesPlaceholderConfigurer properties() {
//		PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
//
//		Resource[] archivosPropiedades = new Resource[] { new ClassPathResource("application.properties") };
//		pspc.setLocations(archivosPropiedades);
//		pspc.setIgnoreUnresolvablePlaceholders(true);
//		return pspc;
//	}
//
//	public Environment getEnv() {
//		return env;
//	}
//
//	public void setEnv(Environment env) {
//		this.env = env;
//	}
//
//}
