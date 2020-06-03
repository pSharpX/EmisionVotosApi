package edu.cibertec.votoelectronico.client.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.enterprise.context.Dependent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Dependent
public class AppGetPropertyValues {

	private static final Logger LOG = LoggerFactory.getLogger(AppGetPropertyValues.class);

	private final String propFileName = "application.properties";

	private Properties props;
	private InputStream inputStream;

	public Properties getPropValues() throws IOException {
		try {
			props = new Properties();
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				props.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
		} catch (Exception e) {
			LOG.error("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return props;
	}

}
